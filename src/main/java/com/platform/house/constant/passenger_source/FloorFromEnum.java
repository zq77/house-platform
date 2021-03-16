package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum FloorFromEnum {

    TYPE1(1, "1"),
    TYPE2(2, "2"),
    TYPE3(3, "3"),
    TYPE4(4, "5"),
    TYPE5(5, "7"),
    TYPE6(6, "10"),
    TYPE7(7, "15"),
    TYPE8(8, "20"),
    TYPE9(9, "25");

    FloorFromEnum(Integer id, String name) {
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
        for (FloorFromEnum areas : values()) {
            map.put(areas.getId(), areas.getName());
        }
        return map;
    }
}
