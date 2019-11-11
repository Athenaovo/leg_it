package edu.msu.zhang546.legit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    // Timer variables
    private long endTime;
    private Timer timer;

    // # of milliseconds in 20 minutes
    private static final long interval = 1000 * 60 * 20;

    // Location variables
    private final ActiveListener activeListener = new ActiveListener();
    private LocationManager locationManager = null;
    private double latitude = 0;
    private double longitude = 0;
    private boolean valid = false;

    // Checkpoint variables
    private Checkpoints checkpoints;
    private String checkpointName;
    private double checkpointLat;
    private double checkpointLon;

    // Game variables
    private long score = 0;
    private double distance = 0;
    private boolean running = false;

    // Saving variables
    private SharedPreferences settings = null;
    private static final String PREFS_NAME = "PrefsFile";
    private static final String SCORE = "score";
    private static final String END_TIME = "endtime";
    private static final String CHECKPOINT = "checkpoint";
    private static final String RUNNING = "running";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
    }

    /**
     * Called when this application becomes foreground again.
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerListeners();
        if (timer == null) {
            startTimer(this);
        }
    }

    /**
     * Called when this application is no longer the foreground application.
     */
    @Override
    protected void onPause() {
        unregisterListeners();
        writePreferences();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.onDestroy();
    }

    @SuppressWarnings("ConstantConditions")
    private void init() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Force the screen to say on and bright
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle extras = getIntent().getExtras();

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        running = extras.getBoolean(RUNNING);

        // Set initial checkpoint
        checkpoints = new Checkpoints();

        if (!running) {
            // Resuming previous game
            readPreferences();
            getCheckpoint();
        }
        else{
            getCheckpoint();
            // Start timer
            endTime = System.currentTimeMillis() + interval;
        }

        startTimer(this);
    }

    private void readPreferences() {
        running = settings.getBoolean(RUNNING, false);
        score = settings.getLong(SCORE, 0);
        endTime = settings.getLong(END_TIME, System.currentTimeMillis());
        String name = settings.getString(CHECKPOINT, "");
        checkpoints.setLocation(name);
    }

    private void writePreferences(){
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(RUNNING, running);
        editor.putLong(SCORE, score);
        editor.putLong(END_TIME, endTime);
        editor.putString(CHECKPOINT, checkpointName);

        editor.apply();
    }

    private void startTimer(final GameActivity activity) {
        // Update UI every second for timer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(
                    new Runnable() {
                           @Override
                           public void run() {
                               updateUI();
                           }
                       }
                );
            }
        },0,300);
    }

    private void getCheckpoint() {
        checkpointName = checkpoints.getName();
        checkpointLat = checkpoints.getLatitude();
        checkpointLon = checkpoints.getLongitude();
    }


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void updateUI() {
        // Update score
        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score: " + Long.toString(score));

        // Update checkpoint
        TextView checkpointText = findViewById(R.id.checkpointText);
        checkpointText.setText(checkpointName);

        // Update distance
        TextView distanceText = findViewById(R.id.distanceText);
        if (valid) {
            distanceText.setText(String.format("%1$6.1fm", distance));
        }
        else {
            distanceText.setText("???");
        }

        // Update time
        TextView timeText = findViewById(R.id.timeText);

        long currTime = System.currentTimeMillis();
        long timeDiff = endTime - currTime + 1000;

        if (timeDiff > 1000) {
            double minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff);
            double seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff) % 60;

            timeText.setText(String.format("Time: %1$02.0f:%2$02.0f", minutes, seconds));
        }
        else {
            running = false;
        }

        if (!running) {
            endGame();
        }
    }

    public void onGiveUp(View view) {
        running = false;
    }

    private void endGame() {
        timer.cancel();
        timer.purge();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(SCORE, score);
        startActivity(intent);
        this.finish();
    }

    private void checkpointCaptured() {
        Toast.makeText(GameActivity.this, "Checkpoint captured!", Toast.LENGTH_SHORT).show();
        score += 100;
        checkpoints.choose();
        getCheckpoint();
        checkDistance();
        updateUI();
        writePreferences();
    }

    private void checkDistance() {
        // Get distance
        float[] result = new float[3];
        Location.distanceBetween(latitude, longitude, checkpointLat, checkpointLon, result);
        distance = result[0];

        if(distance <= 100.0) {
            checkpointCaptured();
        }
    }

    private void onLocation(Location location) {
        if(location == null) {
            return;
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        valid = true;

        checkDistance();
        updateUI();
    }

    private class ActiveListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            onLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            registerListeners();
        }
    }

    private void registerListeners() {
        unregisterListeners();

        // Create a Criteria object
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        String bestAvailable = locationManager.getBestProvider(criteria, true);

        if (bestAvailable != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(bestAvailable, 500, 1, activeListener);
            Location location = locationManager.getLastKnownLocation(bestAvailable);
            onLocation(location);
        }
    }

    private void unregisterListeners() {
        locationManager.removeUpdates(activeListener);
    }
}
