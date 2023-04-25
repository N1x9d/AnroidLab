package com.example.vk;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vk.Api.VKFriendsCommand;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.sdk.api.friends.dto.FriendsFriendsList;
import com.vk.sdk.api.users.dto.UsersFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.loginbutton)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<FriendsFriendsList> friends = new ArrayList<>();
        VKFriendsCommand f = new VKFriendsCommand();
        VK.execute(f, new VKApiCallback<List<FriendsFriendsList>>() {
            @Override
            public void success(List<FriendsFriendsList> friendsFriendsLists) {
                friends.addAll(friendsFriendsLists);
            }

            @Override
            public void fail(@NonNull Exception e) {

            }
        });




    }
}