package com.example.app;

import com.example.app.client.ReadThread;
import com.example.app.client.WriteThread;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    final static int SERVER_PORT = 8081;

    public static void main(String[] args) {
        new Client().execute();
    }

    public void execute() {

        try {
            Socket socket = new Socket("localhost", SERVER_PORT);
            System.out.println("Connected to server");

            new ReadThread(socket).start();
            new WriteThread(socket).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
}
