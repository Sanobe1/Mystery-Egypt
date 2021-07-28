package com.misteryegypt.prize;



import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends AppCompatActivity {

    private TextView mLoadingText;
    private int ProgressStatus = 0;

    ImageView View = null;

    TextView timerTxt;
    Button pauseButton;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;
    boolean timerStarted = false;

    private int countPair = 0;
    private int cardsOpen = 0;
    private TextView mScore;

    int[] drawable = new int[]{
            R.drawable.el1,
            R.drawable.el2,
            R.drawable.el3,
            R.drawable.el4,
            R.drawable.el5,
            R.drawable.el6
    };

    int[] position = {
            0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5
    };

    int currentPosition = -1;

    private long LastClickTime = 0;

    private ImageButton PauseScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shuffleImages();

        GridView gridView = (GridView) findViewById(R.id.gridView);

        PicAdapter imageAdapter = new PicAdapter(this);
        gridView.setAdapter(imageAdapter);


        mScore = (TextView) findViewById(R.id.Score);
        mScore.setVisibility(android.view.View.INVISIBLE);

        PauseScreen = (ImageButton) findViewById(R.id.pauseScreen);
        PauseScreen.setVisibility(android.view.View.INVISIBLE);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int pos, long id) {

                if (currentPosition < 0) {
                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - LastClickTime;
                    LastClickTime = currentClickTime;
                    if (elapsedTime <= 1000) {
                        return;
                    }

                    currentPosition = pos;
                    View = (ImageView) view;
                    View.setImageResource(drawable[position[pos]]);
                    cardsOpen = 1;
                } else {
                    if (currentPosition == pos) {
                        return;
                    }

                    cardsOpen = 2;
                    if (position[currentPosition] != position[pos]) {
                        ((ImageView) view).setImageResource(drawable[position[pos]]);


                        ((ImageView) view).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                View.setImageResource(R.drawable.el9);
                                ((ImageView) view).setImageResource(R.drawable.el9);
                            }
                        }, 1000);
                    } else if (position[currentPosition] == position[pos]) {
                        View.setImageResource(drawable[position[pos]]);
                        ((ImageView) view).setImageResource(drawable[position[pos]]);
                        countPair++;


                        ((ImageView) view).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                timerTxt.setVisibility(android.view.View.INVISIBLE);
                                ((ImageView) view).setVisibility(android.view.View.INVISIBLE);
                            }
                        }, 1000);

                        if (countPair == 6) {
                            Toast toast2 = Toast.makeText(getApplicationContext(), "You Win! Go to Menu", Toast.LENGTH_LONG);
                            toast2.setGravity(0, 0, 0);
                            toast2.show();

                            timerStarted = false;
                            timer.cancel();
                        }
                    }
                    currentPosition = -1;
                    cardsOpen = 0;
                }
            }
        });


        timerTxt = (TextView) findViewById(R.id.Timer);
        pauseButton = (Button) findViewById(R.id.pauseButton);


        timer = new Timer();
        startTimer();
    }

    public void pauseTapped(View view) {
        GridView gv = findViewById(R.id.gridView);

        if (!timerStarted) {
            timerStarted = true;




            startTimer();

            PauseScreen.setVisibility(android.view.View.INVISIBLE);
            pauseButton.setVisibility(android.view.View.VISIBLE);
        } else {
            timerStarted = false;

            timerTask.cancel();

            PauseScreen.setVisibility(android.view.View.VISIBLE);
            pauseButton.setVisibility(android.view.View.INVISIBLE);


        }

    }

    private void startTimer() {
        timerStarted = true;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        time++;
                        timerTxt.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }


    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    private void shuffleImages() {
        int noOfElements = position.length;
        for (int i = 0; i < noOfElements; i++) {
            int s = i + (int) (Math.random() * (noOfElements - i));

            int temp = position[s];
            position[s] = position[i];
            position[i] = temp;
        }
    }

    public void onClick(View v) {

        Button button = findViewById(v.getId());

        switch (button.getTag().toString()) {

            case "MenuButton": {
                startActivity(new Intent(this, StartActivity.class));
                finish();
                break;
            }
        }
    }
}