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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TimesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ArrayList<RecyclerEntity> list = new ArrayList<>();
    public ArrayList<ArrayList<LocalTime>> mTimes = new ArrayList<>();
    public ArrayList<String> mDays = new ArrayList<>();
    private Context mContext;
    private final int SHOW_TIME = 1;
    private final int HIDE_TIME = 2;


    public TimesRecyclerViewAdapter(Context Context, ArrayList<ArrayList<LocalTime>> Times, ArrayList<String> days) {
        Log.e("Num of Days", days.toString());
        int counter = 0;
        if (Times.size() != 7){
            for(int i = 0; i<7; i++){
                Times.add(new ArrayList<>());
            }
        }
        for (String day: days
             ) {
            list.add(new RecyclerEntity(day, Times.get(counter),false));
            counter++;
        }
        mDays = days;
        mTimes = Times;
        mContext = Context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType==SHOW_TIME){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_times_specific_days, parent,false);
            return new TimesViewHolder(v);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_days_times, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).DayButton.setText(list.get(position).getDay());
            ((ViewHolder) holder).DayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu(position);
                }
            });
        }
        if (holder instanceof TimesViewHolder){
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(mContext, list.get(position).times);

            ((TimesViewHolder) holder).DayButton.setText(list.get(position).getDay());
            ((TimesViewHolder) holder).DayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(position).times = adapter.mTimes;
                    mTimes.set(position, adapter.mTimes);
                    closeMenu();
                }
            });

            ((TimesViewHolder) holder).timesRecycler.setAdapter(adapter);
            ((TimesViewHolder) holder).timesRecycler.setLayoutManager(new LinearLayoutManager(mContext));

            ((TimesViewHolder) holder).addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.mTimes.add(LocalTime.MIN);
                    adapter.notifyDataSetChanged();
                }
            });

        }

    }

    @Override
    public int getItemViewType(int position){
        if (list.get(position).isShowMenu()){
            return SHOW_TIME;
        }
        else{
            return HIDE_TIME;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showMenu(int position){
        for (RecyclerEntity entity : list
             ) {
            entity.setShowMenu(false);
        }
        list.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }

    public boolean isMenuShown(){
        return true;
    }

    public void closeMenu(){
        for (int i = 0; i<list.size();i++){
            list.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button DayButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DayButton = itemView.findViewById(R.id.btnDay);
        }
    }

    public class TimesViewHolder extends RecyclerView.ViewHolder{
        RecyclerView timesRecycler;
        Button DayButton;
        FloatingActionButton addButton;
        public TimesViewHolder(View view){
            super(view);
            timesRecycler = view.findViewById(R.id.recyclerByWeekTimes);
            DayButton = view.findViewById(R.id.btnDay2);
            addButton = view.findViewById(R.id.btnAddTime4);
        }
    }

    private class RecyclerEntity {
        private String day;
        private boolean showMenu = false;
        private ArrayList<LocalTime> times;

        public RecyclerEntity(){}
        public RecyclerEntity(String day, ArrayList<LocalTime> times,boolean showMenu){
            this.day = day;
            this.times = times;
            this.showMenu=showMenu;
        }

        public String getDay() {
            return day;
        }
        public void setDay(String day){
            this.day = day;
        }

        public void setShowMenu(boolean showMenu) {
            this.showMenu = showMenu;
        }
        public boolean isShowMenu(){
            return showMenu;
        }

        public void setTimes(ArrayList<LocalTime> times) {
            this.times = times;
        }
        public ArrayList<LocalTime> getTimes(){
            return times;
        }
        public void addTime(LocalTime time){
            times.add(time);
        }
    }
}
