package com.example.vk.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vk.R;
import com.example.vk.model.ContentType;
import com.example.vk.model.NewsPost;
import com.vk.sdk.api.docs.dto.DocsDocPreviewPhotoSizes;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private List<NewsPost> data;

    public NewsFeedAdapter(List<NewsPost> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textContent;
        private final TextView authorName;

        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.post_text);
            authorName = itemView.findViewById(R.id.author_name);
            image = itemView.findViewById(R.id.image);

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
