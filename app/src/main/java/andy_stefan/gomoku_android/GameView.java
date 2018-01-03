package andy_stefan.gomoku_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Stefan on 12/29/2017.
 */

public class GameView extends View {

    // dimensions (px) of the board
    private int boardWidth, boardHeight;
    // dimensions (px) of each tile
    private float tileWidth, tileHeight;
    // whether user has selected a tile
    private boolean selection = false;
    // row and col of selected tile
    private int selectedRow, selectedCol;
    // board is boardSize x boardSize tiles
    private int boardSize = 19;
    // handles game logic
    private GameState gameState = new GameState(boardSize);
    // board and pieces being used
    private BoardType boardType = BoardType.CLASSIC;
    private PieceType player1Piece = PieceType.CLASSIC_WHITE;
    private PieceType player2Piece = PieceType.CLASSIC_BLACK;
    // image of board background, board overlay, and pieces
    private Bitmap gameBoardBmp, boardOverlayBmp, player1PieceBmp, player2PieceBmp;

    public GameView(Context context) {
        super(context);
        init(context);
    } // todo: variable R.drawable id that can be used for the board. use attributes

    public GameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        init(context);
    }

    // initializes resources required
    private void init(Context context) {
        gameBoardBmp = BitmapFactory.decodeResource(context.getResources(), boardType.getDrawableId());
        boardOverlayBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.board_lines);
        player1PieceBmp = BitmapFactory.decodeResource(context.getResources(), player1Piece.getDrawableId());
        player2PieceBmp = BitmapFactory.decodeResource(context.getResources(), player2Piece.getDrawableId());
    }

    // sets board to given boardType, and loads the specified image. TODO: scale to correct size
    public void setGameBoard(BoardType boardType) {
        this.boardType = boardType;
        gameBoardBmp = BitmapFactory.decodeResource(getResources(), boardType.getDrawableId());
    }

    // set player 1 piece to given PieceType, and load specified image
    public void setPlayer1Piece(PieceType player1Piece) {
        this.player1Piece = player1Piece;
        player1PieceBmp = BitmapFactory.decodeResource(getResources(), player1Piece.getDrawableId());
    }

    // set player 2 piece to given PieceType, and load specified image
    public void setPlayer2Piece(PieceType player2Piece) {
        this.player2Piece = player2Piece;
        player2PieceBmp = BitmapFactory.decodeResource(getResources(), player2Piece.getDrawableId());
    }

    // convert coordinate on canvas to tile. Returns -1 if no tile was selected
    private int canvasToTileX(float x) {
        if (x < 27 || x > 453) {
            return -1;
        } else {
            return (int) ((x - 27) / 25);
        }
    }

    private int canvasToTileY(float y) {
        if (y < 27 || y > 453) {
            return -1;
        } else {
            return (int) ((y - 27) / 25);
        }
    }

    private float tileToCanvasX(int x) {
        return 39 + 25 * x;
    }

    private int tileToCanvasY(int y) {
        return 39 + 25 * y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x, y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                selectedRow = canvasToTileY(event.getY());
                selectedCol = canvasToTileX(event.getX());
                if (selectedRow > -1 && selectedCol > -1) {
                    selection = true;
                    gameState.makeMove(selectedRow, selectedCol);
                }
                Log.d("GameView", "Touch event at " + x + ", " + y + " converted to selection of " + selectedRow + ", " + selectedCol);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        /*
        The following info is used to calculate positions: todo: relative calculations
        Gridlines are 2px thick
        Grid starts at (14, 14)
        Tiles are 23*23px (excluding grid lines)
        */

        canvas.drawBitmap(gameBoardBmp, 0, 0, null);
        canvas.drawBitmap(boardOverlayBmp, 0, 0, null);

        // draw pieces
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // draw player1 piece
                if (gameState.board[i][j] == 1) {
                    canvas.drawBitmap(player1PieceBmp, tileToCanvasX(j) - player1PieceBmp.getWidth() / 2, tileToCanvasY(i) - player1PieceBmp.getHeight() / 2, null);
                }
                // draw player2 piece centered on the canvas location
                else if (gameState.board[i][j] == 2) {
                    canvas.drawBitmap(player2PieceBmp, tileToCanvasX(j) - player2PieceBmp.getWidth() / 2, tileToCanvasY(i) - player2PieceBmp.getHeight() / 2, null);
                }
            }
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        //boardWidth = w;
        //boardHeight = h;
        //tileWidth = boardWidth / boardSize;
        //tileHeight = boardHeight / boardSize;
        // scale image of board to the correct size, but keep it square
        //gameBoardBmp = Bitmap.createScaledBitmap(gameBoardBmp, boardWidth, boardWidth, false);
        //boardOverlayBmp = Bitmap.createScaledBitmap(boardOverlayBmp, boardWidth, boardWidth, false);
        // todo: resize pieces
    }
}
