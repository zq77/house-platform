package com.platform.house.spider;

/**
 * Created by Office on 2018/12/27.
 */
public class SpiderReflectObject {

    SpiderReflectObject(String[] label, String method, Class paramType) {
        this.label = label;
        this.method = method;
        this.paramType = paramType;
    }

    private String[] label;

    private String method;

    private Class paramType;

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class getParamType() {
        return paramType;
    }

    public void setParamType(Class paramType) {
        this.paramType = paramType;
    }
}
