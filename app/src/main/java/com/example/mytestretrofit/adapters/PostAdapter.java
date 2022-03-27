package com.example.mytestretrofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytestretrofit.models.Post;
import com.example.mytestretrofit.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    List<Post> postList;

    public interface OnPostClickListener {
        void onPostClick(Post post, int position);
    }

    private final OnPostClickListener onPostClickListener;

    public PostAdapter(List<Post> postList, OnPostClickListener onPostClickListener) {
        this.postList = postList;
        this.onPostClickListener = onPostClickListener;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvUserId, tvTitle, tvBody;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_Id);
            tvUserId = itemView.findViewById(R.id.tv_UserId);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvBody = itemView.findViewById(R.id.tv_Body);
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
        Post post = postList.get(position);
        holder.tvId.setText(String.valueOf(postList.get(position).getId()));
        holder.tvUserId.setText(String.valueOf(postList.get(position).getUserId()));
        holder.tvTitle.setText(postList.get(position).getTitle());
        holder.tvBody.setText(postList.get(position).getBody());


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

}





