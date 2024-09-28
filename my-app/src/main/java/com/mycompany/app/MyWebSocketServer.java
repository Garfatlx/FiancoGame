package com.mycompany.app;

import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

public class MyWebSocketServer extends WebSocketServer {

    public MyWebSocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        Player player1 = new Player("Alice", 1);
        Player player2 = new Player("Bob", -1);

        Gameboard gameboard = new Gameboard(player1, player2);
        Gson gson = new Gson();
        String gameboardJson = gson.toJson(gameboard.getBoard());

        // Send the JSON string to the client
        conn.send(gameboardJson);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message from " + conn.getRemoteSocketAddress() + ": " + message);
        conn.send("Message received: " + message); // Echo the message back to the client
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("An error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
    }

    public static void main(String[] args) {
        int port = 8887; // Port to listen on
        MyWebSocketServer server = new MyWebSocketServer(new InetSocketAddress(port));
        server.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}