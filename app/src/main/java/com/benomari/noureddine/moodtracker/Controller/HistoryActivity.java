package com.benomari.noureddine.moodtracker.Controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.benomari.noureddine.moodtracker.Model.Mood;
import com.benomari.noureddine.moodtracker.R;
import com.benomari.noureddine.moodtracker.Utils.ItemClickSupport;
import com.benomari.noureddine.moodtracker.View.MoodAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.list) RecyclerView mListMood;


    private ArrayList<Mood> moods;
    private MoodAdapter adapter;

    private static final String PREFS = "PREFS";
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        configureRecyclerView();
        configureOnClickRecyclerView();
        mPreferences = this.getSharedPreferences(PREFS, MODE_PRIVATE);
        showMoods();


    }
    private void configureRecyclerView() {
        moods = new ArrayList<>();
        this.adapter = new MoodAdapter(moods);
        this.mListMood.setAdapter(this.adapter);
        this.mListMood.setLayoutManager(new LinearLayoutManager(this));
    }



    // Display the moods history
    private void showMoods() {
        Gson gson = new Gson();
        String jsonMood = mPreferences.getString("MoodList", "");
        Type type = new TypeToken<ArrayList<Mood>>() {
        }.getType();
        ArrayList<Mood> moods1 = gson.fromJson(jsonMood, type);
        moods.addAll(moods1);
        Log.d("MOODSHISTORY",String.valueOf(moods));
        adapter.notifyDataSetChanged();

    }
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mListMood, R.layout.history_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (!(adapter.getMood(position).getMessageMood().equals(""))) {
                            String message = adapter.getMood(position).getMessageMood();
                            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
