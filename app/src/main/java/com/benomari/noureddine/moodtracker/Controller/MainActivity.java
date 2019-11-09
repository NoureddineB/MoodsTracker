package com.benomari.noureddine.moodtracker.Controller;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.benomari.noureddine.moodtracker.Model.Mood;
import com.benomari.noureddine.moodtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private int mCurrentMood;
    private GestureDetectorCompat mDetector;
    private SharedPreferences mPreferences;


    @BindView(R.id.main_layout) ConstraintLayout constraintLayout;
    @BindView(R.id.mood_smiley) ImageView mood_smiley;
    @BindView(R.id.mood_comment) ImageView mood_comment_button;
    @BindView(R.id.mood_history) ImageView mood_history_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this,this);
        ButterKnife.bind(this);
    }

    private void changeMood(int currentMood){
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
        return true;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (motionEvent.getY() > motionEvent1.getY()) {
            changeMood(--mCurrentMood);
            Toast.makeText(this,"Sa glisse",Toast.LENGTH_SHORT).show();
        }
        if (motionEvent.getY() < motionEvent1.getY()) {
            Toast.makeText(this,"Sa glisse aussi",Toast.LENGTH_SHORT).show();
            changeMood(++mCurrentMood);
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

