package com.example.mytestretrofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytestretrofit.models.Patients;
import com.example.mytestretrofit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> implements Filterable {

    List<Patients> postList;
    //для реализации поиска создает еще один лист
    List<Patients> postListFull;

    public interface OnPostClickListener {
        void onPostClick(Patients post, int position);
    }

    private final OnPostClickListener onPostClickListener;

    public PostAdapter(List<Patients> postList, OnPostClickListener onPostClickListener) {
        this.postList = postList;
        this.onPostClickListener = onPostClickListener;
        //для поиска копируем основной лист, в новый лист
        postListFull = new ArrayList<>(postList);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPatronymic, tvSurname, tvAge, tvTreatments;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_Id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPatronymic = itemView.findViewById(R.id.tv_Patronymic);
            tvSurname = itemView.findViewById(R.id.tv_Surname);
            tvAge = itemView.findViewById(R.id.tv_Age);
            tvTreatments = itemView.findViewById(R.id.tv_Treatments);

        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Patients post = postList.get(position);
        holder.tvId.setText(String.valueOf(postList.get(position).getId()));
        holder.tvName.setText(String.valueOf(postList.get(position).getName()));
        holder.tvPatronymic.setText(String.valueOf(postList.get(position).getPatronymic()));
        holder.tvSurname.setText(String.valueOf(postList.get(position).getSurname()));
        holder.tvAge.setText(String.valueOf(postList.get(position).getAge()));
        holder.tvTreatments.setText(String.valueOf(postList.get(position).getTreatments()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostClickListener.onPostClick(post, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    @Override
    public Filter getFilter() {
        return patientsFilter;
    }

    private Filter patientsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Patients> filterList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filterList.addAll(postListFull);

            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Patients patients : postListFull) {
                    if (patients.getName().toLowerCase().contains(filterPattern) || patients.getSurname().toLowerCase().contains(filterPattern)){
                        filterList.add(patients);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            postList.clear();
            postList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}





