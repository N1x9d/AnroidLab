package com.example.vk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vk.CommentActivity;
import com.example.vk.R;
import com.example.vk.Api.model.WallComment;
import com.example.vk.data.Entities.Comment;
import com.example.vk.data.Entities.User;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    private final List<Comment> data;
    private final List<User> users;

    public CommentsAdapter(List<Comment> data, CommentActivity na) {
        this.data = data;
        users = na.users;
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
        Comment item = data.get(position);
        User curUser = users.stream().filter(userItem -> userItem.getId() == item.getFrom().longValue()).findFirst().orElse(null);
        holder.authorName.setText(curUser.getName());
        holder.textContent.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
