package com.example.app;

import com.example.app.payload.entity.TCPClient;
import com.example.app.server.handler.NewClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;


public class Server {

    final static int PORT = 8081;

    public static void main(String[] args) {
        new Server().execute();
    }

    public void execute() {

        Map<Integer, TCPClient> onlineClients = new HashMap<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                var socket = serverSocket.accept();
                new NewClientHandler(socket, onlineClients).start();
            }

        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
        }
    }
}
