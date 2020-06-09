package com.example.app.payload.entity;

import java.net.Socket;

public class TCPClient {

    private Payload payload;
    private Socket socket;

    public Payload getPayload() {
        return payload;
    }

    public TCPClient setPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public Socket getSocket() {
        return socket;
    }

    public TCPClient setSocket(Socket socket) {
        this.socket = socket;
        return this;
    }
}
