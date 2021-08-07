/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.model;

/**
 * Response for register request 1.1
 *
 * @since 2020-01-17
 */
public class AddInstanceResponse {
    private String httpStatus;

    private String jwe;

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getJwe() {
        return jwe;
    }

    public void setJwe(String jwe) {
        this.jwe = jwe;
    }
}
