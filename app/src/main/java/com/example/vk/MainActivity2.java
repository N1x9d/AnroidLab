package com.example.vk;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vk.Api.VKFriendsCommand;
import com.example.vk.Api.VKGetNewsCommands;
import com.example.vk.adapters.NewsFeedAdapter;
import com.example.vk.model.NewsPost;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.sdk.api.friends.dto.FriendsFriendsList;
import com.vk.sdk.api.newsfeed.dto.NewsfeedNewsfeedItem;
import com.vk.sdk.api.users.dto.UsersFields;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.news_line)
    RecyclerView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        ArrayList<NewsPost> friends = new ArrayList<>();
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        VKAccessToken token = MainActivity.g;
        Context cntx = this;
        VKGetNewsCommands f = new VKGetNewsCommands(yesterday.toEpochMilli()/1000L, 0L, 1000, 0L);
        VK.execute(f, new VKApiCallback<List<NewsPost>>() {
            @Override
            public void success(List<NewsPost> friendsFriendsLists) {
                friends.addAll(friendsFriendsLists);
                news.setAdapter(new NewsFeedAdapter(friends));
                news.setLayoutManager(new LinearLayoutManager(cntx));
            }

            @Override
            public void fail(@NonNull Exception e) {

            }
        });





    }
}