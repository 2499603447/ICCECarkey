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
import com.huawei.walletkit.tool.security.model.hwobject.HwWalletObject;
import com.huawei.walletkit.tool.security.util.ConfigUtil;
import com.huawei.walletkit.tool.security.util.HwWalletObjectUtil;
import com.huawei.walletkit.tool.security.util.LogUtil;

/**
 * Std car key model tests.
 *
 * @since 2021-05-27
 */
public class AddICCECarKeyModel {
    private static final String TAG = "AddICCECarKeyModel";
    private final ServerApiService serverApiService = new ServerApiServiceImpl();

    private static final AddICCECarKeyModel INSTANCE = new AddICCECarKeyModel();

    public static AddICCECarKeyModel getInstance() {
        return INSTANCE;
    }

    /**
     * Create a new std car key model.
     * Each std car key model indicates a style of std car key passes.
     * POST http://XXX/hmspass/v2/key_stdcar/model
     */
    public void createStdCarKeyModel(HwWalletObject model) {
        LogUtil.info(TAG, "createStdCarKeyModel begin.");

        // Validate parameters.
        HwWalletObjectUtil.validateModel(model);

        // Post the new std car key model to HMS wallet server.
        String urlSegment = "/v2/key_stdcar/model";
        HwWalletObject responseModel = serverApiService.postToWalletServer(urlSegment, model);
        LogUtil.info(TAG, "Posted std car key model: " + JSONObject.toJSONString(responseModel));
    }

    /**
     * Get a std car key model by its model ID.
     * Run the "createStdCarKeyModel" test before running this test.
     * GET http://xxx/hmspass/v2/key_stdcar/model/{modelId}
     */
    public void getStdCarKeyModel(String modelId) {
        LogUtil.info(TAG, "getStdCarKeyModel begin.");

        // Get the std car key model.
        String urlSegment = "/v2/key_stdcar/model/";
        JSONObject responseModel = serverApiService.getHwWalletObjectById(urlSegment, modelId);
        LogUtil.info(TAG, "Corresponding std car key model: " + JSONObject.toJSONString(responseModel));
    }

    /**
     * Get std car key models belonging to a specific appId.
     * Run the "createStdCarKeyModel" test before running this test.
     * GET http://xxx/hmspass/v2/key_stdcar/model?session=XXX&pageSize=XXX
     */
    public void getStdCarKeyModelList() {
        LogUtil.info(TAG, "getStdCarKeyModelList begin.");

        // Get a list of std car key models.
        String urlSegment = "/v2/key_stdcar/model";

        JSONArray models = serverApiService.getModels(urlSegment, 5);
        LogUtil.info(TAG, "Total models count: " + models.size());
        LogUtil.info(TAG, "Models list: " + models.toJSONString());
    }

    /**
     * Overwrite a std car key model.
     * Run the "createStdCarKeyModel" test before running this test.
     * PUT http://xxx/hmspass/v2/key_stdcar/model/{modelId}
     */
    public void fullUpdateStdCarKeyModel(HwWalletObject object) {
        LogUtil.info(TAG, "fullUpdateStdCarKeyModel begin.");

        // Validate parameters.
        HwWalletObjectUtil.validateModel(object);

        // Update the std car key model.
        String urlSegment = "/v2/key_stdcar/model/";
        JSONObject responseModel = serverApiService.fullUpdateHwWalletObject(urlSegment, object.getPassStyleIdentifier(), object);
        LogUtil.info(TAG, "Updated std car key model: " + JSONObject.toJSONString(responseModel));
    }

    /**
     * Update a std car key model.
     * Run the "createStdCarKeyModel" test before running this test.
     * PATCH http://xxx/hmspass/v2/key_stdcar/model/{modelId}
     */
    public void partialUpdateStdCarKeyModel(HwWalletObject object) {
        LogUtil.info(TAG, "partialUpdateStdCarKeyModel begin.");

        // ID of the std car key model you want to update.
        String modelId = object.getPassStyleIdentifier();

        // Update the std car key model.
        String urlSegment = "/v2/key_stdcar/model/";
        JSONObject responseModel = serverApiService.partialUpdateHwWalletObject(urlSegment, modelId, object);
        LogUtil.info(TAG, "Updated std car key model: " + JSONObject.toJSONString(responseModel));
    }
}
