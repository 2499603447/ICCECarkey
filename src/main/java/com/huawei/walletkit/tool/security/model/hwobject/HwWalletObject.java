package com.huawei.walletkit.tool.security.model.hwobject;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2021/8/8 13:04
 */
public class HwWalletObject {
    private String passVersion;

    private String organizationName;

    private String passTypeIdentifier;

    private String passStyleIdentifier;

    private String organizationPassId;

    private String serialNumber;

    private Fields fields;

    private LinkDevicePass linkDevicePass;

    public String getPassVersion() {
        return passVersion;
    }

    public void setPassVersion(String passVersion) {
        this.passVersion = passVersion;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPassTypeIdentifier() {
        return passTypeIdentifier;
    }

    public void setPassTypeIdentifier(String passTypeIdentifier) {
        this.passTypeIdentifier = passTypeIdentifier;
    }

    public String getPassStyleIdentifier() {
        return passStyleIdentifier;
    }

    public void setPassStyleIdentifier(String passStyleIdentifier) {
        this.passStyleIdentifier = passStyleIdentifier;
    }

    public String getOrganizationPassId() {
        return organizationPassId;
    }

    public void setOrganizationPassId(String organizationPassId) {
        this.organizationPassId = organizationPassId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public LinkDevicePass getLinkDevicePass() {
        return linkDevicePass;
    }

    public void setLinkDevicePass(LinkDevicePass linkDevicePass) {
        this.linkDevicePass = linkDevicePass;
    }
}
