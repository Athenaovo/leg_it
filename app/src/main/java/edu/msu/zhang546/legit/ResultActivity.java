package edu.msu.zhang546.legit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private long score;
    private long highscore;

    // Saving variables
    private SharedPreferences settings = null;
    private static final String PREFS_NAME = "PrefsFile";
    private static final String RUNNING = "running";
    private static final String HIGHSCORE = "highscore";
    private static final String SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();
    }
    @SuppressWarnings("ConstantConditions")
    private void init(){
        // Read high score
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        highscore = settings.getLong(HIGHSCORE, 0);

        // Get current score
        Bundle extras = getIntent().getExtras();
        score = extras.getLong(SCORE, 0);

        if (score > highscore) {
            highscore = score;
        }

        updateUI();
        writePreferences();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        // Set current score
        TextView scoreText = findViewById(R.id.currentScoreText);
        scoreText.setText(Long.toString(score));

        // Set high score
        TextView highText = findViewById(R.id.highScoreText);
        highText.setText(Long.toString(highscore));
    }

    private void writePreferences(){
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(RUNNING, false);
        editor.putLong(HIGHSCORE, highscore);

        editor.apply();
    }

    public void onReturn(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
