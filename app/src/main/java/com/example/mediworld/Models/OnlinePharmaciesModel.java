package com.example.mediworld.Models;

public class OnlinePharmaciesModel {
    private String regNo;
    private String shopName;
    private String location;

    public OnlinePharmaciesModel() {
        // Required default constructor
    }

    public OnlinePharmaciesModel(String regNo, String shopName, String location) {
        this.regNo = regNo;
        this.shopName = shopName;
        this.location = location;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
