package com.platform.house.tim;

import java.util.Map;

public class IMActionResult {
    private Map<String, Object> result;

    public IMActionResult(Map<String, Object> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return "OK".equals(getActionStatus());
    }

    public String getActionStatus() {
        return (String) result.get("ActionStatus");
    }


    public String getErrorInfo() {
        return (String) result.get("ErrorInfo");
    }


    public int getErrorCode() {
        return (int) result.get("ErrorCode");
    }


    public Map<String, Object> getResult() {
        return result;
    }

    public Object getObject(String name) {
        return result.get(name);
    }

    public <T> T get(String name) {
        return (T) result.get(name);
    }
}
