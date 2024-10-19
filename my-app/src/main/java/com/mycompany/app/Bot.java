package com.mycompany.app;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Bot {
    private int botplayer; // The player this bot is playing as
    private Gameboard state; // The gameboard this bot is playing on
    private int[] bestMove; // The best move found by the bot
    private int searchdepth = 7; // The depth of the search tree

    private HashMap<Long, TTEntry> transpositionTable = new HashMap<Long, TTEntry>();

    public Bot(Gameboard state) {
       
        this.state = state;
        this.botplayer = state.getCurrentPlayer();
    }

    public int[] generateMoves() {
        
        // Implement bot move generation logic
        negamax(state, Integer.MIN_VALUE, Integer.MAX_VALUE, searchdepth);
        return bestMove;
    }

    // Negamax search function
    public int negamax(Gameboard state, int alpha, int beta, int depth) {
        
        int oldAlpha = alpha;

        long hash = zobristHash(state);
        TTEntry n = retrieve(hash);
        if(n != null && n.depth >= depth){
            if(n.flag == 0){
                return n.score;
            }
            else if(n.flag == 1){
                alpha = Math.max(alpha, n.score);
            }
            else if(n.flag == -1){
                beta = Math.min(beta, n.score);
            }
            if(alpha >= beta){
                return n.score;
            }
        }

        if (isGameOver(state) || depth == 0) {
            return evaluateBoard(state);
        }
        // Null move pruning
        if (depth > 3 ) {
            state.switchPlayer();
            int nullMoveScore = -negamax(state, -beta, -alpha, depth - 1 - 2);
            state.switchPlayer();
            if (nullMoveScore >= beta) {
                return nullMoveScore;
            }
        }
        
        int score = Integer.MIN_VALUE;
        for (int[] move : generateMoves(state)) {
            applyMove(state, move);
            int eval = -negamax(state, -beta,-alpha, depth - 1);
            undoMove(state, move);
            if(eval > score){
                score = eval;
                if(depth == searchdepth){
                    bestMove = move;
                }
                
            }
            if(score > alpha){
                alpha = score;
            }
            if(score >= beta){
                break;
            }
            
            
        }
        int flag;
        if (score<=oldAlpha){
            flag=-1; //upper bound
        }
        else if (score>=beta){
            flag=1; //lower bound
        }
        else{
            flag=0; //exact value
        }
        store(hash, new TTEntry(state.getBoard(), state.getCurrentPlayer(), bestMove, score, depth, flag));
        return score;
    }

    // Check if the game is over
    private boolean isGameOver(Gameboard state) {
        // Implement game over logic
        return state.isGameOver();
    }

    // Evaluate the board state
    private int evaluateBoard(Gameboard state) {
        // Implement board evaluation logic
        int currentpalayer = botplayer;
        int sumscore = 0;

        if(isGameOver(state) && state.getWinner().getColor() == currentpalayer){
            System.out.println("win");
            return -100000;
        }
        else if(isGameOver(state) && state.getWinner().getColor() == 0-currentpalayer){
            System.out.println("lose");
            return 100000;
        }
        //if the current player is the bot player, add score
        if(currentpalayer==state.getCurrentPlayer()){
            sumscore += 100;
        }
        else{
            sumscore -= 100;
        }
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(state.getBoard()[i][j] == currentpalayer){

                    //feature 1: distance to the destination
                    if(currentpalayer == 1){
                        sumscore += Math.pow(2,(8-i));
                    }
                    if(currentpalayer == -1){
                        sumscore += Math.pow(2, i);
                    
                    }
                    //feature 2: number of pieces
                    sumscore += 50;

                }
            }
        }

         //feature 3: number of pieces that can be captured
         sumscore += state.scanCapture(currentpalayer).size()*120;

         //feature 4: number of pieces that can be captured by the opponent
         sumscore -= state.scanCapture(0-currentpalayer).size()*150;

        return -sumscore;
    }

    // Generate all possible moves
    private List<int[]> generateMoves(Gameboard state) {
        List<int[]> moves = new ArrayList<int[]>();
        moves = state.possibleMoves(state.getCurrentPlayer());
        // for(int i=0; i<moves.size(); i++){
        //     System.out.println(moves.get(i)[0] + " " + moves.get(i)[1] + " " + moves.get(i)[2] + " " + moves.get(i)[3]);
        // }
        // Implement move generation logic
        return moves;
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

    private TTEntry retrieve(long hash){
        return transpositionTable.get(hash);
    }
    private void store(long hash, TTEntry entry){
        transpositionTable.put(hash, entry);
        //handle collision
        
    }
    

    private long zobristHash(Gameboard state){
        long hash = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                hash ^= Zobrist.zobristTable[i][j][state.getBoard()[i][j]+1];
            }
        }
        hash ^= Zobrist.zobristTable[9][9][state.getCurrentPlayer()+1];
        return hash;
    }

    private static class Zobrist{
        static long[][][] zobristTable = new long[10][10][3];
        static{
            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    for(int k=0; k<3; k++){
                        zobristTable[i][j][k] = randomLong();
                    }
                }
            }
            zobristTable[9][9][0] = randomLong();
            zobristTable[9][9][2] = randomLong();
        }
        private static long randomLong(){
            return (long) (Math.random() * Long.MAX_VALUE);
        }
    }

    class TTEntry{
        int[][] board;
        int currentPlayer;
        int[] bestMove;
        int depth;
        int score;
        int flag;
        TTEntry(int[][] board, int currentPlayer, int[] bestMove, int score, int depth, int flag){
            this.board = board;
            this.currentPlayer = currentPlayer;
            this.bestMove = bestMove;
            this.depth = depth;
            this.score = score;
            this.flag = flag;
        }
    }

}
