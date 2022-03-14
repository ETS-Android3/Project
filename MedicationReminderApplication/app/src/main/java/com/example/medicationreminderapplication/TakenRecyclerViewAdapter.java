package com.example.medicationreminderapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TakenRecyclerViewAdapter extends RecyclerView.Adapter<TakenRecyclerViewAdapter.ViewHolder>{

    public ArrayList<Medication> mMeds = new ArrayList<>();
    public DataController dc;
    private Context mContext;

    public TakenRecyclerViewAdapter(Context Context) {
        dc = DataController.getInstance();
        mMeds = dc.getNextMedList();
        mContext = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_taken_medications, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TakenRecyclerViewAdapter adapter = this;
        holder.nameText.setText(mMeds.get(position).name);
        holder.strengthText.setText(mMeds.get(position).Strength);
        holder.taken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dc.medTaken(mMeds.get(position), LocalDateTime.now());
                buttonView.setEnabled(Boolean.FALSE);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView strengthText;
        CheckBox taken;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.txtMedicationName);
            strengthText = itemView.findViewById(R.id.txtMedicationStrength);
            taken = itemView.findViewById(R.id.chkTaken);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
