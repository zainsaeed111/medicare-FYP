package com.example.mediworld.Shop;

public class Product {

    private String category;
    private String subcategory;
    private String name;
    private String description;
    private String company;
    private int price;
    private String discount;
    private int discountedPrice;
    private String imageUrl;
    private String productId;
    private String productPrice;

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public Product(String productId, String productName, String productPrice, String category, String subcategory, String name, String description, String company, int price, String discount, int discountedPrice, String imageUrl) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.description = description;
        this.company = company;
        this.price = price;
        this.discount = discount;
        this.discountedPrice = discountedPrice;
        this.imageUrl = imageUrl;
    }

    public Product(String productId, String category, String subcategory, String productName, String productDescription, String company, int price, String discount, int discountedPrice, String imageUri) {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}