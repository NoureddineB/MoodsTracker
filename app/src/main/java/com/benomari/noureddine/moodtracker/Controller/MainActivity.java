package com.benomari.noureddine.moodtracker.Controller;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.benomari.noureddine.moodtracker.Model.Mood;
import com.benomari.noureddine.moodtracker.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private int mCurrentMood;
    private GestureDetectorCompat mDetector;
    private SharedPreferences preferences;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    @BindView(R.id.main_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.mood_smiley)
    ImageView mood_smiley;
    @BindView(R.id.mood_comment)
    ImageView mood_comment_button;
    @BindView(R.id.mood_history)
    ImageView mood_history_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureAlarm();
        mCurrentMood = 3;
        mDetector = new GestureDetectorCompat(this, this);
        ButterKnife.bind(this);
        preferences = this.getSharedPreferences("PREFS", 0);


    }

    public void buttonClick(View view) {
        Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(historyActivityIntent);
    }

    public void commentClick(View view) {
        showCommentDialog();
    }


    private void changeCurrentMood(int currentMood) {
        if (currentMood < 0) {
            currentMood = 0;
        } else if (currentMood > 4) {
            currentMood = 4;
        }
        mCurrentMood = currentMood;
        switch (currentMood) {
            case Mood.MOOD_SAD:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                mood_smiley.setImageResource(R.drawable.smiley_sad);
                MediaPlayer mp0 = MediaPlayer.create(this, R.raw.sad);
                mp0.setVolume(1.0f,1.0f);
                mp0.start();

                break;
            case Mood.MOOD_DISAPPOINTED:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                mood_smiley.setImageResource(R.drawable.smiley_disappointed);
                MediaPlayer mp1 = MediaPlayer.create(this, R.raw.dissapointed);
                mp1.setVolume(1.0f,1.0f);
                mp1.start();

                break;
            case Mood.MOOD_NORMAL:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                mood_smiley.setImageResource(R.drawable.smiley_normal);
                MediaPlayer mp2 = MediaPlayer.create(this, R.raw.normal);
                mp2.setVolume(1.0f,1.0f);
                mp2.start();

                break;
            case Mood.MOOD_HAPPY:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                mood_smiley.setImageResource(R.drawable.smiley_happy);
                MediaPlayer mp3 = MediaPlayer.create(this, R.raw.happy);
                mp3.setVolume(1.0f,1.0f);
                mp3.start();

                break;
            case Mood.MOOD_SUPER_HAPPY:
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                mood_smiley.setImageResource(R.drawable.smiley_super_happy);
                MediaPlayer mp4 = MediaPlayer.create(this, R.raw.superhappy);
                mp4.setVolume(1.0f,1.0f);
                mp4.start();

                break;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Mood", mCurrentMood);
        editor.apply();


    }

    private void configureAlarm() {
        alarmMgr = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.setClass(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

    private void showCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Commentaire");
        final EditText edittext = new EditText(this);
        if (!(preferences.getString("Comment", Mood.NO_MESSAGE).equals(""))) {
            edittext.setText(preferences.getString("Comment", Mood.NO_MESSAGE));
        } else {
            edittext.setHint("Entrez votre commentaire");
            edittext.requestFocus();
        }
        builder.setView(edittext)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferences.edit().putString("Comment", edittext.getText().toString()).apply();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (motionEvent.getY() > motionEvent1.getY()) {
            changeCurrentMood(mCurrentMood - 1);

        }
        if (motionEvent.getY() < motionEvent1.getY()) {
            changeCurrentMood(mCurrentMood + 1);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }


}

