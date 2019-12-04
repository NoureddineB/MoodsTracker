package com.benomari.noureddine.moodtracker.Model;

public class Mood {
    private int mMood;
    private String mMessageMood;

    public static final int MOOD_SAD = 0;
    public static final int MOOD_DISAPPOINTED = 1;
    public static final int MOOD_NORMAL = 2;
    public static final int MOOD_HAPPY = 3;
    public static final int MOOD_SUPER_HAPPY = 4;
    public static final String NO_MESSAGE = "";

    public Mood(int mood,String messageMood) {
        mMood = mood;
        mMessageMood = messageMood;
    }
    public int getMood() {
        return mMood;
    }

    public void setMood(int mood) {
        mMood = mood;
    }


    public String getMessageMood() {
        return mMessageMood;
    }

    public void setMessageMood(String messageMood) {
        mMessageMood = messageMood;
    }


}