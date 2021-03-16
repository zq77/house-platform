package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum BuildingToAreaEnum {

    TYPE1(1, "70"),
    TYPE2(2, "90"),
    TYPE3(3, "100"),
    TYPE4(4, "120"),
    TYPE5(5, "140"),
    TYPE6(6, "160"),
    TYPE7(7, "180"),
    TYPE8(8, "200"),
    TYPE9(9, "不限");

    BuildingToAreaEnum(Integer id, String name) {
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
        for (BuildingToAreaEnum area : values()) {
            map.put(area.getId(), area.getName());
        }
        return map;
    }

}
