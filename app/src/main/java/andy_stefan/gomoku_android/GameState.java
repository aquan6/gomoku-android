package andy_stefan.gomoku_android;

import android.util.Log;

/**
 * Created by Andy on 12/29/2017.
 */

public class GameState {
    public final static int MAX_BOARD_SIZE = 15;
    private int currentPlayer = 1;
    private int boardSize;
//Define what is the state of the game
    public int [][] board;
    //0 = empty, 1 = white, 2=black
    private int numPieces = 0;

    // initialize board array with given dimensions
    public GameState(int boardSize) {
        this.boardSize = boardSize;
        board = new int[boardSize][boardSize];
    }

    //define a makeMove function:
    public boolean makeMove(int i, int j){
        Log.d("GameState", "Making Move at " + i + ", " + j);
        if(validMove(i,j)){
            board[i][j] = currentPlayer;
            numPieces+=1;
            currentPlayer = (currentPlayer == 1 ? 2 : 1);
            return true;
        }
        else{
            return false;
        }
    }

    //Define Valid Moves
    public boolean validMove(int i, int j) {
        if(board[i][j] != 0) {
            return false;
        }
        return true;
    }

    //Define Whether the game is over
    public boolean checkGameOver(int i, int j, int color) {
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

}
