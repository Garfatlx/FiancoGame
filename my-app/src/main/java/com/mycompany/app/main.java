package com.mycompany.app;

import java.lang.reflect.Array;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Alice", 1);
        Player player2 = new Player("Bob", -1);

        Gameboard gameboard = new Gameboard(player1, player2);
        gameboard.printBoard();
        gameboard.movePiece(5, 3, 4, 3);
        gameboard.printBoard();
        gameboard.movePiece(3, 3, 3, 2);
        gameboard.printBoard();
        gameboard.undoMove(3, 3, 3, 2);
        gameboard.printBoard();
        // ArrayList<int[]> possibleCaptures = gameboard.possibleMoves();
        // for(int[] capture : possibleCaptures){
        //     System.out.println("Capture: " + capture[0] + ", " + capture[1] + " to " + capture[2] + ", " + capture[3]);
        // }

        
    }
}