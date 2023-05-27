package com.example.mediworld.Models;

public class ChatModel {
    private String Message;
    private String senderId;
    private Long timeStamp;

    public ChatModel(String message, String senderId, Long timeStamp) {
        this.Message = message;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
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
}
