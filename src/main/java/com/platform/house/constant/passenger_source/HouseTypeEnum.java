package com.platform.house.constant.passenger_source;

import java.util.Map;
import java.util.TreeMap;

public enum  HouseTypeEnum {

    TYPE1(1, "住宅"),
    TYPE2(2, "别墅"),
    TYPE3(3, "写字楼"),
    TYPE4(4, "商铺"),
    TYPE5(5, "厂房"),
    TYPE6(6, "仓库"),
    TYPE7(7, "车位"),
    TYPE8(8, "土地");

    HouseTypeEnum(Integer id, String name) {
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
        for (HouseTypeEnum typeEnum : values()) {
            map.put(typeEnum.getId(), typeEnum.getName());
        }
        return map;
    }
}
