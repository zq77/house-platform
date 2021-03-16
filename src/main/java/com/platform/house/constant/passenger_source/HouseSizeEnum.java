package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum HouseSizeEnum {

    TYPE1(1, "1 室"),
    TYPE2(2, "2 室"),
    TYPE3(3, "3 室"),
    TYPE4(4, "4 室"),
    TYPE5(5, "5 室"),
    TYPE6(6, "5 室以上");

    HouseSizeEnum(Integer id, String name) {
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
        for (HouseSizeEnum sizeEnum : values()) {
            map.put(sizeEnum.getId(), sizeEnum.getName());
        }
        return map;
    }

}
