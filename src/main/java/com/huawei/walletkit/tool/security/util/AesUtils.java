/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.util;

import com.huawei.walletkit.tool.security.manager.active.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/**
 * AES Utils
 *
 * @since 2020-01-19
 */
public class AesUtils {
    /**
     * AES CBC encryption mode
     */
    public static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * AES CBC Nopadding encryption mode
     */
    public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";

    private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    /**
     * AES-GCM encryption.
     *
     * @param plainData    the data to be encrypted.
     * @param secretKeyStr encryption secret key.
     * @param iv           encryption random iv.
     * @return the encrypted string.
     */
    public static String encryptByGcm(String plainData, String secretKeyStr, byte[] iv) {
        try {
            byte[] secretKeyByte = secretKeyStr.getBytes(StandardCharsets.UTF_8);
            byte[] plainByte = plainData.getBytes(Charsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(secretKeyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", BOUNCY_CASTLE_PROVIDER);
            AlgorithmParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(1, secretKey, spec);
            byte[] fBytes = cipher.doFinal(plainByte);
            return new String(Hex.encodeHex(fBytes, false));
        } catch (Exception e) {
            throw new IllegalArgumentException("GCM encryption failed. Error: " + e.getMessage());
        }
    }

    /**
     * Generate encryption random iv.
     *
     * @param size iv length
     * @return encryption the byte array.
     */
    public static byte[] getIvByte(int size) {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[size];
            sr.nextBytes(bytes);
            return bytes;
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("Get iv bytes failed. Error: " + e.getMessage());
        }
    }

    /**
     * Get AES key
     *
     * @param length Length or AES key
     * @return String AES key value in format
     */
    public static String getAesKey(int length) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[length];
        random.nextBytes(key);
        return DataConvertUtil.byteArrayToHexString(key);
    }

    /**
     * Get 16 bytes IV value
     *
     * @return String IV value in hex format
     */
    public static String getAesIV() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return DataConvertUtil.byteArrayToHexString(key);
    }

    /**
     * AES encryption
     *
     * @param data      data to be encrypted
     * @param key       encryption key
     * @param iv        IV value
     * @param algorithm encryption mode
     * @return String encryption result
     */
    public static String encryptToHex(String data, String key, String iv, String algorithm) {
        if (CommonUtils.isStringEmpty(data) || CommonUtils.isStringEmpty(key)) {
            LogUtil.info("AES:AES encrypt, Invalid params, data or key is empty");
            return null;
        }
        try {
            return encryptToHex(data.getBytes(Constants.UTF8_ENCODING), key, iv, algorithm);
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("AES:encrypt UnsupportedEncodingException.");
        }
        return null;
    }

    /**
     * AES encryption
     *
     * @param srcData   data to be encrypted
     * @param key       encryption key
     * @param iv        IV value
     * @param algorithm encryption mode
     * @return String encryption result
     */
    public static String encryptToHex(byte[] srcData, String key, String iv, String algorithm) {
        if (srcData == null || CommonUtils.isStringEmpty(key)) {
            LogUtil.info("AES:AES encrypt, Invalid params, data or key is empty");
            return null;
        }
        try {
            byte[] valueByte = encrypt(srcData, DataConvertUtil.hexStringToByteArray(key),
                    DataConvertUtil.hexStringToByteArray(iv), algorithm);
            return DataConvertUtil.byteArrayToHexString(valueByte);
        } catch (Exception e) {
            LogUtil.info("AesUtils", "AES:encryptToBase64 Exception::" + LogUtil.parseSensitiveinfo(e.toString()));
        }
        return null;
    }

    /**
     * AES encryption
     *
     * @param data      data to be encrypted
     * @param key       encryption key
     * @param iv        IV value
     * @param algorithm encryption mode
     * @return String encryption result, encoded with Base64
     */
    public static String encryptToBase64(String data, String key, String iv, String algorithm) {
        if (CommonUtils.isStringEmpty(data) || CommonUtils.isStringEmpty(key)) {
            LogUtil.info("AES:AES encrypt, Invalid params, data or key is empty");
            return null;
        }
        try {
            byte[] valueByte = encrypt(data.getBytes(Constants.UTF8_ENCODING),
                    DataConvertUtil.hexStringToByteArray(key), DataConvertUtil.hexStringToByteArray(iv), algorithm);
            return DataConvertUtil.base64EncodeToString(valueByte);
        } catch (UnsupportedEncodingException e) {
            LogUtil.info("AES:encrypt UnsupportedEncodingException.");
        }
        return null;
    }

    private static byte[] encrypt(byte[] data, byte[] key, byte[] ivBytes, String algorithm) {
        if (data == null || key == null) {
            LogUtil.info("AES:AES encrypt, invalid params, data: " + (data == null) + " key: " + (key == null));
            return new byte[0];
        }
        if (ivBytes.length != 16) {
            LogUtil.info("AES:AES encrypt, Invalid AES iv length (must be 16 bytes)");
            return new byte[0];
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            if (CommonUtils.isStringEmpty(algorithm)) {
                // CBC mode as default
                algorithm = AES_CBC_PKCS_5_PADDING;
            }
            Cipher cipher = Cipher.getInstance(algorithm);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            return cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            LogUtil.info("AES:encrypt NoSuchPaddingException. ");
        } catch (NoSuchAlgorithmException e) {
            LogUtil.info("AES:encrypt NoSuchAlgorithmException. ");
        } catch (InvalidAlgorithmParameterException e) {
            LogUtil.info("AES:encrypt InvalidAlgorithmParameterException.");
        } catch (InvalidKeyException e) {
            LogUtil.info("AES:encrypt InvalidKeyException.");
        } catch (BadPaddingException e) {
            LogUtil.info("AES:encrypt BadPaddingException.");
        } catch (IllegalBlockSizeException e) {
            LogUtil.info("AES:encrypt IllegalBlockSizeException. ");
        } catch (Exception e) {
            LogUtil.info("AesUtils", "AES:encrypt Exception::" + LogUtil.parseSensitiveinfo(e.toString()));
        }
        return new byte[0];
    }
}
