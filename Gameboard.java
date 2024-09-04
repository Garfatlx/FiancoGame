
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
        return true;
    }

    public boolean checkWinCondition() {
        // Check for win condition
        return false;
    }

    public void endGame(Player player) {
        System.out.println(player.getName() + " wins!");
        // End the game
    }   

}