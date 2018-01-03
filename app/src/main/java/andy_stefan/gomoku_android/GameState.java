package andy_stefan.gomoku_android;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andy on 12/29/2017.
 */

public class GameState {

    // represents a piece moved onto the board. Stores the row and column defining the exact
    // place on the board.
    public static class Move {
        public int row, col;

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // writes the move to a String that can be restored via restoreFromString()
        // simply equal to row and column, each of which will not be more than one character
        public String saveToString() {
            return Integer.toString(row) + Integer.toString(col);
        }

        // creates Move from String
        public static Move restoreFromString(String savedMove) {
            return new Move((int) savedMove.charAt(0), (int) savedMove.charAt(1));
        }
    }

    public final static int MAX_BOARD_SIZE = 15;
    private int currentPlayer = 1;
    private int boardSize;

    private BoardType boardType = BoardType.CLASSIC;
    private PieceType piecePlayer1 = PieceType.CLASSIC_WHITE;
    private PieceType piecePlayer2 = PieceType.CLASSIC_BLACK;

    // id used to save and restore this game
    private int gameId;
    // represents pieces on the board. 0 = empty, 1 = white, 2 = black
    public int [][] board;
    // moves that have been played so far in the game
    List<Move> playedMoves = new LinkedList<>();

    // initialize board array with given dimensions
    public GameState(int boardSize) {
        this.boardSize = boardSize;
        board = new int[boardSize][boardSize];
    }

    public boolean makeMove(Move m) {
        return makeMove(m.row, m.col);
    }

    // adds a piece to the board, played by currentPlayer
    public boolean makeMove(int i, int j){
        Log.d("GameState", "Making Move at " + i + ", " + j);
        if(validMove(i,j)){
            board[i][j] = currentPlayer;
            playedMoves.add(new Move(i, j));
            currentPlayer = (currentPlayer == 1 ? 2 : 1);
            return true;
        } else {
            return false;
        }
    }

    // checks whether a piece can be placed at (i, j) by making sure there isn't already a piece there
    public boolean validMove(int i, int j) {
        return board[i][j] == 0;
    }

    //Define Whether the game is over
    public boolean isGameOver(int i, int j, int color) {
        int [] rowSums = {1,1,1,1}; //horizontal, vertical, topLeftDia, botLeftDia
        //Check each direction
        //Left
        boolean keepGoing = true;
        int nextLeft = i-1;
        while(keepGoing && nextLeft >=0 ){
            if(board[nextLeft][j]==color){
                rowSums[0]+=1;
                if(checkRowSums(rowSums)){
                    return true;
                }
                nextLeft-=1;
            }
            else { //if the next Left value is not same color
                keepGoing = false;
            }
        }

        //Right
        keepGoing = true;
        int nextRight = i+1;
        while(keepGoing && nextRight <= MAX_BOARD_SIZE-1){
            if(board[nextRight][j]==color){
                rowSums[0]+=1;
                if(checkRowSums(rowSums)){
                    return true;
                }
                nextRight+=1;
            }
            else { //if the next Right value is not same color
                keepGoing = false;
            }
        }

        //Down (Actually goes up in the board)
        keepGoing = true;
        int nextDown = j-1;
        while(keepGoing && nextDown >= 0){
            if(board[i][nextDown]==color){
                rowSums[1]+=1;
                if(checkRowSums(rowSums)){
                    return true;
                }
                nextDown-=1;
            }
            else { //if the next Up value is not same color
                keepGoing = false;
            }
        }

        //Up (actually goes down in the board)
        keepGoing = true;
        int nextUp = j+1;
        while(keepGoing && nextUp <= MAX_BOARD_SIZE-1){
            if(board[i][nextUp]==color){
                rowSums[1]+=1;
                if(checkRowSums(rowSums)){
                    return true;
                }
                nextUp+=1;
            }
            else { //if the next Up value is not same color
                keepGoing = false;
            }
        }

        return false;
    }

    public boolean checkRowSums(int[] rowSums) {
        for (int i=0; i < rowSums.length; i++) {
            if(rowSums[i] >=5) {
                return true;
            }
        }
        return false;
    }

    public void drawBoard() {
        for (int i =0; i < MAX_BOARD_SIZE; i++) {
            for(int l = 0; l <MAX_BOARD_SIZE; l++) {
                if(board[i][l]==0){
                    System.out.println("0");
                }
                else
                    System.out.println(board[i][l]);
            }
        }
    }

    /*
    A GameState is saved in the following format:
    BoardType toString()
    player1 PieceType toString()
    player2 PieceType toString()
    [line-separated Moves]
     */
    // saves current game state to a string, that can be restored later
    public String saveToString() {
        String saved = "";
        saved += boardType.toString() + "\n";
        saved += piecePlayer1.toString() + "\n";
        saved += piecePlayer2.toString() + "\n";
        for (Move m : playedMoves) {
            saved += m.saveToString() + "\n";
        }
        return saved;
    }

    // restores game from given String
    public static GameState restoreFromString(String savedGame) throws IllegalArgumentException {
        GameState restored = new GameState(19);
        // split into lines
        String[] lines = savedGame.split("\n");
        restored.boardType = BoardType.valueOf(lines[0]);
        restored.piecePlayer1 = PieceType.valueOf(lines[1]);
        restored.piecePlayer2 = PieceType.valueOf(lines[2]);
        for (int i = 3; i < lines.length; i++) {
            restored.playedMoves.add(Move.restoreFromString(lines[i]));
        }
        return restored;
    }
}
