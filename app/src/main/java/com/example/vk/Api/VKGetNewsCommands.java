package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.example.vk.model.NewsPost;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.newsfeed.dto.NewsfeedNewsfeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VKGetNewsCommands extends ApiCommand<List<NewsPost>> {
        private Long start_time;
        private Long end_time;
        private Long start_from;
        private int maxCount;

        public VKGetNewsCommands(Long start_time, Long end_time, int maxCount, Long start_from) {
            this.start_time = start_time;
            this.end_time = end_time;
            this.start_from = start_from;
            this.maxCount = maxCount;
        }

    @Override
        protected ArrayList<NewsPost> onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
            if (start_from != 0 && end_time != 0) {
                // if no uids, send user's data
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post")
                        .args("start_from", start_from)
                        .args("start_time", start_time)
                        .args("end_time", end_time)
                        .version("5.131")
                        .build();
                Class<NewsPost> t = NewsPost.class;
                ArrayList<NewsPost> execute = (ArrayList<NewsPost>) vkApiManager.execute(call, new ResponseApiParser(t));
                return execute;
            } else if(end_time == 0){
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post")
                        .args("start_time", start_time)
                        .args("count", maxCount)
                        .version("5.131")
                        .build();
                Class<NewsPost> t = NewsPost.class;
                ArrayList<NewsPost> execute = (ArrayList<NewsPost>) vkApiManager.execute(call, new ResponseApiParser(t));
                return execute;
            }
            else {
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post")
                        .args("start_time", start_time)
                        .args("end_time", end_time)
                        .version("5.131")
                        .build();
                Class<NewsPost> t = NewsPost.class;
                ArrayList<NewsPost> execute = (ArrayList<NewsPost>) vkApiManager.execute(call, new ResponseApiParser(t));
                return execute;
            }

        }


}
