package com.mycompany.app;

import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

public class MyWebSocketServer extends WebSocketServer {
    private Gameboard gameboard;

    public MyWebSocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        Player player1 = new Player("White", 1);
        Player player2 = new Player("Balck", -1);

        this.gameboard = new Gameboard(player1, player2);
        

        // Send the JSON string to the client
        conn.send(getGameboard());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message from " + conn.getRemoteSocketAddress() + ": " + message);
        if (message.equals("reset")) {
            Player player1 = new Player("White", 1);
            Player player2 = new Player("Balck", -1);

            this.gameboard = new Gameboard(player1, player2);
            conn.send(getGameboard());
            return;
        }
        int[] move;
        if(message.equals("BotMove")){
            Bot bot = new Bot(gameboard);
            System.out.println(gameboard.getCurrentPlayer());
            move = bot.generateMoves();
        }else{
            move = parseFourDigitNumber(message);
        }
        if(gameboard.movePiece(move[0], move[1], move[2], move[3])){
            conn.send(getGameboard());
        }


        //conn.send("Message received: " + message); // Echo the message back to the client
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("An error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
    }

    public int[] parseFourDigitNumber(String number) {
        if (number.length() != 4) {
            throw new IllegalArgumentException("The input string must be exactly 4 characters long.");
        }
        int[] digits = new int[4];
        for (int i = 0; i < 4; i++) {
            digits[i] = Character.getNumericValue(number.charAt(i));
        }
        return digits;
    }

    public String getGameboard() {
        Gson gson = new Gson();
        String gameboardJson = gson.toJson(gameboard.getBoard());
        String msg=" ";
        if (gameboard.isGameOver()) {
            msg=gameboard.getWinner().getName() + " wins!";
        }else{
            if (gameboard.getCurrentPlayer()==1) {
                msg="White's turn";
            }else{
                msg="Black's turn";
            }
        }

        int[][] intBoard = gameboard.getBoard();
        String[][] stringBoard = new String[intBoard.length][intBoard[0].length];

        for (int i = 0; i < intBoard.length; i++) {
            for (int j = 0; j < intBoard[i].length; j++) {
                stringBoard[i][j] = String.valueOf(intBoard[i][j]);
            }
        }

        return gson.toJson(new String[]{gameboardJson, msg});
    }

    public static void main(String[] args) {
        int port = 8887; // Port to listen on
        MyWebSocketServer server = new MyWebSocketServer(new InetSocketAddress(port));
        server.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}