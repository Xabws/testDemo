package com.example.a1234.miracle.eventbus;


public class MessageEvent {
    public int message;
    public MessageEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
