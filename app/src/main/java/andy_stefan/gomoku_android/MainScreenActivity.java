package andy_stefan.gomoku_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainScreenActivity extends AppCompatActivity {

    private Button playButton, statsButton, storeButton;
    private ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        //For formatting the images
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        int width= displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        // links java UI elements to main_screen.xml view elements
        playButton = (Button) findViewById(R.id.play_button);
        statsButton = (Button) findViewById(R.id.stats_button);
        storeButton = (Button) findViewById(R.id.store_button);
        settingsButton = (ImageButton) findViewById(R.id.image_button_settings);
    }

    public void onSettingsPressed(View view) {
        Log.d("MainScreen", "Settings button pressed");
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void onPlayPressed(View view) {
        Log.d("MainScreen", "Play button pressed");
        startActivity(new Intent(this, GameActivity.class));
    }

    public void onStatsPressed(View view) {
        Log.d("MainScreen", "Stats button pressed");
        startActivity(new Intent(this, StatsActivity.class));
    }

    public void onStorePressed(View view) {
        Log.d("Mainscreen", "Store button pressed");
        startActivity(new Intent(this, StoreActivity.class));
    }


}
