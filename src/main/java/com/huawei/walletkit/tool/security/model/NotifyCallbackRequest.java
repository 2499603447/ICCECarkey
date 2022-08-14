package com.huawei.walletkit.tool.security.model;

/**
 * @Description 开卡、删卡的结果方向通知
 * @Author Zhang DeZhou
 * @Since 2022/8/14 16:13
 */
public class NotifyCallbackRequest {
  /**
   * 事件ID。若钱包服务器多次重试发送回调通知，开发者服务器可以用此参数去重。
   */
  private String eventId;

  /**
   * 卡券对应的服务号。
   */
  private String passTypeIdentifier;

  /**
   * 卡券号，即“serialNumber”。说明：中国大陆卡券包方式生成的卡券，此参数为“organizationPassId”。
   */
  private String passNumber;

  /**
   * 事件发生时间（UTC时间）："yyyy-MM-dd'T'HH:mm:ss:SSS'Z'"
   */
  private String eventTime;

  /**
   * 事件类型：“RECEIVE_CARD”：用户在华为钱包App中领取卡券。“DELETE_CARD”：用户在华为钱包App中删除卡券。“TASK_RESULT_NOTIFY”：定时任务通知。
   */
  private String eventType;

  /**
   * 帐号及设备关联的唯一标识。
   */
  private String pushToken;

  /**
   * 场景类型：
   * “USER_OPERATION_DELETE_CARD”：用户在华为钱包App里删卡，或用户在华为钱包App退出华为帐号触发删卡。
   * “RESTORE_FACTORY_SETTINGS”：用户恢复手机出厂设置触发删卡。
   * “REMOTE_DELETE_CARD”：华为服务器删卡，如用户挂失场景。
   * “THIRD_PARTY_DELETE_CARD ”：开发者删卡。
   */
  private String sceneType;

  /**
   * NFC事件对应的标识，用于调用用户使用NFC能力后推送消息接口。此标识有效时间为3分钟。
   */
  private String noticeToken;

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getPassTypeIdentifier() {
    return passTypeIdentifier;
  }

  public void setPassTypeIdentifier(String passTypeIdentifier) {
    this.passTypeIdentifier = passTypeIdentifier;
  }

  public String getPassNumber() {
    return passNumber;
  }

  public void setPassNumber(String passNumber) {
    this.passNumber = passNumber;
  }

  public String getEventTime() {
    return eventTime;
  }

  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getPushToken() {
    return pushToken;
  }

  public void setPushToken(String pushToken) {
    this.pushToken = pushToken;
  }

  public String getSceneType() {
    return sceneType;
  }

  public void setSceneType(String sceneType) {
    this.sceneType = sceneType;
  }

  public String getNoticeToken() {
    return noticeToken;
  }

  public void setNoticeToken(String noticeToken) {
    this.noticeToken = noticeToken;
  }
}
