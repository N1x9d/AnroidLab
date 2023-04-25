//package com.example.vkm.Oauth2;
//
//import android.content.Intent;
//
//import androidx.annotation.Nullable;
//
//import com.example.vkm.MainActivity;
//import com.vk.api.sdk.auth.VKAccessToken;
//import com.vk.api.sdk.auth.VKAccessTokenProvider;
//
//public class Application extends android.app.Application {
//
//    VKAccessToken vkAccessTokenTracker = new VKAccessToken() {
//        @Override
//        public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
//            if (newToken == null){
//                Intent intent = new Intent(Application.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        }
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        vkAccessTokenTracker.startTracking();
//        VKSdk.initialize(this);
//    }
//}