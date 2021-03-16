package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum BuildingFromAreaEnum {

    TYPE1(1, "0"),
    TYPE2(2, "50"),
    TYPE3(3, "60"),
    TYPE4(4, "70"),
    TYPE5(5, "80"),
    TYPE6(6, "90"),
    TYPE7(7, "100"),
    TYPE8(8, "120"),
    TYPE9(9, "140"),
    TYPE10(10, "160");

    BuildingFromAreaEnum(Integer id, String name) {
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
        for (BuildingFromAreaEnum areas : values()) {
            map.put(areas.getId(), areas.getName());
        }
        return map;
    }
}
