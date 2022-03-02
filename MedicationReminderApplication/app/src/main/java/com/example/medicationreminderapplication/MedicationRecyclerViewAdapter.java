package com.example.medicationreminderapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicationRecyclerViewAdapter extends RecyclerView.Adapter<MedicationRecyclerViewAdapter.ViewHolder>{

    public ArrayList<Medication> mMedications = new ArrayList<>();
    private Context mContext;

    public MedicationRecyclerViewAdapter(Context Context, ArrayList<Medication> medications) {
        mMedications = medications;
        mContext = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_medications, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMedications.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MedicationRecyclerViewAdapter adapter = this;

        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:: Call edit popup
            }
        });

        holder.mMedicationName.setText(mMedications.get(position).name);
        holder.mMedicationDosage.setText(mMedications.get(position).Strength);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mMedicationName;
        TextView mMedicationDosage;
        ImageButton mEditButton;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMedicationName = itemView.findViewById(R.id.txtMedicationName);
            mMedicationDosage = itemView.findViewById(R.id.txtMedicationStrength);
            mEditButton = itemView.findViewById(R.id.btnEditMedication);
        }
    }
}
