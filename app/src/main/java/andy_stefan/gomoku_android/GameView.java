package andy_stefan.gomoku_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
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
    private int boardSize = 9;
    private Paint paint = new Paint();

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attributes) {
        super(context, attributes);
//        setFocusable(true);
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
                selection = true;
                x = event.getX();
                y = event.getY();
                selectedRow = (int) (event.getY() / tileHeight);
                selectedCol = (int) (event.getX() / tileWidth);
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
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        boardWidth = w;
        boardHeight = h;
        Log.d("HealthBarView.java", "Size changed to " + boardWidth + "," + boardHeight);
        tileWidth = boardWidth / boardSize;
        tileHeight = boardHeight / boardSize;
    }
}
