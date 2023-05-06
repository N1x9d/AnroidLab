package com.example.vk;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.vk.Api.VKGetCommentsCommand;
import com.example.vk.Api.VKSendCommentCommand;
import com.example.vk.Api.VKUsersCommand;
import com.example.vk.adapters.CommentsAdapter;
import com.example.vk.data.AppData;
import com.example.vk.data.DAO.UserDAO;
import com.example.vk.data.Entities.Comment;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;
import com.example.vk.tools.AsyncTasks;
import com.google.gson.Gson;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity  extends AppCompatActivity {
    @BindView(R.id.comment_lines)
    public RecyclerView commentsItem;
    public List<User> users;

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
    public static CommentActivity na;
    public UserDAO userDAO;
    public ArrayList<Integer> usersIds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);
        ButterKnife.bind(this);
        AppData db = Room.databaseBuilder(this, AppData.class, "mydb").enableMultiInstanceInvalidation().build();
        userDAO = db.userDAO();
        String jsonMyObject = null;
        String jsonMyObject2 = null;
        na = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("newsItem");
        }
        NewsPost item = new Gson().fromJson(jsonMyObject, NewsPost.class);

        authorName.setText(item.getUserName());
        textContent.setText(item.getText());

        if(item.getImageLink() != null && item.getImageBitmap() == null) {

            new AsyncTasks.DownLoadImageTask(image, item).execute(item.getImageLink());
        }
        else if(item.getImageBitmap() != null){
            image.setImageBitmap(item.getImageBitmap());
        }
        else{
            image.setImageBitmap(item.getImageBitmap());
        }


        ArrayList<Comment> commentsList = new ArrayList();
        CommentActivity na = this;
        Context cntx = this;

        VKGetCommentsCommand vkl = new VKGetCommentsCommand(item.getId(), item.getSourceId() , this);
        VK.execute(vkl, new VKApiCallback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments) {
                VKUsersCommand f = new VKUsersCommand(usersIds);
                VK.execute(f, new VKApiCallback<List<User>>() {
                    @Override
                    public void success(List<User> friendsFriendsLists) {
                        users = friendsFriendsLists;
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
        btn.setOnClickListener(v -> {

            commentsList.clear();

            VKSendCommentCommand vksc = new VKSendCommentCommand(item.getId(), item.getSourceId() , commentText.getText().toString());
            VK.execute(vksc, new VKApiCallback<Integer>() {
                @Override
                public void success(Integer comments) {
                    VKGetCommentsCommand vkl = new VKGetCommentsCommand(item.getId(), item.getSourceId(), na  );
                    VK.execute(vkl, new VKApiCallback<List<Comment>>() {
                        @Override
                        public void success(List<Comment> comments) {
                            VKUsersCommand f = new VKUsersCommand(usersIds);
                            VK.execute(f, new VKApiCallback<List<User>>() {
                                @Override
                                public void success(List<User> friendsFriendsLists) {
                                    users = friendsFriendsLists;
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
                }

                @Override
                public void fail(@NonNull Exception e) {

                }
            });

        });

    }
}
