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
    private ArrayList<Mood> mMoods = new ArrayList<>();





    @BindView(R.id.main_layout) ConstraintLayout constraintLayout;
    @BindView(R.id.mood_smiley) ImageView mood_smiley;
    @BindView(R.id.button) Button button;
    @BindView(R.id.mood_comment) ImageView mood_comment_button;
    @BindView(R.id.mood_history) ImageView mood_history_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureAlarm();
        mCurrentMood = 3;
        mDetector = new GestureDetectorCompat(this,this);
        ButterKnife.bind(this);
        preferences = this.getSharedPreferences("PREFS",0);



    }
    public void buttonClick(View view){
        Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(historyActivityIntent);
        }
    public void commentClick(View view){
        showCommentDialog();
    }

    public void addMood(){
        SharedPreferences prefs = this.getSharedPreferences("PREFS",0);
        int onScreenMood = prefs.getInt("Mood", 3);
        String comment = prefs.getString("Comment","") ;
        makeMoodList(mMoods,prefs,onScreenMood, comment);
        Log.d("TAG",String.valueOf(mMoods));
    }

    public void addMoodButton(View view){
        SharedPreferences prefs = this.getSharedPreferences("PREFS",0);
        int onScreenMood = prefs.getInt("Mood", 3);
        String comment = prefs.getString("Comment","") ;
        makeMoodList(mMoods,prefs,onScreenMood, comment);
        Log.d("TAG",String.valueOf(mMoods));
    }
    private void clearList(ArrayList<Mood> moods){
        if (moods.size() > 6){
            moods.clear();
        }

    }


    private void makeMoodList(ArrayList<Mood> moods,SharedPreferences prefs,int moodToSave, String messageToSave){
        Mood mood = new Mood(moodToSave,messageToSave);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        moods.add(mood);
        String json = gson.toJson(moods);
        editor.putString("MoodList", json);
        editor.apply();
        clearList(moods);


    }



    private void changeCurrentMood(int currentMood){
        if (currentMood < 0){
            currentMood = 0;
        } else if (currentMood > 4){
            currentMood = 4;
        }
        mCurrentMood = currentMood;
        switch(currentMood){
            case Mood.MOOD_SAD :
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                mood_smiley.setImageResource(R.mipmap.smiley_sad);
                break;
            case Mood.MOOD_DISAPPOINTED :
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                mood_smiley.setImageResource(R.mipmap.smiley_disappointed);
                break;
            case Mood.MOOD_NORMAL :
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                mood_smiley.setImageResource(R.mipmap.smiley_normal);
                break;
            case Mood.MOOD_HAPPY :
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                mood_smiley.setImageResource(R.mipmap.smiley_happy);
                break;
            case Mood.MOOD_SUPER_HAPPY :
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                mood_smiley.setImageResource(R.mipmap.smiley_super_happy);
                break;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Mood", mCurrentMood);
        editor.apply();



    }
    private void configureAlarm(){

        alarmMgr = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.setClass(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);


        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        Toast.makeText(this,"ALARM SET",Toast.LENGTH_SHORT).show();



    }
    private void showCommentDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Commentaire");
        final EditText edittext = new EditText(this);
        if (!(preferences.getString("Comment",Mood.NO_MESSAGE).equals(""))){
            edittext.setText(preferences.getString("Comment",Mood.NO_MESSAGE));
        }else{
            edittext.setHint("Entrez votre commentaire");
            edittext.requestFocus();
        }
        builder.setView(edittext)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferences.edit().putString("Comment",edittext.getText().toString()).apply();
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
    public boolean onTouchEvent(MotionEvent event){
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
            changeCurrentMood(mCurrentMood-1);
            Toast.makeText(this,String.valueOf(preferences.getInt("Mood",0)),Toast.LENGTH_SHORT).show();
        }
        if (motionEvent.getY() < motionEvent1.getY()) {
            Toast.makeText(this,String.valueOf(preferences.getInt("Mood",0)),Toast.LENGTH_SHORT).show();
            changeCurrentMood(mCurrentMood+1);
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

