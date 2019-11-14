package com.benomari.noureddine.moodtracker.Model;

public class Mood {
    private int mMood;
    private String mDateMood;
    private String mMessageMood;

    public static final int MOOD_SAD = 0;
    public static final int MOOD_DISAPPOINTED = 1;
    public static final int MOOD_NORMAL = 2;
    public static final int MOOD_HAPPY = 3;
    public static final int MOOD_SUPER_HAPPY = 4;
    public static final String NO_MESSAGE = "";

    public Mood(int mood, String dateMood, String messageMood) {
        mMood = mood;
        mDateMood = dateMood;
        mMessageMood = messageMood;
    }
    public int getMood() {
        return mMood;
    }

    public void setMood(int mood) {
        mMood = mood;
    }

    public String getDateMood() {
        return mDateMood;
    }

    public void setDateMood(String dateMood) {
        mDateMood = dateMood;
    }

    public String getMessageMood() {
        return mMessageMood;
    }

    public void setMessageMood(String messageMood) {
        mMessageMood = messageMood;
    }


}