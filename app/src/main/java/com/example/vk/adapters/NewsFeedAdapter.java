package com.example.vk.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.example.vk.model.ContentType;
import com.example.vk.model.NewsPost;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.sdk.api.base.dto.BaseBoolInt;
import com.vk.sdk.api.docs.dto.DocsDocPreviewPhotoSizes;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private List<NewsPost> data;
    private MainActivity2 mainActivity2;

    public NewsFeedAdapter(List<NewsPost> data, MainActivity2 mainActivity2) {
        this.data = data;
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
        holder.authorName.setText(item.sourceId.toString());
        holder.textContent.setText(item.text);
        if(item.likes.getCanLike() == BaseBoolInt.NO && item.likes.getUserLikes() == 1) {
          holder.Like.setText("Liked");
        } else if (item.likes.getCanLike() == BaseBoolInt.NO) {
          holder.Like.setEnabled(false);
        }
      if(item.comments.getCanPost() == BaseBoolInt.NO){
          holder.Comment.setEnabled(false);
        }
        StringBuilder sb = new StringBuilder();
        holder.Like.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View v)
          {

            final Integer[] likesInfo = {0};
            VKPostLikeCommand vkl = new VKPostLikeCommand(item.sourceId, item.id, item.type.toString().toLowerCase() );
            VK.execute(vkl, new VKApiCallback<Integer>() {
              @Override
              public void success(Integer baseLikesInfo) {
                likesInfo[0] = baseLikesInfo;
                holder.Like.setText("Liked");
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

        holder.like_count.setText(sb.append(item.likes.getCount()).toString());
        sb = new StringBuilder("");
        holder.comment_count.setText(sb.append(item.comments.getCount()).toString());
        for (ContentType content:
        item.attachments) {
            if(content.type == WallWallpostAttachmentType.PHOTO) {
                String url = content.photo.sizes.get(content.photo.sizes.size()-1).getUrl();
                try {
                    new DownLoadImageTask(holder.image).execute(url);
                } catch (Exception e) {
                        break;
                }
                break;
            }
            else if(content.type == WallWallpostAttachmentType.DOC && content.doc.type == 4) {
                List<DocsDocPreviewPhotoSizes> s = content.doc.preview.getPhoto().getSizes();
                DocsDocPreviewPhotoSizes ls = s.get(s.size() - 1);
                String url = ls.getSrc();
                try {
                    new DownLoadImageTask(holder.image).execute(url);
                } catch (Exception e) {
                    break;
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage)
                        .openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
