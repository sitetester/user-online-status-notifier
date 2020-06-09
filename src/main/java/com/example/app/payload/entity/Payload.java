package com.example.app.payload.entity;

public class Payload {

    public int userId;
    public int[] friends;

    public Payload(int userId, int[] friends) {
        this.userId = userId;
        this.friends = friends;
    }
}
