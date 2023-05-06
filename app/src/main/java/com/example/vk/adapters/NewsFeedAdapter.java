package com.example.vk.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vk.Api.VKPostLikeCommand;
import com.example.vk.MainActivity2;
import com.example.vk.R;
import com.example.vk.Api.model.ContentType;
import com.example.vk.Api.model.VKNewsPost;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;
import com.example.vk.tools.AsyncTasks;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.sdk.api.base.dto.BaseBoolInt;
import com.vk.sdk.api.docs.dto.DocsDocPreviewPhotoSizes;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private List<NewsPost> data;
    List<User> users;
    private MainActivity2 mainActivity2;

    public NewsFeedAdapter(List<NewsPost> data, MainActivity2 mainActivity2) {
        this.data = data;
        this.users = mainActivity2.users;
        this.mainActivity2 = mainActivity2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textContent;
        private final TextView authorName;

        private final ImageView image;

        private final Button Like;
        private final Button Comment;
        private final TextView like_count;
        private final TextView comment_count;


        public ViewHolder(@NonNull View itemView) {
          super(itemView);
          textContent = itemView.findViewById(R.id.post_text);
          authorName = itemView.findViewById(R.id.author_name);
          like_count = itemView.findViewById(R.id.likes_count);
          comment_count = itemView.findViewById(R.id.comments_count);
          image = itemView.findViewById(R.id.image);
          Like = itemView.findViewById(R.id.like_bat);
          Comment = itemView.findViewById(R.id.comment_bat);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewsPost item = data.get(position);
        holder.authorName.setText(item.getUserName());
        holder.textContent.setText(item.getText());
        if(item.getUserLikes() == Boolean.TRUE) {
            holder.Like.setText("Liked");
            holder.Like.setBackgroundColor(Color.RED);
        }
        else{
            holder.Like.setText("Like");
            holder.Like.setBackgroundColor(Color.GRAY);
        }
      if(!item.getCanComment() == Boolean.TRUE){
          holder.Comment.setEnabled(false);
        }
        StringBuilder sb = new StringBuilder();
        holder.Like.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View v)
          {

            final Integer[] likesInfo = {0};
            VKPostLikeCommand vkl = new VKPostLikeCommand(item.getSourceId(), item.getId(), item.getType().toString().toLowerCase(), item.getUserLikes());
            VK.execute(vkl, new VKApiCallback<Integer>() {
              @Override
              public void success(Integer baseLikesInfo) {
                likesInfo[0] = baseLikesInfo;
                StringBuilder sb2 = new StringBuilder();
                holder.like_count.setText(sb2.append(likesInfo[0]).toString());
                  if(item.getUserLikes() == Boolean.FALSE || item.getUserLikes() == null) {
                      item.setUserLikes(Boolean.TRUE);
                      holder.Like.setText("Liked");
                      holder.Like.setBackgroundColor(Color.RED);
                  }
                  else{
                      item.setUserLikes(Boolean.FALSE);
                      holder.Like.setText("Like");
                      holder.Like.setBackgroundColor(Color.GRAY);
                  }
              }

              @Override
              public void fail(@NonNull Exception e) {

              }
            });
          }
        });

      holder.Comment.setOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View v)
        {
            mainActivity2.CommentsOpen(item);

        }
      });

        holder.like_count.setText(sb.append(item.getCount()).toString());
        sb = new StringBuilder("");
        holder.comment_count.setText(sb.append(item.getCommentCount()).toString());
        if(item.getImageLink() != null && item.getImageBitmap() == null) {

                new AsyncTasks.DownLoadImageTask(holder.image, item).execute(item.getImageLink());
        }
        else if(item.getImageBitmap() != null){
            holder.image.setImageBitmap(item.getImageBitmap());
        }
        else{
            holder.image.setImageBitmap(item.getImageBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
