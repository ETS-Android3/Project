package com.example.medicationreminderapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TimesRecyclerViewAdapter extends RecyclerView.Adapter<TimesRecyclerViewAdapter.ViewHolder>{

    public ArrayList<ArrayList<LocalTime>> mTimes = new ArrayList<>();
    public ArrayList<String> mDays = new ArrayList<>();
    private Context mContext;

    public TimesRecyclerViewAdapter(Context Context, ArrayList<ArrayList<LocalTime>> Times, ArrayList<String> days) {
        mDays = days;
        mTimes = Times;
        mContext = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_days_times, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TimesRecyclerViewAdapter adapter = this;
        holder.timesRecycler.setAdapter(new RecyclerViewAdapter(mContext, mTimes.get(position)));
        holder.DayButton.setText(mDays.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button DayButton;
        RecyclerView timesRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DayButton = itemView.findViewById(R.id.btnAddTime3);
            timesRecycler = itemView.findViewById(R.id.recyclerByWeekTimes);
        }
    }
}
