/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

import com.huawei.walletkit.tool.security.manager.active.KeyValueConnector;
import com.huawei.walletkit.tool.security.model.hwobject.HwWalletObject;

/**
 * AddInstanceRequest 1.1
 *
 * @since 2020-01-17
 */
public class AddInstanceRequest {
    private HwWalletObject walletObject;

    private String signature;

    private Certificate certificate;

    public HwWalletObject getWalletObject() {
        return walletObject;
    }

    public void setWalletObject(HwWalletObject walletObject) {
        this.walletObject = walletObject;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public String toJsonString() {
        return walletObject.toString();
    }
}
