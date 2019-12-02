package com.benomari.noureddine.moodtracker.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.os.Build;
import android.util.Log;
import android.view.View;


import com.benomari.noureddine.moodtracker.Model.Mood;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;


public class AlarmReceiver extends BroadcastReceiver {

    private ArrayList<Mood> mMoods = new ArrayList<>();
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Gson gson = new Gson();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAGITWORK","sa marche");
        SharedPreferences prefs = context.getSharedPreferences("PREFS",0);
        int onScreenMood = prefs.getInt("Mood", 3);
        String comment = prefs.getString("Comment","") ;
        String jsonMood = prefs.getString("MoodList", "");
        if (!jsonMood.equals("")) {
            Type type = new TypeToken<ArrayList<Mood>>() {
            }.getType();
            ArrayList<Mood> moods1 = gson.fromJson(jsonMood, type);
            mMoods.addAll(moods1);
        }
        makeMoodList(mMoods,prefs,onScreenMood, comment);
        Log.d("TAG",String.valueOf(mMoods));
        checkVersion(context);


    }

    private void checkVersion(Context context){
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent2 = new Intent(context, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent2, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }



    private void clearList(ArrayList<Mood> mMoods){
        if (mMoods.size() > 6){
            mMoods.clear();
        }

    }
    private void makeMoodList(ArrayList<Mood> mMoods,SharedPreferences prefs,int moodToSave, String messageToSave){
        Mood mood = new Mood(moodToSave,messageToSave);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        mMoods.add(mood);
        String json = gson.toJson(mMoods);
        editor.putString("MoodList", json);
        editor.apply();
        clearList(mMoods);


    }
}


