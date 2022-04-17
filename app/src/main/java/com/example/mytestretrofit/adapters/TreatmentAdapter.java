package com.example.mytestretrofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.models.MedicalTreatments;

import java.util.List;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.TreatmentHolder> {
    List<MedicalTreatments> medicalTreatmentsList;


    public TreatmentAdapter(List<MedicalTreatments> List) {
        this.medicalTreatmentsList = List;
    }


    public class TreatmentHolder extends RecyclerView.ViewHolder {
        TextView tvTreatmentId, tvTreatmentDiagnos, tvTreatmentMkb, tvTreatmentCreatedDate;

        public TreatmentHolder(@NonNull View itemView) {
            super(itemView);
            tvTreatmentId = itemView.findViewById(R.id.tv_Tretment_Id);
            tvTreatmentDiagnos = itemView.findViewById(R.id.tv_Tretment_Diagnos);
            tvTreatmentMkb = itemView.findViewById(R.id.tv_Tretment_Mkb);
            tvTreatmentCreatedDate = itemView.findViewById(R.id.tv_Tretments_createdAt);

        }
    }

    @NonNull
    @Override
    public TreatmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tretment, parent, false);
        return new TreatmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreatmentHolder holder, int position) {
        holder.tvTreatmentId.setText(String.valueOf(medicalTreatmentsList.get(position).getId()));
        holder.tvTreatmentDiagnos.setText(String.valueOf(medicalTreatmentsList.get(position).getDiagnosis()));
        holder.tvTreatmentMkb.setText(String.valueOf(medicalTreatmentsList.get(position).getMkb()));
        holder.tvTreatmentCreatedDate.setText(String.valueOf(medicalTreatmentsList.get(position).getCreatedAt()).split("T")[0]);
    }

    @Override
    public int getItemCount() {
        return medicalTreatmentsList.size();
    }


}
