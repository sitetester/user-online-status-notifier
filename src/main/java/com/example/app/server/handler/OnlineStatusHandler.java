package com.example.app.server.handler;

import com.example.app.payload.entity.OnlineStatus;
import com.example.app.payload.entity.TCPClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

public class OnlineStatusHandler {

    public static boolean isFriend(int userId, int[] friends) {
        return Arrays.stream(friends).anyMatch(i -> i == userId);
    }

    private PrintWriter getWriter(Socket socket) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }

        return writer;
    }

    public void sendConnectMsgToOnlineFriends(Map<Integer, TCPClient> onlineClients, TCPClient tcpClient) {

        onlineClients.forEach((k, friend) -> {
            if (isFriend(tcpClient.getPayload().userId, friend.getPayload().friends)) {
                System.out.printf("Sending `online` msg to friend (userId): %d\n", friend.getPayload().userId);

                var onlineStatus = new OnlineStatus(tcpClient.getPayload().userId, true);
                var json = new Gson().toJson(onlineStatus);
                var writer = getWriter(friend.getSocket());
                writer.println(json);
            }
        });
    }

    public void sendDisconnectMsgToOnlineFriends(Map<Integer, TCPClient> onlineClients, TCPClient offlineUser) {

        System.out.printf("\nuserId:%d is now offline", offlineUser.getPayload().userId);
        onlineClients.remove(offlineUser.getPayload().userId);
        System.out.printf("\nTotal online clients: %d\n", onlineClients.size());

        onlineClients.forEach((k, friend) -> {
            if (isFriend(friend.getPayload().userId, offlineUser.getPayload().friends)) {
                System.out.printf("\nSending `offline` msg to friend (userId): %d\n", friend.getPayload().userId);

                var onlineStatus = new OnlineStatus(offlineUser.getPayload().userId, false);

                var json = new Gson().toJson(onlineStatus);
                var writer = getWriter(friend.getSocket());
                writer.println(json);
            }
        });
    }

}
