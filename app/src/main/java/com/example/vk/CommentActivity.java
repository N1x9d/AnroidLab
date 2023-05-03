package com.example.vk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vk.Api.VKGetCommentsCommand;
import com.example.vk.Api.VKSendCommentCommand;
import com.example.vk.adapters.CommentsAdapter;
import com.example.vk.adapters.NewsFeedAdapter;
import com.example.vk.model.ContentType;
import com.example.vk.model.NewsPost;
import com.example.vk.model.WallComment;
import com.google.gson.Gson;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.utils.VKUtils;
import com.vk.sdk.api.docs.dto.DocsDocPreviewPhotoSizes;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity  extends AppCompatActivity {

    @BindView(R.id.comment_lines)
    RecyclerView commentsItem;

    @BindView(R.id.post_text)
    TextView textContent;

    @BindView(R.id.author_name)
    TextView authorName;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.editTextTextPersonName)
    EditText commentText;
    @BindView(R.id.post_comment)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        ButterKnife.bind(this);

        String jsonMyObject = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("newsItem");
        }
        NewsPost item = new Gson().fromJson(jsonMyObject, NewsPost.class);
        authorName.setText(item.sourceId.toString());
        textContent.setText(item.text);
        for (ContentType content:
                item.attachments) {
            if(content.type == WallWallpostAttachmentType.PHOTO) {
                String url = content.photo.sizes.get(content.photo.sizes.size()-1).getUrl();
                try {
                    new DownLoadImageTask(image).execute(url);
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
                    new DownLoadImageTask(image).execute(url);
                } catch (Exception e) {
                    break;
                }
                break;
            }
        }


        ArrayList<WallComment> commentsList = new ArrayList();
        CommentActivity na = this;
        Context cntx = this;

        VKGetCommentsCommand vkl = new VKGetCommentsCommand(item.id, item.sourceId );
        VK.execute(vkl, new VKApiCallback<List<WallComment>>() {
            @Override
            public void success(List<WallComment> comments) {
                commentsList.addAll(comments);
                commentsItem.setAdapter(new CommentsAdapter(commentsList, na));
                commentsItem.setLayoutManager(new LinearLayoutManager(cntx));
            }

            @Override
            public void fail(@NonNull Exception e) {

            }
        });
        btn.setOnClickListener(v -> {

            commentsList.clear();

            VKSendCommentCommand vksc = new VKSendCommentCommand(item.id, item.sourceId, commentText.getText().toString());
            VK.execute(vksc, new VKApiCallback<Integer>() {
                @Override
                public void success(Integer comments) {
                    VKGetCommentsCommand vkl = new VKGetCommentsCommand(item.id, item.sourceId );
                    VK.execute(vkl, new VKApiCallback<List<WallComment>>() {
                        @Override
                        public void success(List<WallComment> comments) {
                            commentsList.addAll(comments);
                            commentsItem.setAdapter(new CommentsAdapter(commentsList, na));
                            commentsItem.setLayoutManager(new LinearLayoutManager(cntx));
                        }

                        @Override
                        public void fail(@NonNull Exception e) {

                        }
                    });
                }

                @Override
                public void fail(@NonNull Exception e) {

                }
            });

        });

    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
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
