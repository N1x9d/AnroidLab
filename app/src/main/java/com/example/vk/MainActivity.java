package com.example.vk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKTokenExpiredHandler;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKAuthException;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private VKScope[] scope = new VKScope[]{VKScope.OFFLINE,VKScope.FRIENDS,VKScope.WALL, VKScope.GROUPS};
    static VKAccessToken g;
    @BindView(R.id.btnLogin)
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn.setOnClickListener(v -> {
            VK.login(this, Arrays.asList(scope));


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VK.onActivityResult(requestCode, resultCode, data, new VKAuthCallback() {
            @Override
            public void onLoginFailed(@NonNull VKAuthException e) {
            }

            @Override
            public void onLogin(@NonNull VKAccessToken vkAccessToken) {
                g = vkAccessToken;
            }
        })){

            super.onActivityResult(requestCode, resultCode, data);
        }
        if(g != null){
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }

    private VKTokenExpiredHandler tokenTracker = new VKTokenExpiredHandler() {
        @Override
        public void onTokenExpired() {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}