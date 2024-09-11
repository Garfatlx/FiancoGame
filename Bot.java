import java.util.ArrayList;
import java.util.List;

public class Bot {
    private Player botplayer; // The player this bot is playing as
    private Gameboard state; // The gameboard this bot is playing on

    public Bot(Player player,Gameboard state) {
        if(player.getColor()== 'w'){
            Player player2 = new Player("rival", 'b');
        }else{
            Player player2 = new Player("rival", 'w');
        }
        
        this.botplayer = player;
        this.state = state;
    }

    // Negamax search function
    public int negamax(Gameboard state, int alpha, int beta, int depth) {
        if (isGameOver(state) || depth == 0) {
            return evaluateBoard(state);
        }
        int score = Integer.MIN_VALUE;
        for (int[] move : generateMoves(state)) {
            applyMove(state, move);
            int eval = -negamax(state, -beta,-alpha, depth - 1);
            undoMove(state, move);
            if(eval > score){
                score = eval;
            }
            if(score > alpha){
                alpha = score;
            }
            if(alpha >= beta){
                break;
            }
        }
        return score;
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
        return state.possibleMoves();
    }

    // Apply a move to the board
    private void applyMove(Gameboard state, int[] move) {
        state.movePiece(move[0], move[1], move[2], move[3]);
    }

    // Undo a move
    private void undoMove(Gameboard state, int[] move) {
        // Implement move undo logic
        state.undoMove(move[0], move[1], move[2], move[3]);
    }
}
