package com.huawei.walletkit.tool.security.model;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2022/8/14 16:13
 */
public class NotifyCallbackResponse {
  private String returnCode;

  private String returnDesc;

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnDesc() {
    return returnDesc;
  }

  public void setReturnDesc(String returnDesc) {
    this.returnDesc = returnDesc;
  }
}
