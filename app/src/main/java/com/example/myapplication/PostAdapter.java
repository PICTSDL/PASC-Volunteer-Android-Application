package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<ModelPosts> PostList;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1=itemView.findViewById(R.id.textView4);
            textView2=itemView.findViewById(R.id.textView5);
            textView3=itemView.findViewById(R.id.textView6);
        }
    }

    public PostAdapter(ArrayList<ModelPosts> postList) {
        PostList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_template,parent,false);
        return new PostAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPosts currentPost = PostList.get(position);

        holder.textView1.setText(currentPost.getTitle());
        holder.textView2.setText(currentPost.getDescription());
        holder.textView3.setText(currentPost.getDate());
    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }
}
