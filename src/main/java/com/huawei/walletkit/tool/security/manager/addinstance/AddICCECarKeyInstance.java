/*
 * Copyright 2021. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.huawei.walletkit.tool.security.manager.addinstance;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huawei.walletkit.tool.security.manager.addinstance.hms.ServerApiService;
import com.huawei.walletkit.tool.security.manager.addinstance.hms.ServerApiServiceImpl;
import com.huawei.walletkit.tool.security.manager.active.Constants;
import com.huawei.walletkit.tool.security.model.AddInstanceRequest;
import com.huawei.walletkit.tool.security.model.AddInstanceResponse;
import com.huawei.walletkit.tool.security.model.hwobject.HwWalletObject;
import com.huawei.walletkit.tool.security.util.ConfigUtil;
import com.huawei.walletkit.tool.security.util.HwWalletObjectUtil;
import com.huawei.walletkit.tool.security.util.JweUtil;
import com.huawei.walletkit.tool.security.util.LogUtil;

/**
 * Std car key instance tests.
 *
 * @since 2021-05-27
 */
public class AddICCECarKeyInstance {
    private static final String TAG = "AddICCECarKeyInstance";

    private final ServerApiService serverApiService = new ServerApiServiceImpl();

    private static final AddICCECarKeyInstance INSTANCE = new AddICCECarKeyInstance();

    public static AddICCECarKeyInstance getInstance() {
        return INSTANCE;
    }

    /**
     * Add a std car key instance to HMS wallet server.
     * Run the "createStdCarKeyInstance" test before running this test.
     * After using this API, you will use a thin JWE to bind this instance to a user. Or you can add an instance by
     * sending a JWE with complete instance information, without using this API. See JWE example tests.
     * POST http://XXX/hmspass/v2/key_stdcar/instance
     */
    public AddInstanceResponse addStdCarKeyInstance(HwWalletObject instance) {
        LogUtil.info(TAG, "addStdCarKeyInstance begin.");

        // Validate parameters.
        HwWalletObjectUtil.validateInstance(instance);

        // Post the new std car key instance to HMS wallet server.
        String urlSegment = "/v2/key_stdcar/instance";
        HwWalletObject responseInstance =
                serverApiService.postToWalletServer(urlSegment, instance);
        LogUtil.info(TAG, "Posted std car key instance: " + JSONObject.toJSONString(responseInstance));
        AddInstanceResponse response = new AddInstanceResponse();
        if (responseInstance == null) {
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }
        String serialNumber = responseInstance.getSerialNumber();
        if (serialNumber == null || serialNumber.isEmpty()) {
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_PARAM_ERROR));
            return response;
        }

        String jwe = JweUtil.generateThinJWEToBindUser(serialNumber);
        if (jwe == null || jwe.isEmpty()) {
            response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_INNER_ERROR));
            return response;
        }
        // todo:insert record to DB

        response.setHttpStatus(String.valueOf(Constants.RESULT_CODE_OK));
        response.setJwe(jwe);
        return response;
    }

    /**
     * Get a std car key instance by its instance ID.
     * Run the "createStdCarKeyInstance" test before running this test.
     * GET http://xxx/hmspass/v2/key_stdcar/instance/{instanceId}
     */
    public AddInstanceResponse getStdCarKeyInstance(HwWalletObject object) {
        LogUtil.info(TAG, "getStdCarKeyInstance begin.");
        AddInstanceResponse response = new AddInstanceResponse();
        String instanceId = object.getSerialNumber();
        // Get the std car key instance.
        String urlSegment = "/v2/key_stdcar/instance/";
        JSONObject responseInstance = serverApiService.getHwWalletObjectById(urlSegment, instanceId);
        LogUtil.info(TAG, "Corresponding std car key instance: " + JSONObject.toJSONString(responseInstance));
        return response;
    }

    /**
     * Get std car key instance belonging to a specific std car key model.
     * Run the "createStdCarKeyInstance" test before running this test.
     * GET http://xxx/hmspass/v2/key_stdcar/instance?modelId=XXX&session=XXX&pageSize=XXX
     */
    public AddInstanceResponse getStdCarKeyInstanceList(HwWalletObject object) {
        LogUtil.info(TAG, "getStdCarKeyyInstanceList begin.");
        AddInstanceResponse response = new AddInstanceResponse();
        String modelId = object.getPassStyleIdentifier();
        // Get a list of std car key instances.
        String urlSegment = "/v2/key_stdcar/instance";

        JSONArray instances = serverApiService.getInstances(urlSegment, modelId, 5);
        LogUtil.info(TAG, "Total instances count: " + instances.size());
        LogUtil.info(TAG, "Instances list: " + instances.toJSONString());
        return response;
    }

    /**
     * Overwrite a std car key instance.
     * Run the "createStdCarKeyInstance" test before running this test.
     * PUT http://xxx/hmspass/v2/key_stdcar/instance/{instanceId}
     */
    public AddInstanceResponse fullUpdateStdCarKeyInstance(HwWalletObject instance) {
        LogUtil.info(TAG, "fullUpdateStdCarKeyInstance begin.");
        AddInstanceResponse response = new AddInstanceResponse();
        // Validate parameters.
        HwWalletObjectUtil.validateInstance(instance);

        // Update the std car key instance.
        String urlSegment = "/v2/key_stdcar/instance/";
        JSONObject responseInstance = serverApiService.fullUpdateHwWalletObject(urlSegment,
                instance.getSerialNumber(), instance);
        LogUtil.info(TAG, "Updated std car key instance: " + JSONObject.toJSONString(responseInstance));
        return response;
    }

    /**
     * Update a std car key instance.
     * Run the "createStdCarKeyInstance" test before running this test.
     * PATCH http://xxx/hmspass/v2/key_stdcar/instance/{instanceId}
     */
    public AddInstanceResponse partialUpdateStdCarKeyInstance(HwWalletObject instance) {
        LogUtil.info(TAG, "partialUpdateStdCarKeyInstance begin.");
        AddInstanceResponse response = new AddInstanceResponse();
        // Update the std car key instance.
        String urlSegment = "/v2/key_stdcar/instance/";
        JSONObject responseInstance = serverApiService.partialUpdateHwWalletObject(urlSegment, instance.getSerialNumber(), instance);
        LogUtil.info(TAG, "Updated std car key instance: " + JSONObject.toJSONString(responseInstance));
        return response;
    }
}
