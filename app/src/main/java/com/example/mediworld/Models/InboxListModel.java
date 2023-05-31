package com.example.mediworld.Models;

public class InboxListModel {
    private String senderId;
    private Long timeStamp;
    private String message;
    private String shopID;
    private String chatRoom;


    public InboxListModel() {}

    public InboxListModel(String senderId, Long timeStamp, String message, String shopID, String chatRoom) {
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.message = message;
        this.shopID = shopID;
        this.chatRoom = chatRoom;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }
}
