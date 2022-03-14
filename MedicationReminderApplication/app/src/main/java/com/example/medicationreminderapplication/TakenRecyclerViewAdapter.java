package com.example.medicationreminderapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.ArrayList;

public class TakenRecyclerViewAdapter extends RecyclerView.Adapter<TakenRecyclerViewAdapter.ViewHolder>{

    public ArrayList<Medication> mMeds = new ArrayList<>();
    private Context mContext;

    public TakenRecyclerViewAdapter(Context Context, ArrayList<Medication> Medications) {
        mMeds = Medications;
        mContext = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_times_of_day, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mTimes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TakenRecyclerViewAdapter adapter = this;
        holder.timeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TextChanged", String.valueOf(position));
                try{
                    mTimes.set(position, LocalTime.parse(s));
                }
                catch (Exception e){

                }
            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimes.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        holder.timeText.setText(mTimes.get(position).toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText timeText;
        FloatingActionButton minusButton;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.editTextTime);
            minusButton = itemView.findViewById(R.id.btnMinus);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
