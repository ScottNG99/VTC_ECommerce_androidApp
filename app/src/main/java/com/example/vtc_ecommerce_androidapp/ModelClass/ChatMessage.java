package com.example.vtc_ecommerce_androidapp.ModelClass;

import java.util.Date;

public class ChatMessage {
    private String message;
    private boolean isUser;
    private Date sentAt;

    public ChatMessage(String message, boolean isUser, Date sentAt) {
        this.message = message;
        this.isUser = isUser;
        this.sentAt = sentAt;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUser() {
        return isUser;
    }

    public Date getTime() {
        return sentAt;
    }
}
