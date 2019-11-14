package com.benomari.noureddine.moodtracker.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benomari.noureddine.moodtracker.Model.Mood;
import com.benomari.noureddine.moodtracker.R;

import java.util.ArrayList;

public class MoodAdapter extends RecyclerView.Adapter<MoodViewHolder> {

    // FOR DATA
    private ArrayList<Mood> mMoods;

    // CONSTRUCTOR
    public MoodAdapter(ArrayList<Mood> mMoods) {
        this.mMoods = mMoods;
    }

    @Override
    public MoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_main_item, parent, false);

        return new MoodViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MoodViewHolder viewHolder, int position) {
        viewHolder.MoodTracker(this.mMoods.get(position),position);
    }

    public Mood getMood(int position){
        return this.mMoods.get(position);
    }


    @Override
    public int getItemCount() {
        if (mMoods == null){
            return 0;
        }else{
            return this.mMoods.size();
        }
    }

}