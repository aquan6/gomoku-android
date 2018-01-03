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
    private int boardSize = 15;
    // handles game logic
    private GameState gameState = new GameState(boardSize);
    private Paint paint = new Paint();
    // image of the game board
    private Bitmap gameBoardBmp;

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
        gameBoardBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.classic_board);
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
                selectedRow = (int) (event.getY() / tileHeight);
                selectedCol = (int) (event.getX() / tileWidth);
                if (selectedRow < boardSize && selectedCol < boardSize) {
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
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, boardWidth, boardHeight, paint);

        // fill in selected tile
        if (selection) {
            Log.d("GameView", "Drawing Selection");
            paint.setColor(Color.CYAN);
            canvas.drawRect(selectedCol * tileWidth, selectedRow * tileHeight,
                    (selectedCol + 1) * tileWidth, (selectedRow + 1) * tileHeight, paint);
        }

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(boardHeight * 0.01f);
        float x, y;
        // draw horizontal lines
        for (int i = 0; i <= boardSize; i++) {
            y = i * tileHeight;
            canvas.drawLine(0, y, boardWidth, y, paint);
        }
        // draw vertical lines
        for (int j = 0; j <= boardSize; j++) {
            x = j * tileWidth;
            canvas.drawLine(x, 0, x, boardHeight, paint);
        }

        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameState.board[i][j] == 1) {
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(j * tileWidth, i * tileHeight, (j + 1) * tileWidth, (i + 1) * tileHeight, paint);
                } else if (gameState.board[i][j] == 2) {
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(j * tileWidth, i * tileHeight, (j + 1) * tileWidth, (i + 1) * tileHeight, paint);
                }
            }
        }

        canvas.drawBitmap(gameBoardBmp, 0, 0, null);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        boardWidth = w;
        boardHeight = h;
        Log.d("HealthBarView.java", "Size changed to " + boardWidth + "," + boardHeight);
        tileWidth = boardWidth / boardSize;
        tileHeight = boardHeight / boardSize;
        // scale image of board to the correct size, but keep it square
        gameBoardBmp = Bitmap.createScaledBitmap(gameBoardBmp, boardWidth, boardWidth, false);
    }
}
