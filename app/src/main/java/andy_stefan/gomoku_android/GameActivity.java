package andy_stefan.gomoku_android;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    // keys for values in Bundle this activity receives
    public static final String BOARDTYPE_KEY = "BOARD_TYPE_KEY";
    public static final String PLAYER_1_PIECE_TYPE_KEY = "PLAYER_1_PIECE_TYPE_KEY";
    public static final String PLAYER_2_PIECE_TYPE_KEY = "PLAYER_2_PIECE_TYPE_KEY";

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        gameView = (GameView) findViewById(R.id.game_view);

        // read configuration from the Bundle
        Bundle args = getIntent().getExtras();

        try {
            gameView.setGameBoard(BoardType.valueOf(args.getString(BOARDTYPE_KEY)));
            gameView.setPlayer1Piece(PieceType.valueOf(args.getString(PLAYER_1_PIECE_TYPE_KEY)));
            gameView.setPlayer2Piece(PieceType.valueOf(args.getString(PLAYER_2_PIECE_TYPE_KEY)));
        } catch (NullPointerException|IllegalArgumentException e) {
            throw new IllegalArgumentException("GameActivity requires a Bundle with valid " +
                    "GAMEMODE_KEY and DIFFICULTY_KEY params");
        }

        gameView.setGameBoard(BoardType.DRAGON_1);
    }
}
