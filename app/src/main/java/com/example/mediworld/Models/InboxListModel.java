package com.example.mediworld.Models;

public class InboxListModel {
    private String shopId;
    private String shopName;
    private String message;
    private String chatRoomId;
    private String messageId;

    public InboxListModel() {}

    public InboxListModel(String pharmacyId, String shopName, String message, String chatRoomId, String messageId) {
        this.shopId = pharmacyId;
        this.shopName = shopName;
        this.message = message;
        this.chatRoomId = chatRoomId;
        this.messageId = messageId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
