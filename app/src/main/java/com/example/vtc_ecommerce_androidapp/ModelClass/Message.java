package com.example.vtc_ecommerce_androidapp.ModelClass;

public class Message {

    private String sender;
    private String receiver;
    private String message;
    private String timestamp;
    private String isRead;

    public Message( String sender, String receiver, String message, String timestamp, String isRead) {

        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
