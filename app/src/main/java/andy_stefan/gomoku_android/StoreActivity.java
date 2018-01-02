package andy_stefan.gomoku_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class StoreActivity extends AppCompatActivity {

    private ImageButton skyscraperButton, volcanoButton, rainforestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_screen);
        Log.d("Debug", "hello?");

        skyscraperButton = (ImageButton) findViewById(R.id.skyscraper_button);
        volcanoButton = (ImageButton) findViewById(R.id.volcano_button);
        rainforestButton = (ImageButton) findViewById(R.id.rainforest_button);
    }


    public void onSkyscraperPressed(View view) {
        Log.d("StorePage", "Skyscraper button pressed");
    }

    public void onVolcanoPressed(View view) {
        Log.d("StorePage", "Volcano button pressed");
    }

    public void onRainforestPressed(View view) {
        Log.d("StorePage", "Rainforest button pressed");
    }

}
