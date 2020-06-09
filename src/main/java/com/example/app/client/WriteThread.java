package com.example.app.client;

import com.example.app.payload.entity.Payload;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

public class WriteThread extends Thread {

    private PrintWriter writer;

    public WriteThread(Socket socket) {

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        var scanner = new Scanner(System.in);

        System.out.print("Enter userId: ");
        String userId = scanner.nextLine();

        System.out.print("Enter friends(comma separated integers): ");
        String friendsStr = scanner.nextLine();

        Payload payload;
        if (friendsStr.trim().length() > 0) {
            payload = new Payload(Integer.parseInt(
                    userId),
                    Stream.of(friendsStr.split(",")).mapToInt(Integer::parseInt).toArray()
            );
        } else {
            int[] friends = new int[0];
            payload = new Payload(Integer.parseInt(
                    userId),
                    friends
            );
        }


        String json = new Gson().toJson(payload);
        System.out.printf("Sending JSON payload to server: %s", json);

        writer.println(json);
    }
}