/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.huawei.walletkit.tool.security.controller;

import com.huawei.walletkit.tool.security.manager.addinstance.AddICCECarKeyInstance;
import com.huawei.walletkit.tool.security.model.AddInstanceResponse;
import com.huawei.walletkit.tool.security.model.RegistrationsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * AddInstanceController
 *
 * @since 2020-01-16
 */
@RestController
public class AddInstanceController {
    /**
     * addInstance: Car app -> My server
     *
     * @param res HttpServletRequest
     * @param request RegistrationsRequest
     * @return ResponseEntity
     */
    @PostMapping(path = "/v1/passes/addInstance")
    public ResponseEntity<AddInstanceResponse> addInstance(HttpServletRequest res,
        @RequestBody RegistrationsRequest request) {
        return ResponseEntity.ok(AddICCECarKeyInstance.getInstance().addStdCarKeyInstance());
    }
}
