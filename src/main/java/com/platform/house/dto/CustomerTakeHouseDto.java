package com.platform.house.dto;

/**
 * Created by Office on 2019/1/28.
 */
public class CustomerTakeHouseDto {
    private Long id;

    private Long takeId;

    private Long houseId;

    private Long resoldHouseId;

    private String houseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTakeId() {
        return takeId;
    }

    public void setTakeId(Long takeId) {
        this.takeId = takeId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getResoldHouseId() {
        return resoldHouseId;
    }

    public void setResoldHouseId(Long resoldHouseId) {
        this.resoldHouseId = resoldHouseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
}
