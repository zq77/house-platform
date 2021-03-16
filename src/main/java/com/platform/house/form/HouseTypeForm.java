package com.platform.house.form;


public class HouseTypeForm {

    private Integer roomCount;

    private Integer hallCount;

    private Integer kitchenCount;

    private Integer bathroomCount;

    private Double floorage;

    private Double price;

    private Double totalPrice;

    private String imageUrl;

    private String sellingStatus;


    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getHallCount() {
        return hallCount;
    }

    public void setHallCount(Integer hallCount) {
        this.hallCount = hallCount;
    }

    public Integer getKitchenCount() {
        return kitchenCount;
    }

    public void setKitchenCount(Integer kitchenCount) {
        this.kitchenCount = kitchenCount;
    }

    public Integer getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(Integer bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public Double getFloorage() {
        return floorage;
    }

    public void setFloorage(Double floorage) {
        this.floorage = floorage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
    }
}
