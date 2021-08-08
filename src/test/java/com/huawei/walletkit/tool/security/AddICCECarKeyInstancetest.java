package com.huawei.walletkit.tool.security;

import com.alibaba.fastjson.JSONObject;
import com.huawei.walletkit.tool.security.manager.addinstance.AddICCECarKeyInstance;
import com.huawei.walletkit.tool.security.model.hwobject.HwWalletObject;
import com.huawei.walletkit.tool.security.model.hwobject.LinkDevicePass;
import com.huawei.walletkit.tool.security.util.AesUtils;
import com.huawei.walletkit.tool.security.util.ConfigUtil;
import com.huawei.walletkit.tool.security.util.LogUtil;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2021/8/8 12:27
 */
public class AddICCECarKeyInstancetest {
    private static final String TAG = "AddICCECarKeyInstancete";

    private String serialNumber = "3B03FC78F499E91D934AE0EA2D3156D6";

    @Test
    public void addInstance() {
        // Read a std car key instance from a JSON file.
        HwWalletObject instance = JSONObject.parseObject(
                ConfigUtil.readFile("StdCarKeyInstance.json"), HwWalletObject.class);
        String serialNumber = AesUtils.getAesKey(16);
        LogUtil.info(TAG, "serialNumber:" + serialNumber);

        instance.setSerialNumber(serialNumber);
        instance.setOrganizationPassId(serialNumber);
        LinkDevicePass devicePass = instance.getLinkDevicePass();
        devicePass.setSerialNumber(serialNumber);
        LogUtil.info(TAG, JSONObject.toJSONString(instance));

        AddICCECarKeyInstance.getInstance().addStdCarKeyInstance(instance);
    }

    @Test
    public void getInstance() {
        HwWalletObject instance = JSONObject.parseObject(
                ConfigUtil.readFile("StdCarKeyInstance.json"), HwWalletObject.class);
        instance.setSerialNumber(serialNumber);
        AddICCECarKeyInstance.getInstance().getStdCarKeyInstance(instance);
    }

    @Test
    public void getInstanceList() {

    }

    @Test
    public void partialUpdateInstance() {
        // Read a HwWalletObject from a JSON file. This HwWalletObject will merge with the corresponding instance.
        HwWalletObject instance = JSONObject.parseObject(
                ConfigUtil.readFile("PartialUpdateStdCarKeyInstance.json"), HwWalletObject.class);

        instance.setSerialNumber(serialNumber);
        instance.setOrganizationPassId(serialNumber);
        LinkDevicePass devicePass = instance.getLinkDevicePass();
        devicePass.setSerialNumber(serialNumber);

        AddICCECarKeyInstance.getInstance().partialUpdateStdCarKeyInstance(instance);
    }

    @Test
    public void fullUpdateInstance() {
        // Read a HwWalletObject from a JSON file. This HwWalletObject will overwrite the corresponding instance.
        HwWalletObject instance = JSONObject.parseObject(
                ConfigUtil.readFile("FullUpdateStdCarKeyInstance.json"), HwWalletObject.class);

        instance.setSerialNumber(serialNumber);
        instance.setOrganizationPassId(serialNumber);
        LinkDevicePass devicePass = instance.getLinkDevicePass();
        devicePass.setSerialNumber(serialNumber);

        AddICCECarKeyInstance.getInstance().fullUpdateStdCarKeyInstance(instance);
    }
}
