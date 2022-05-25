package com.example.medicationreminderapplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MedicationOnDayRecyclerViewAdapter extends RecyclerView.Adapter<MedicationOnDayRecyclerViewAdapter.ViewHolder>{

    public ArrayList<Medication> mMedications = new ArrayList<>();
    private Context mContext;

    public MedicationOnDayRecyclerViewAdapter(Context Context, ArrayList<Medication> medications) {
        mMedications = medications;
        mContext = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_medications_cal, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMedications.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MedicationOnDayRecyclerViewAdapter adapter = this;
        holder.mMedicationName.setText(mMedications.get(position).name);
        holder.mMedicationDosage.setText(mMedications.get(position).Strength);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mMedicationName;
        TextView mMedicationDosage;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMedicationName = itemView.findViewById(R.id.txtMedNameCal);
            mMedicationDosage = itemView.findViewById(R.id.txtMedStrCal);
        }
    }
}
