package com.huawei.walletkit.tool.security;

import com.alibaba.fastjson.JSONObject;
import com.huawei.walletkit.tool.security.manager.addinstance.AddICCECarKeyInstance;
import com.huawei.walletkit.tool.security.manager.addinstance.AddICCECarKeyModel;
import com.huawei.walletkit.tool.security.model.AddInstanceRequest;
import com.huawei.walletkit.tool.security.model.hwobject.HwWalletObject;
import com.huawei.walletkit.tool.security.util.ConfigUtil;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2021/8/8 12:27
 */
public class AddICCECarKeyModeltest {
    @Test
    public void addModel() {
        // Read a std car key model from a JSON file.
        HwWalletObject model = JSONObject.parseObject(
                ConfigUtil.readFile("StdCarKeyModel.json"), HwWalletObject.class);
        AddICCECarKeyModel.getInstance().createStdCarKeyModel(model);
    }

    @Test
    public void getModel() {
        AddICCECarKeyModel.getInstance().getStdCarKeyModel("icceModel");
    }

    @Test
    public void getModelList() {

    }

    @Test
    public void partialUpdateModel() {
        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding model.
        HwWalletObject model = JSONObject.parseObject(
                ConfigUtil.readFile("PartialUpdateStdCarKeyModel.json"), HwWalletObject.class);
        AddICCECarKeyModel.getInstance().partialUpdateStdCarKeyModel(model);
    }

    @Test
    public void fullUpdateModel() {
        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding model.
        HwWalletObject model = JSONObject.parseObject(
                ConfigUtil.readFile("FullUpdateStdCarKeyModel.json"), HwWalletObject.class);
        AddICCECarKeyModel.getInstance().fullUpdateStdCarKeyModel(model);
    }
}
