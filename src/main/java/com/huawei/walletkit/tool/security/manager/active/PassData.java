/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.manager.active;

import com.huawei.walletkit.tool.security.util.AesUtils;
import com.huawei.walletkit.tool.security.util.CommonUtils;
import com.huawei.walletkit.tool.security.util.DataConvertUtil;
import com.huawei.walletkit.tool.security.util.LogUtil;
import com.huawei.walletkit.tool.security.model.Certificate;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Some data of the pass
 *
 * @since 2020-02-18
 */
public class PassData {
    private static final String TAG = "PassData";
    private static final String AES_TEMP_KEY_PREV_PART = "34810233";
    private static final String CARD_KEY_PREV_PART = "34010133";

    /**
     * Create the token for personalize data, this is a test data, developer should modify the return value
     *
     * @return Token for personalize data
     */
    public static String getPersonalizeToken() {
        return "sp.token." + System.currentTimeMillis();
    }

    /**
     * Get sp server's certificate, encrypt applet identity public key with private key of sp server
     *
     * @param authPublicKey applet identity public key
     * @return sp server's certificate
     */
    public static Certificate getServerCert(String authPublicKey) {
        String signature = DataConvertUtil.signData(authPublicKey, Constants.SERVER_SECRET_KEY);
        if (CommonUtils.isStringEmpty(signature)) {
            LogUtil.info("getServerCert, sign data error.");
            return null;
        }
        Certificate cert = new Certificate();
        cert.setPublicKey(authPublicKey);
        cert.setSignature(signature);
        return cert;
    }

    /**
     * Get personalize data, the data must be encrypted with applet personalize public key
     *
     * @param appletPublicKey applet personalize public key
     * @param serialNumber    card serial number
     * @return Personalize data in json
     */
    public static String getPersonalizeData(String appletPublicKey, String serialNumber) {
        JSONObject rootObj = new JSONObject();
        String aesKey = AesUtils.getAesKey(16);
        String aesIv = AesUtils.getAesIV();
        LogUtil.info(TAG, "getPersonalizeData, aesKey=" + LogUtil.parseSensitiveinfo(aesKey));
        LogUtil.info(TAG, "getPersonalizeData, aesIv=" + LogUtil.parseSensitiveinfo(aesIv));

        // Encrypt AES key with applet identity public key by RSA,
        // you must add a fix prev hex string to the key before you encrypt it.
        LogUtil.info(TAG, "getPersonalizeData, aesKey2=" + LogUtil.parseSensitiveinfo(AES_TEMP_KEY_PREV_PART + aesKey));
        String tempKey = DataConvertUtil.encryptToHexByPublicKey(
            DataConvertUtil.hexStringToByteArray(AES_TEMP_KEY_PREV_PART + aesKey),
            appletPublicKey, DataConvertUtil.PERSONALIZE_DATA_ENCRYPT_MODE);

        // Encrypt AES IV with applet identity public key by RSA
        String tempIv = DataConvertUtil.encryptToHexByPublicKey(DataConvertUtil.hexStringToByteArray(aesIv),
            appletPublicKey, DataConvertUtil.PERSONALIZE_DATA_ENCRYPT_MODE);

        String cardId = getCardId(serialNumber);
        // Fill card id data
        byte[] filledCardId = fillData(cardId);
        // Encrypt filled card id by AES
        String cardIdEncrypt = AesUtils.encryptToHex(filledCardId, aesKey, aesIv, AesUtils.AES_CBC_NOPADDING);

        // Encrypt card key with applet identity public key by RSA,
        String srcCardId = getCardKey();
        LogUtil.info(TAG, "getPersonalizeData, srcCardId=" + LogUtil.parseSensitiveinfo(srcCardId));
        String cardKey = DataConvertUtil.encryptToHexByPublicKey(DataConvertUtil.hexStringToByteArray(srcCardId),
            appletPublicKey, DataConvertUtil.PERSONALIZE_DATA_ENCRYPT_MODE);

        // Fill card info data
        byte[] filledCardInfo = fillData(getCardInfo() + getCardAuthParameter());
        // Encrypt filled card info by AES
        String cardInfoEncrypt = AesUtils.encryptToHex(filledCardInfo, aesKey, aesIv, AesUtils.AES_CBC_NOPADDING);

        // Fill private info data
        byte[] filledPrivateInfo = fillData(getPrivateInfo());
        // Encrypt filled privet info data by AES
        String privateInfoEncrypt = AesUtils.encryptToHex(filledPrivateInfo, aesKey, aesIv, AesUtils.AES_CBC_NOPADDING);

        rootObj.put("temp_key", tempKey);
        rootObj.put("temp_iv", tempIv);
        rootObj.put("card_id", cardIdEncrypt);
        rootObj.put("card_key", cardKey);
        rootObj.put("card_info", cardInfoEncrypt);
        rootObj.put("card_privateInfo", privateInfoEncrypt);
        rootObj.put("card_key_iv", getCardKeyIv());
        LogUtil.info(TAG, "Personalize:" + rootObj, true);
        return rootObj.toString();
    }

    /**
     * Fill personal data before encrypt it.
     *
     * @param srcData personal data
     * @return filled byte array
     */
    private static byte[] fillData(String srcData) {
        byte[] srcBytes = DataConvertUtil.hexStringToByteArray(srcData);
        byte[] lengthBytes = DataConvertUtil.dataLengthToBytes(srcBytes.length);
        if (lengthBytes.length == 0) {
            LogUtil.info("fillData, data length error, length=" + srcBytes.length);
            return null;
        }
        int totalLength = (lengthBytes.length + srcBytes.length + 15) / 16 * 16;
        byte[] dstBytes = new byte[totalLength];
        Arrays.fill(dstBytes, (byte) 0);
        System.arraycopy(lengthBytes, 0, dstBytes, 0, lengthBytes.length);
        System.arraycopy(srcBytes, 0, dstBytes, lengthBytes.length, srcBytes.length);
        if (lengthBytes.length + srcBytes.length < totalLength) {
            // First filled byte must be 0x80, others be 0x00.
            dstBytes[lengthBytes.length + srcBytes.length] = (byte) 0x80;
        }
        return dstBytes;
    }

    private static String getTLV(String tag, String value) {
        StringBuilder builder = new StringBuilder();
        String lc = "";
        if (value.length() > 254 && value.length() < 300) {
            lc = "81";
        }
        lc = lc + Integer.toHexString(value.length() / 2);
        return builder.append(tag).append(lc).append(value).toString();
    }

    private static String getCardInfo() {
        return getTLV("9F05", "00000000000000000000000000000000");
    }

    private static String getCardAuthParameter() {
        return getTLV("9F31", "0102030405060708");
    }

    private static String getCardKeyIv() {
        return "10" + "00000000000000000000000000000000";
    }

    /**
     * Private card data，this is a test data, developer must fix the return data
     */
    private static String getPrivateInfo() {
        return "00000000000000000000000000000000";
    }

    /**
     * The unique ID of card，this is a test data, developer must fix the return data
     */
    private static String getCardId(String serialNumber) {
        return "9F3B" + "10" + serialNumber;
    }

    /**
     * The private key of card，this is a test data, developer must fix the return data.
     */
    private static String getCardKey() {
        // you must add a fix prev hex string to the key before you encrypt it.
        return CARD_KEY_PREV_PART + AesUtils.getAesKey(32);
    }
}
