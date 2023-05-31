package com.example.mediworld.Models;

public class InboxListModel {
    private String senderId;
    private Long timeStamp;
    private String message;


    public InboxListModel() {}

    public InboxListModel(String senderId, Long timeStamp, String message) {
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.message = message;
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
}
