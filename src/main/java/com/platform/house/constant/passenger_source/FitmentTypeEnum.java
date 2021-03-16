package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum FitmentTypeEnum {

    TYPE1(1, "毛坯"),
    TYPE2(2, "简装"),
    TYPE3(3, "普装"),
    TYPE4(4, "精装"),
    TYPE5(5, "豪装");

    FitmentTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<Integer, Object> toJson() {
        Map<Integer, Object> map = new TreeMap<>();
        for (FitmentTypeEnum typeEnum : values()) {
            map.put(typeEnum.getId(), typeEnum.getName());
        }
        return map;
    }
}
