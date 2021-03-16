package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum TotalPriceFromEnum {

    TYPE1(1, "0"),
    TYPE2(2, "25"),
    TYPE3(3, "50"),
    TYPE4(4, "75"),
    TYPE5(5, "100"),
    TYPE6(6, "125"),
    TYPE7(7, "150"),
    TYPE8(8, "175"),
    TYPE9(9, "200"),
    TYPE10(10, "250");

    TotalPriceFromEnum(Integer id, String name) {
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
        for (TotalPriceFromEnum fromEnum : values()) {
            map.put(fromEnum.getId(), fromEnum.getName());
        }
        return map;
    }
}
