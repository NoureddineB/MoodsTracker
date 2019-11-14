package com.benomari.noureddine.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;


import com.benomari.noureddine.moodtracker.Model.Mood;

import com.google.gson.Gson;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AlarmReceiver extends BroadcastReceiver {


    private ArrayList<Mood> moods = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("PREFS",0);
        int onScreenMood = prefs.getInt("Mood", 3);
        String comment = prefs.getString("Comment","") ;
        Mood mood = new Mood(onScreenMood,todayDate(),comment);
        makeMoodList(mood,moods,prefs,onScreenMood, comment);
        Log.e("TAGRECEIVER", "onReceive marche");
        Log.e("TAGRECEIVERMOODLIST",String.valueOf(moods));



    }
    private String todayDate(){
        DateFormat df = DateFormat.getDateInstance();
        return df.format(Calendar.getInstance().getTime());
    }
    private void clearList(ArrayList<Mood> moods){
        if (moods.size() > 6){
            moods.clear();
        }

    }
    private void makeMoodList(Mood mood,ArrayList<Mood> moods,SharedPreferences prefs,int moodToSave, String messageToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        moods.add(mood);
        String json = gson.toJson(moods);
        editor.putString("MoodList", json);
        editor.apply();
        clearList(moods);

    }

}