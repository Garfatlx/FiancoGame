package FiancoGame;
public class Gameboard{

    private Piece[][] board;

    public Gameboard() {
        board = new Piece[9][9];
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize pieces on the board for a standard checker setup
        for (int i = 0; i < 3; i++) {
            for (int j = (i % 2); j < 8; j += 2) {
                board[i][j] = new Piece("black", i, j);
                board[7 - i][j] = new Piece("white", 7 - i, j);
            }
        }
    }

    public void printBoard() {
        // Print the board to the console
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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
            // Check for kinging
            if ((piece.getColor().equals("white") && endX == 0) || (piece.getColor().equals("black") && endX == 7)) {
                piece.king();
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

}