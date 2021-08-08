package com.huawei.walletkit.tool.security.model.hwobject;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2021/8/8 13:10
 */
public class Status {
    private String state;

    private String effectTime;

    private String expireTime;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
