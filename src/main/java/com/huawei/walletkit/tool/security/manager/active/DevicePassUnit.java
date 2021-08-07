/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.manager.active;

import com.huawei.walletkit.tool.security.util.AesUtils;
import com.huawei.walletkit.tool.security.util.DataConvertUtil;
import com.huawei.walletkit.tool.security.util.LogUtil;
import com.huawei.walletkit.tool.security.model.PassDataResponse;
import com.huawei.walletkit.tool.security.model.RequestBody;
import com.huawei.walletkit.tool.security.pass.PassDataField;
import com.huawei.walletkit.tool.security.pass.PassDataInfo;
import com.huawei.walletkit.tool.security.pass.PassPackageCreator;

/**
 * Basic data of pass
 *
 * @since 2020-01-19
 */
public class DevicePassUnit {
    private static final String TAG = "DevicePassUnit";

    private String organizationName = "Test Orginization";

    private String encryptSessionKey;

    private String encryptUserDeviceFields;

    private String encryptAppletPersonalizeFields;

    /**
     * Get encrypted pass info and personalize data
     *
     * @param requestBody request body
     * @return Pass data
     */
    public PassDataResponse toJson(RequestBody requestBody) {
        generatePassData(requestBody);
        PassDataResponse dataInfo = new PassDataResponse();
        dataInfo.setEncryptAppletPersonalizeFields(encryptAppletPersonalizeFields);
        dataInfo.setEncryptDevicePass(encryptUserDeviceFields);
        dataInfo.setEncryptSessionKey(encryptSessionKey);
        return dataInfo;
    }

    private void generatePassData(RequestBody requestBody) {
        String transId = requestBody.getTransId();
        String taPublicKey = requestBody.getTransPublicKey();
        String appletPublicKey = requestBody.getPersonalizePublicKey();
        LogUtil.info(TAG, "generatePassData, transId=" + LogUtil.parseSensitiveinfo(transId));
        LogUtil.info(TAG, "generatePassData, taPublicKey=" + LogUtil.parseSensitiveinfo(taPublicKey));
        LogUtil.info(TAG, "generatePassData, appletPublicKey=" + LogUtil.parseSensitiveinfo(appletPublicKey));
        String aesKey = AesUtils.getAesKey(16);
        String aesIv = AesUtils.getAesIV();
        String originKey = transId + aesKey + aesIv;
        // Encrypt originKey with taPublicKey
        encryptSessionKey = DataConvertUtil.encryptToBase64ByPublicKey(originKey, taPublicKey,
                DataConvertUtil.TA_PUBLIC_KEY_ENCRYPT_MODE);
        LogUtil.info(TAG, "generatePassData, encryptSessionKey=" + LogUtil.parseSensitiveinfo(encryptSessionKey));
        // Get pass package data
        String passPackageData = generatePassPackageData(requestBody);
        LogUtil.info(TAG, "generatePassData, passPackageData=" + passPackageData, true);
        // Encrypt pass data with key-iv
        encryptUserDeviceFields =
                AesUtils.encryptToBase64(passPackageData, aesKey, aesIv, AesUtils.AES_CBC_PKCS_5_PADDING);
        LogUtil.info(TAG, "generatePassData, encryptUserDeviceFields=" + encryptUserDeviceFields, true);
        // get personalize data
        String personalizeData = PassData.getPersonalizeData(appletPublicKey, requestBody.getSerialNumber());
        LogUtil.info(TAG, "generatePassData, personalizeData=" + LogUtil.parseSensitiveinfo(personalizeData));
        // Encrypt personalize data with key-iv
        encryptAppletPersonalizeFields =
                AesUtils.encryptToBase64(personalizeData, aesKey, aesIv, AesUtils.AES_CBC_PKCS_5_PADDING);
        LogUtil.info(TAG, "generatePassData, encryptAppletPersonalizeFields=" + encryptAppletPersonalizeFields, true);
    }

    private String generatePassPackageData(RequestBody requestBody) {
        PassDataInfo passDataInfo = new PassDataInfo();
        passDataInfo.setPassVersion(requestBody.getPassVersion());
        passDataInfo.setOrganizationPassId(requestBody.getSerialNumber());
        passDataInfo.setOrganizationName(organizationName);
        passDataInfo.setPassTypeIdentifier(requestBody.getPassTypeIdentifier());
        passDataInfo.setSerialNumber(requestBody.getSerialNumber());
        passDataInfo.setPassStyleIdentifier("DevicePassStyle");
        PassDataField fields = new PassDataField();
        passDataInfo.setFields(fields);
        String dataInfo = PassPackageCreator.createPassPackage(passDataInfo);
        LogUtil.info(TAG, "dataInfo:" + dataInfo, true);
        return dataInfo;
    }
}
