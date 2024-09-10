import java.util.ArrayList;

public class Gameboard{

    private Piece[][] board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Gameboard(Player player1, Player player2) {
        board = new Piece[9][9];
        this.player1 = player1;
        this.player2 = player2;

        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize pieces on the board for a standard checker setup
        
        for (int j = 0; j < 9; j++) {
            board[0][j] = new Piece('b', 0, j);
        }
        board[1][1] = new Piece('b', 1, 1);
        board[1][7] = new Piece('b', 1, 7);
        board[2][2] = new Piece('b', 2, 2);
        board[2][6] = new Piece('b', 2, 6);
        board[3][3] = new Piece('b', 3, 3);
        board[3][5] = new Piece('b', 3, 5);
        for (int j = 0; j < 9; j++) {
            board[8][j] = new Piece('w', 9, j);
        }
        board[7][1] = new Piece('w', 7, 1);
        board[7][7] = new Piece('w', 7, 7);
        board[6][2] = new Piece('w', 6, 2);
        board[6][6] = new Piece('w', 6, 6);
        board[5][3] = new Piece('w', 5, 3);
        board[5][5] = new Piece('w', 5, 5);

        this.currentPlayer = player1;
    }

    public void printBoard() {
        // Print the board to the console
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j].getColor() + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        // Implement piece movement logic, including capturing and kinging
        Piece piece = board[startX][startY];
        if(piece == null){
            System.out.println("Invalid move. Please select a piece.");
            return false;
        }   
        if(piece.getColor()!=currentPlayer.getColor()){
            System.out.println("Invalid move. Please select a piece of your own color.");
            return false;
        }
        ArrayList<int[]> possibleCaptures = scanCapture(piece.getColor());
        if(possibleCaptures.size() > 0){
            for(int[] capture : possibleCaptures){
                if(capture[0] == startX && capture[1] == startY && capture[2] == endX && capture[3] == endY){
                    performCapture(startX, startY, endX, endY);
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                    return true;
                }
            }
            System.out.println("Invalid move. Please perform a capture.");
            return false;
        }
        if (piece != null && isValidMove(piece, startX, startY, endX, endY)) {
            board[endX][endY] = piece;
            board[startX][startY] = null;
            piece.move(endX, endY);

            if((piece.getColor()=='w' && endX == 0) ){
                endGame(player1);
            }
            // Check for kinging
            if ((piece.getColor()=='b' && endX == 7)) {
                endGame(player2);
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            return true;
        }else{
            System.out.println("invalid move");
        }
        return false;
    }

    private boolean isValidMove(Piece piece, int startX, int startY, int endX, int endY) {
        // Implement move validation logic
        if(endX<0 || endX>=9 || endY<0 || endY>=9 ){
            return false;
        }
        if(board[endX][endY]!=null){
            return false;
        }
        if(piece.getColor()=='b'){
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

    public ArrayList<int[]> possibleMoves(){
        // Implement scan for possible moves
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        possibleMoves=scanCapture(currentPlayer.getColor());
        if(possibleMoves.size() > 0){
            return possibleMoves;
        }
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != null && board[i][j].getColor()==currentPlayer.getColor()){
                    if(board[i][j].getColor()== 'b'){
                        if(i+1 < 9 && j < 9 && board[i+1][j] == null){
                            possibleMoves.add(new int[]{i, j, i+1, j});
                        } 
                        if(i < 9 && j-1 >= 0 && board[i][j-1] == null){
                            possibleMoves.add(new int[]{i, j, i, j-1});
                        } 
                        if(i < 9 && j+1 < 9 && board[i][j+1] == null){
                            possibleMoves.add(new int[]{i, j, i, j+1});
                        } 
                    } else if(board[i][j].getColor()== 'w'){
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
    public ArrayList<int[]> scanCapture(char color) {
        // Implement scan for possible captures
        ArrayList<int[]> possibleCaptures= new ArrayList<int[]>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != null){
                    if(board[i][j].getColor()==color){
                        if(board[i][j].getColor()=='b'){
                            if(i+2 < 9 && j+2 < 9 && board[i+2][j+2] == null && board[i+1][j+1] != null && board[i+1][j+1].getColor()=='w'){
                                possibleCaptures.add(new int[]{i, j, i+2, j+2});
                                
                            } 
                            if(i+2 < 9 && j-2 >= 0 && board[i+2][j-2] == null && board[i+1][j-1] != null && board[i+1][j-1].getColor()=='w'){
                                possibleCaptures.add(new int[]{i, j, i+2, j-2});
                            }
                        } else if(board[i][j].getColor()=='w'){
                            if(i-2 >= 0 && j+2 < 9 && board[i-2][j+2] == null && board[i-1][j+1] != null && board[i-1][j+1].getColor()=='b'){
                                possibleCaptures.add(new int[]{i, j, i-2, j+2});
                            }
                            if(i-2 >= 0 && j-2 >= 0 && board[i-2][j-2] == null && board[i-1][j-1] != null && board[i-1][j-1].getColor()=='b'){
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
            if(board[midX][midY] != null && board[midX][midY].getColor()!=board[startX][startY].getColor()){
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
    public Player getCurrentPlayer() {
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