package com.example.mediworld.Models;

public class PharmaciesMainItemModel {
    private String category;
    private String company;
    private String description;
    private String discount;
    private int discountedPrice;
    private String imageUrl;
    private String name;
    private String subcategory;
    private String productId;
    private int price;

    public PharmaciesMainItemModel() {
        // Empty constructor needed for Firebase serialization
    }

    public PharmaciesMainItemModel(String category, String company, String description, String discount, int discountedPrice, String imageUrl, String name, String subcategory, String productId, int price) {
        this.category = category;
        this.company = company;
        this.description = description;
        this.discount = discount;
        this.discountedPrice = discountedPrice;
        this.imageUrl = imageUrl;
        this.name = name;
        this.subcategory = subcategory;
        this.productId = productId;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
