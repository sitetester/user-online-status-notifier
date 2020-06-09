package com.example.app.payload.entity;

public class OnlineStatus {

    private int userId;
    private boolean online;

    public OnlineStatus(int userId, boolean online) {
        this.userId = userId;
        this.online = online;
    }
}
