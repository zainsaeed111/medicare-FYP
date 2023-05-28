package com.example.mediworld.Models;

public class ChatMessage {
    private String mText;
    private boolean mIsSentByUser;
    public ChatMessage(String text, boolean isSentByUser) {
        mText = text;
        mIsSentByUser = isSentByUser;
    }
    public String getText() {
        return mText;
    }
    public boolean isSentByUser() {
        return mIsSentByUser;
    }
}