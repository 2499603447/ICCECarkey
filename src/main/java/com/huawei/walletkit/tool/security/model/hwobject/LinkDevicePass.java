package com.huawei.walletkit.tool.security.model.hwobject;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2021/8/8 13:09
 */
public class LinkDevicePass {
    private String webServiceURL;

    private String token;

    private String serialNumber;

    private String passVersion;

    private String spPublickey;

    private String nfcType;

    public String getWebServiceURL() {
        return webServiceURL;
    }

    public void setWebServiceURL(String webServiceURL) {
        this.webServiceURL = webServiceURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPassVersion() {
        return passVersion;
    }

    public void setPassVersion(String passVersion) {
        this.passVersion = passVersion;
    }

    public String getSpPublickey() {
        return spPublickey;
    }

    public void setSpPublickey(String spPublickey) {
        this.spPublickey = spPublickey;
    }

    public String getNfcType() {
        return nfcType;
    }

    public void setNfcType(String nfcType) {
        this.nfcType = nfcType;
    }
}
