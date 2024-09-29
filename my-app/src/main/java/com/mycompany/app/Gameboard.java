package com.mycompany.app;

import java.util.ArrayList;

public class Gameboard{

    //use int to represent the color of the piece, -1 for black, 1 for white, 0 for empty

    private int[][] board;
    private Player player1;
    private Player player2; 
    private int currentPlayer;
    private boolean gameover = false;
    private Player winner=null;
    private ArrayList<int[]> allMoves = new ArrayList<int[]>();

    public Gameboard(Player player1, Player player2) {
        board = new int[9][9];
        this.player1 = player1;
        this.player2 = player2;

        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize pieces on the board for a standard checker setup
        
        for (int j = 0; j < 9; j++) {
            board[0][j] = -1;
        }
        board[1][1] = -1;
        board[1][7] = -1;
        board[2][2] = -1;
        board[2][6] = -1;
        board[3][3] = -1;
        board[3][5] = -1;
        for (int j = 0; j < 9; j++) {
            board[8][j] = 1;
        }
        board[7][1] = 1;
        board[7][7] = 1;
        board[6][2] = 1;
        board[6][6] = 1;
        board[5][3] = 1;
        board[5][5] = 1;

        this.currentPlayer = 1;
    }

    public void printBoard() {
        // Print the board to the console
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    if (board[i][j] == -1) {
                        System.out.print("b ");
                    } else {
                        System.out.print("w ");
                    }
                }
            }
            System.out.println();
        }
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        // Implement piece movement logic, including capturing and kinging
        int piece = board[startX][startY];
        if(piece == 0){
            System.out.println("Invalid move. Please select a piece.");
            return false;
        }   
        if(piece!=currentPlayer){
            System.out.println("Invalid move. Please select a piece of your own color.");
            return false;
        }
        ArrayList<int[]> possibleCaptures = scanCapture(piece);
        if(possibleCaptures.size() > 0){
            for(int[] capture : possibleCaptures){
                if(capture[0] == startX && capture[1] == startY && capture[2] == endX && capture[3] == endY){
                    performCapture(startX, startY, endX, endY);
                    currentPlayer = (currentPlayer == 1) ? -1 : 1;
                    allMoves.add(new int[]{startX, startY, endX, endY});
                    return true;
                }
            }
            System.out.println("Invalid move. Please perform a capture.");
            return false;
        }
        if (piece != 0 && isValidMove(piece, startX, startY, endX, endY)) {
            board[endX][endY] = piece;
            board[startX][startY] = 0;

            int wincondition=checkWinCondition();
            
            currentPlayer = (currentPlayer == 1) ? -1 : 1;
            allMoves.add(new int[]{startX, startY, endX, endY});
            return true;
        }else{
            System.out.println("invalid move");
        }
        return false;
    }

    public boolean undoMove(int startX, int startY, int endX, int endY) {
        // Implement move undo logic
        int piece = board[endX][endY];
        board[startX][startY] = piece;
        board[endX][endY] = 0;
        if(Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 2){
            board[(startX + endX) / 2][(startY + endY) / 2] = (piece == 1) ? -1 : 1;
        }
        currentPlayer = (currentPlayer == 1) ? -1 : 1;
        allMoves.remove(allMoves.size()-1);
        //piece.move(startX, startY);
        return true;
    }
    private boolean isValidMove(int piece, int startX, int startY, int endX, int endY) {
        // Implement move validation logic
        if(endX<0 || endX>=9 || endY<0 || endY>=9 ){
            return false;
        }
        if(board[endX][endY]!=0){
            return false;
        }
        if(piece==-1){
            if(endX-startX==1){
                if (endY==startY) {
                    return true;
                }
            }
            if(endX==startX){
                if(Math.abs(startY-endY)==1){
                    return true;
                }
            }
        } else {
            if(endX-startX==-1){
                if (endY==startY) {
                    return true;
                }
            }
            if(endX==startX){
                if(Math.abs(startY-endY)==1){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<int[]> possibleMoves(int player){
        // Implement scan for possible moves
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        possibleMoves=scanCapture(player);
        if(possibleMoves.size() > 0){
            return possibleMoves;
        }
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != 0 && board[i][j]==player){
                    if(board[i][j]== -1){
                        if(i+1 < 9 && j < 9 && board[i+1][j] == 0){
                            possibleMoves.add(new int[]{i, j, i+1, j});
                        } 
                        if(i < 9 && j-1 >= 0 && board[i][j-1] == 0){
                            possibleMoves.add(new int[]{i, j, i, j-1});
                        } 
                        if(i < 9 && j+1 < 9 && board[i][j+1] == 0){
                            possibleMoves.add(new int[]{i, j, i, j+1});
                        } 
                    } else if(board[i][j]== 1){
                        if(i-1 >= 0 && j < 9 && board[i-1][j] == 0){
                            possibleMoves.add(new int[]{i, j, i-1, j});
                        } 
                        if(i >= 0 && j+1 < 9 && board[i][j+1] == 0){
                            possibleMoves.add(new int[]{i, j, i, j+1});
                        }
                        if(i >= 0 && j-1 >= 0 && board[i][j-1] == 0){
                            possibleMoves.add(new int[]{i, j, i, j-1});
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }
    public ArrayList<int[]> scanCapture(int color) {
        // Implement scan for possible captures
        ArrayList<int[]> possibleCaptures= new ArrayList<int[]>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != 0){
                    if(board[i][j]==color){
                        if(board[i][j]==-1){
                            if(i+2 < 9 && j+2 < 9 && board[i+2][j+2] == 0 && board[i+1][j+1] != 0 && board[i+1][j+1]==1){
                                possibleCaptures.add(new int[]{i, j, i+2, j+2});
                                
                            } 
                            if(i+2 < 9 && j-2 >= 0 && board[i+2][j-2] == 0 && board[i+1][j-1] != 0 && board[i+1][j-1]==1){
                                possibleCaptures.add(new int[]{i, j, i+2, j-2});
                            }
                        } else if(board[i][j]==1){
                            if(i-2 >= 0 && j+2 < 9 && board[i-2][j+2] == 0 && board[i-1][j+1] != 0 && board[i-1][j+1]==-1){
                                possibleCaptures.add(new int[]{i, j, i-2, j+2});
                            }
                            if(i-2 >= 0 && j-2 >= 0 && board[i-2][j-2] == 0 && board[i-1][j-1] != 0 && board[i-1][j-1]==-1){
                                possibleCaptures.add(new int[]{i, j, i-2, j-2});
                            }
                        }
                    }
                    
                }
            }
        }
        return possibleCaptures;
    }



    private boolean performCapture(int startX, int startY, int endX, int endY){
        // Implement capture logic
        if(Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 2){
            int midX = (startX + endX) / 2;
            int midY = (startY + endY) / 2;
            if(board[midX][midY] != 0 && board[midX][midY]!=board[startX][startY]){
                board[midX][midY] = 0;
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = 0;
                int wincondition=checkWinCondition();
                
                return true;
            }
        }
        return false;
    }

    public int checkWinCondition() {
        for(int i=0; i<9; i++){
            if (board[0][i] == 1) {
                gameover = true;
                endGame(player1);
                return 1;
            }
            if (board[8][i] == -1) {
                gameover = true;
                endGame(player2);
                return -1;
            }
        }
        if(possibleMoves(0-currentPlayer).size() == 0){
            gameover = true;
            endGame((currentPlayer == 1) ? player1 : player2);
            return (currentPlayer == 1) ? 1 : -1;
        }
        // Check for win condition
        return 0;
    }

    public void endGame(Player player) {
        winner = player;
        System.out.println(player.getName() + " wins!");
        // End the game
    }   

    public int[][] getBoard() {
        return board;
    }
    public boolean isGameOver() {
        return gameover;
    }
    public Player getWinner() {
        return winner;
    }
    public ArrayList<int[]> getAllMoves(){
        return allMoves;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] positionConverter(String position) {
        int[] pos = new int[2];
        //convert positon to pos, ex: A1 to 0,0
        pos[0] = 8 - (position.charAt(1) - '1');
        pos[1] = position.charAt(0) - 'A';

        return pos;
    }
}