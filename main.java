
class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Alice", "white");
        Player player2 = new Player("Bob", "black");

        Gameboard gameboard = new Gameboard(player1, player2);
        gameboard.printBoard();
        gameboard.movePiece(5, 3, 4, 3);
        gameboard.printBoard();
    }
}