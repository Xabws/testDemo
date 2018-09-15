package com.example.a1234.miracle.eventbus;


public class MessageEvent {
    public String message;
    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
