package com.example.app.server.handler;

import com.example.app.payload.entity.Payload;
import com.example.app.payload.entity.TCPClient;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class NewClientHandler extends Thread {

    private final Socket socket;
    private final Map<Integer, TCPClient> onlineClients;

    public NewClientHandler(Socket socket, Map<Integer, TCPClient> onlineClients) {

        this.socket = socket;
        this.onlineClients = onlineClients;
    }

    public void run() {
        var onlineClientsHandler = new OnlineStatusHandler();

        try {
            var bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            TCPClient tcpClient = null;

            while (true) {
                var json = bufferedReader.readLine();
                if (json != null) {
                    var payload = new Gson().fromJson(json, Payload.class);
                    System.out.printf("\nClient joined with address & payload: %s, %s", this.socket.getRemoteSocketAddress(), json);

                    tcpClient = new TCPClient()
                            .setPayload(payload)
                            .setSocket(this.socket);

                    this.onlineClients.put(tcpClient.getPayload().userId, tcpClient);
                    System.out.printf("\nTotal online clients: %d\n", onlineClients.size());

                    onlineClientsHandler.sendConnectMsgToOnlineFriends(this.onlineClients, tcpClient);
                } else {
                    if (tcpClient != null) {
                        onlineClientsHandler.sendDisconnectMsgToOnlineFriends(this.onlineClients, tcpClient);
                        break;
                    }
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error in NewClientHandler: " + ioException.getMessage());
        }
    }
}
