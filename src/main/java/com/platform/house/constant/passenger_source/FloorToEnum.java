package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum FloorToEnum {

    TYPE1(1, "3"),
    TYPE2(2, "6"),
    TYPE3(3, "9"),
    TYPE4(4, "12"),
    TYPE5(5, "15"),
    TYPE6(6, "18"),
    TYPE7(7, "20"),
    TYPE8(8, "25"),
    TYPE9(9, "30"),
    TYPE10(10, "不限");

    FloorToEnum(Integer id, String name) {
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
        for (FloorToEnum areas : values()) {
            map.put(areas.getId(), areas.getName());
        }
        return map;
    }
}
