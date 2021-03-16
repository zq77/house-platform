package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum PurposeEnum {

    TYPE1(1, "换房"),
    TYPE2(2, "结婚"),
    TYPE3(3, "就学"),
    TYPE4(4, "投资"),
    TYPE5(5, "其它");

    PurposeEnum(Integer id, String name) {
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
        for (PurposeEnum areas : values()) {
            map.put(areas.getId(), areas.getName());
        }
        return map;
    }
}
