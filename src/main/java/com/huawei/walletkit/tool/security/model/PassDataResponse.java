/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

import com.huawei.walletkit.tool.security.manager.active.KeyValueConnector;

/**
 * PassDataResponse
 *
 * @since 2020-01-17
 */
public class PassDataResponse {
    private String encryptAppletPersonalizeFields;
    private String encryptSessionKey;

    public String getEncryptAppletPersonalizeFields() {
        return encryptAppletPersonalizeFields;
    }

    public void setEncryptAppletPersonalizeFields(String encryptAppletPersonalizeFields) {
        this.encryptAppletPersonalizeFields = encryptAppletPersonalizeFields;
    }

    public String getEncryptSessionKey() {
        return encryptSessionKey;
    }

    public void setEncryptSessionKey(String encryptSessionKey) {
        this.encryptSessionKey = encryptSessionKey;
    }

    /**
     * toString()方法
     *
     * @return 返回一个toString 字串
     */
    public String toJsonString() {
        return new KeyValueConnector()
                .append("encryptAppletPersonalizeFields", encryptAppletPersonalizeFields)
                .append("encryptSessionKey", encryptSessionKey)
                .toString();
    }
}
