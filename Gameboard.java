import java.util.ArrayList;

public class Gameboard{

    private Piece[][] board;
    private Player player1;
    private Player player2;

    public Gameboard(Player player1, Player player2) {
        board = new Piece[9][9];
        this.player1 = player1;
        this.player2 = player2;

        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize pieces on the board for a standard checker setup
        
        for (int j = 0; j < 9; j++) {
            board[0][j] = new Piece("black", 0, j);
        }
        board[1][1] = new Piece("black", 1, 1);
        board[1][7] = new Piece("black", 1, 7);
        board[2][2] = new Piece("black", 2, 2);
        board[2][6] = new Piece("black", 2, 6);
        board[3][3] = new Piece("black", 3, 3);
        board[3][5] = new Piece("black", 3, 5);
        for (int j = 0; j < 9; j++) {
            board[8][j] = new Piece("white", 9, j);
        }
        board[7][1] = new Piece("white", 7, 1);
        board[7][7] = new Piece("white", 7, 7);
        board[6][2] = new Piece("white", 6, 2);
        board[6][6] = new Piece("white", 6, 6);
        board[5][3] = new Piece("white", 5, 3);
        board[5][5] = new Piece("white", 5, 5);
    }

    public void printBoard() {
        // Print the board to the console
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j].getColor().charAt(0) + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        // Implement piece movement logic, including capturing and kinging
        Piece piece = board[startX][startY];
        ArrayList<int[]> possibleCaptures = scanCapture(piece.getColor());
        if(possibleCaptures.size() > 0){
            for(int[] capture : possibleCaptures){
                if(capture[0] == startX && capture[1] == startY && capture[2] == endX && capture[3] == endY){
                    performCapture(startX, startY, endX, endY);
                    return true;
                }
            }
            System.out.println("Invalid move. Please perform a capture.");
        }
        if (piece != null && isValidMove(piece, startX, startY, endX, endY)) {
            board[endX][endY] = piece;
            board[startX][startY] = null;
            piece.move(endX, endY);

            if((piece.getColor().equals("white") && endX == 0) ){
                endGame(player1);
            }
            // Check for kinging
            if ((piece.getColor().equals("black") && endX == 7)) {
                endGame(player2);
            }
            return true;
        }
        return false;
    }

    private boolean isValidMove(Piece piece, int startX, int startY, int endX, int endY) {
        // Implement move validation logic
        if(piece.getColor().equals("black")){
            if(endX < startX){
                return false;
            }

        } else {
            if(endX > startX){
                return false;
            }
        }
        return true;
    }

    public ArrayList<int[]> possibleMoves(Player player){
        // Implement scan for possible moves
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        possibleMoves=scanCapture(player.getColor());
        if(possibleMoves.size() > 0){
            System.out.println("You must perform a capture.");
            return possibleMoves;
        }
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != null && board[i][j].getColor().equals(player.getColor())){
                    if(board[i][j].getColor().equals("black")){
                        if(i+1 < 9 && j < 9 && board[i+1][j] == null){
                            possibleMoves.add(new int[]{i, j, i+1, j});
                        } 
                        if(i < 9 && j-1 >= 0 && board[i][j-1] == null){
                            possibleMoves.add(new int[]{i, j, i, j-1});
                        } 
                        if(i < 9 && j+1 < 9 && board[i][j+1] == null){
                            possibleMoves.add(new int[]{i, j, i, j+1});
                        } 
                    } else if(board[i][j].getColor().equals("white")){
                        if(i-1 >= 0 && j < 9 && board[i-1][j] == null){
                            possibleMoves.add(new int[]{i, j, i-1, j});
                        } 
                        if(i >= 0 && j+1 < 9 && board[i][j+1] == null){
                            possibleMoves.add(new int[]{i, j, i, j+1});
                        }
                        if(i >= 0 && j-1 >= 0 && board[i][j-1] == null){
                            possibleMoves.add(new int[]{i, j, i, j-1});
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }
    public ArrayList<int[]> scanCapture(String color) {
        // Implement scan for possible captures
        ArrayList<int[]> possibleCaptures= new ArrayList<int[]>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != null){
                    if(board[i][j].getColor().equals(color)){
                        if(board[i][j].getColor().equals("black")){
                            if(i+2 < 9 && j+2 < 9 && board[i+2][j+2] == null && board[i+1][j+1] != null && board[i+1][j+1].getColor().equals("white")){
                                possibleCaptures.add(new int[]{i, j, i+2, j+2});
                                
                            } 
                            if(i+2 < 9 && j-2 >= 0 && board[i+2][j-2] == null && board[i+1][j-1] != null && board[i+1][j-1].getColor().equals("white")){
                                possibleCaptures.add(new int[]{i, j, i+2, j-2});
                            }
                        } else if(board[i][j].getColor().equals("white")){
                            if(i-2 >= 0 && j+2 < 9 && board[i-2][j+2] == null && board[i-1][j+1] != null && board[i-1][j+1].getColor().equals("black")){
                                possibleCaptures.add(new int[]{i, j, i-2, j+2});
                            }
                            if(i-2 >= 0 && j-2 >= 0 && board[i-2][j-2] == null && board[i-1][j-1] != null && board[i-1][j-1].getColor().equals("black")){
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
            if(board[midX][midY] != null && !board[midX][midY].getColor().equals(board[startX][startY].getColor())){
                board[midX][midY] = null;
                return true;
            }
        }
        return false;
    }

    public boolean checkWinCondition() {
        // Check for win condition
        return false;
    }

    public void endGame(Player player) {
        System.out.println(player.getName() + " wins!");
        // End the game
    }   

    public Piece[][] getBoard() {
        return board;
    }
}