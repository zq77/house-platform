package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum TotalPriceToEnum {

    TYPE1(1, "75"),
    TYPE2(2, "100"),
    TYPE3(3, "125"),
    TYPE4(4, "150"),
    TYPE5(5, "175"),
    TYPE6(6, "200"),
    TYPE7(7, "250"),
    TYPE8(8, "不限");

    TotalPriceToEnum(Integer id, String name) {
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
        for (TotalPriceToEnum toEnum : values()) {
            map.put(toEnum.getId(), toEnum.getName());
        }
        return map;
    }
}
