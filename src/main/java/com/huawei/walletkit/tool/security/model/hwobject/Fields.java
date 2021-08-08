package com.huawei.walletkit.tool.security.model.hwobject;

import java.util.List;

/**
 * @Description
 * @Author Zhang DeZhou
 * @Since 2021/8/8 13:09
 */
public class Fields {
    private Status status;

    private List<ValueObject> appendFields;

    private List<ValueObject> timeList;

    private List<ValueObject> commonFields;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ValueObject> getAppendFields() {
        return appendFields;
    }

    public void setAppendFields(List<ValueObject> appendFields) {
        this.appendFields = appendFields;
    }

    public List<ValueObject> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<ValueObject> timeList) {
        this.timeList = timeList;
    }

    public List<ValueObject> getCommonFields() {
        return commonFields;
    }

    public void setCommonFields(List<ValueObject> commonFields) {
        this.commonFields = commonFields;
    }
}
