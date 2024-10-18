package com.mycompany.app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.net.InetSocketAddress;

class Main {
    public static void main(String[] args) {
        int port = 8887; // Port to listen on
        MyWebSocketServer server = new MyWebSocketServer(new InetSocketAddress(port));
        server.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}