package com.example.app.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {

    private final Socket socket;

    public ReadThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {
            var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                var msg = bufferedReader.readLine();

                if (msg != null) {
                    System.out.printf("\nmsg: %s", msg);
                } else {
                    System.out.print("\n:( Lost connection to server");
                    break;
                }
            }

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
