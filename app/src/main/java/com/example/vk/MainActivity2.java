package com.example.vk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.vk.Api.VKGetNewsCommands;
import com.example.vk.data.AppData;
import com.example.vk.data.DAO.NewsPostDAO;
import com.example.vk.data.DAO.UserDAO;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;
import com.example.vk.tools.AsyncTasks;
import com.google.gson.Gson;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.news_line)
    public RecyclerView news;

    public static String StartFrom;

    public NewsPostDAO newsPostDAO;

    public UserDAO userDAO;

    public List<User> users;
    public List<NewsPost> newsPosts;
    public static MainActivity2 na;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        Context context = this;
        context.deleteDatabase("mydb");
        AppData db = Room.databaseBuilder(this, AppData.class, "mydb").enableMultiInstanceInvalidation().build();
        userDAO = db.userDAO();
        newsPostDAO =  db.newsPostDAO();
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        na = this;
        news.setLayoutManager(new LinearLayoutManager(na));
        VKGetNewsCommands f = new VKGetNewsCommands(yesterday.toEpochMilli()/1000L, 0L, 10, 0L);
        VK.execute(f, new VKApiCallback<List<NewsPost>>() {
            @Override
            public void success(List<NewsPost> friendsFriendsLists) {
                new AsyncTasks.TaskGetUsers(MainActivity2.na).execute();
                new AsyncTasks.TaskInsertPosts(MainActivity2.na, friendsFriendsLists).execute();
                new AsyncTasks.TaskGetPosts(MainActivity2.na).execute();


            }

            @Override
            public void fail(@NonNull Exception e) {

            }
        });
    }
     @Override
     protected void onStart() {
         super.onStart();

     }
    public void CommentsOpen(NewsPost post){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("newsItem", new Gson().toJson(post));
        intent.putExtra("users", new Gson().toJson(users));
        startActivity(intent);
    }

}