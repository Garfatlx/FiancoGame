import java.lang.reflect.Array;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Alice", "white");
        Player player2 = new Player("Bob", "black");

        Gameboard gameboard = new Gameboard(player1, player2);
        gameboard.printBoard();
        gameboard.movePiece(5, 3, 4, 3);
        gameboard.printBoard();
        gameboard.movePiece(4, 3, 4, 4);
        gameboard.printBoard();
        ArrayList<int[]> possibleCaptures = gameboard.possibleMoves(player2);
        for(int[] capture : possibleCaptures){
            System.out.println("Capture: " + capture[0] + ", " + capture[1] + " to " + capture[2] + ", " + capture[3]);
        }
    }
}