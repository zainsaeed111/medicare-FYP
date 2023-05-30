package com.example.mediworld.Models;

public class MyMessage {
    private String text;
    private String time;
    private String sender;
    private String receiver;

    public MyMessage() {
        // Default constructor required for Firebase database operations
    }

    public MyMessage(String text, String time, String sender, String receiver) {
        this.text = text;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
    }

    // Getters and setters for the message fields

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
