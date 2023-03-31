package com.example.mediworld;

public class ShopHelperClass {

    String shopName, regNo, phone, location, password, confirmPassword;

    public ShopHelperClass() {
    }

    public ShopHelperClass(String shopName, String regNo, String phone, String location, String password, String confirmPassword) {
        this.shopName = shopName;
        this.regNo = regNo;
        this.phone = phone;
        this.location = location;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
