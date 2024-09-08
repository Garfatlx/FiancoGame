import java.util.ArrayList;
import java.util.List;

public class Bot {
    private Player botplayer; // The player this bot is playing as
    private Gameboard state; // The gameboard this bot is playing on

    public Bot(Player player,Gameboard state) {
        if(player.getColor().equals("white")){
            Player player2 = new Player("rival", "black");
        }else{
            Player player2 = new Player("rival", "white");
        }
        
        this.botplayer = player;
        this.state = state;
    }

    // Negamax search function
    public int negamax(Gameboard state, int depth, int color) {
        if (isGameOver(state) || depth == 0) {
            return color * evaluateBoard(state);
        }

        int maxEval = Integer.MIN_VALUE;
        for (int[] move : generateMoves(state)) {
            applyMove(state, move);
            int eval = -negamax(state, depth - 1, -color);
            undoMove(state, move);
            maxEval = Math.max(maxEval, eval);
        }
        return maxEval;
    }

    // Check if the game is over
    private boolean isGameOver(Gameboard state) {
        // Implement game over logic
        return false;
    }

    // Evaluate the board state
    private int evaluateBoard(Gameboard state) {
        // Implement board evaluation logic
        return 0;
    }

    // Generate all possible moves
    private List<int[]> generateMoves(Gameboard state) {

        // Implement move generation logic
        return state.possibleMoves(botplayer);
    }

    // Apply a move to the board
    private void applyMove(Gameboard state, int[] move) {
        // Implement move application logic
    }

    // Undo a move
    private void undoMove(Gameboard state, int[] move) {
        // Implement move undo logic
    }
}
