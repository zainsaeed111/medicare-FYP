package com.example.mediworld.Models;

public class OnlinePharmaciesModel {

    private String shopName;
    private String shopLocation;

    public OnlinePharmaciesModel() {
        // Required default constructor
    }

    public OnlinePharmaciesModel(String shopName, String location) {
        this.shopName = shopName;
        this.shopLocation = location;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLocation() {
        return shopLocation;
    }

    public void setLocation(String location) {
        this.shopLocation = location;
    }
}
