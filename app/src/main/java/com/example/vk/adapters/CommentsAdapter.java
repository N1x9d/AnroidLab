package com.example.vk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vk.CommentActivity;
import com.example.vk.R;
import com.example.vk.model.WallComment;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    private final List<WallComment> data;

    public CommentsAdapter(List<WallComment> data, CommentActivity na) {
        this.data = data;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textContent;
        private final TextView authorName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.post_text);
            authorName = itemView.findViewById(R.id.author_name);
        }
    }
    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WallComment item = data.get(position);
        holder.authorName.setText(item.fromId.toString());
        holder.textContent.setText(item.text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
