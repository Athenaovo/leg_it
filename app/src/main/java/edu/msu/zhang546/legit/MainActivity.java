package edu.msu.zhang546.legit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private static final String RUNNING = "running";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        // Read running state from preferences

        // Saving variables
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean running = settings.getBoolean(RUNNING, false);

        if (running) {
            resumeGame();
        }
    }

    private void resumeGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(RUNNING, false);
        startActivity(intent);
        this.finish();
    }

    public void onStartGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(RUNNING, true);
        startActivity(intent);
        this.finish();
    }

    public void onHowtoPlay(View view){
        AlertDialog dialog = new AlertDialog.Builder(this).create();

        // Set custom title
        TextView title = new TextView(this);
        title.setText(getString(R.string.tutorial_title));
        title.setPadding(20, 15, 20, 15);   // Set Position
        title.setGravity(Gravity.START);
        title.setTextColor(Color.BLACK);
        title.setTextSize(18);
        dialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(this);
        // Message Properties
        msg.setText(getString(R.string.tutorials));
        msg.setPadding(20, 10, 20, 0);   // Set Position
        msg.setGravity(Gravity.START);
        dialog.setView(msg);

        // Set Button
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Closes dialog
            }
        });

        new Dialog(getApplicationContext());
        dialog.show();
    }
}
