package com.platform.house.tim;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IMActionResponse {
    @JsonProperty("ActionStatus")
    private String ActionStatus;
    @JsonProperty("ErrorInfo")
    private String ErrorInfo;
    @JsonProperty("ErrorCode")
    private int ErrorCode;

    public boolean isSuccess() {
        return "OK".equals(ActionStatus);
    }

    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String actionStatus) {
        ActionStatus = actionStatus;
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        ErrorInfo = errorInfo;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }
}
