package com.example.vk.Api;

import com.google.common.collect.Lists;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.newsfeed.dto.NewsfeedNewsfeedItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VKGetNewsCommands extends ApiCommand<List<NewsfeedNewsfeedItem>> {
        private Long start_time;
        private Long end_time;
        private Long start_from;
        private int maxCount;

        public VKGetNewsCommands(Long start_time, Long end_time, Long start_from, int maxCount) {
            this.start_time = start_time;
            this.end_time = end_time;
            this.start_from = start_from;
            this.maxCount = maxCount;
        }

    @Override
        protected ArrayList<NewsfeedNewsfeedItem> onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
            if (start_from != 0) {
                // if no uids, send user's data
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post, wall_photo, video")
                        .args("start_from", start_from)
                        .args("start_time", start_time)
                        .args("end_time", end_time)
                        .version("5.131")
                        .build();
                Class<NewsfeedNewsfeedItem> t = NewsfeedNewsfeedItem.class;
                ArrayList<NewsfeedNewsfeedItem> execute = (ArrayList<NewsfeedNewsfeedItem>) vkApiManager.execute(call, new ResponseApiParser(t));
                return execute;
            } else if(end_time == 0){
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post, wall_photo, video")
                        .args("start_time", start_time)
                        .args("count", maxCount)
                        .version("5.131")
                        .build();
                Class<NewsfeedNewsfeedItem> t = NewsfeedNewsfeedItem.class;
                ArrayList<NewsfeedNewsfeedItem> execute = (ArrayList<NewsfeedNewsfeedItem>) vkApiManager.execute(call, new ResponseApiParser(t));
                return execute;
            }
            else {
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post, wall_photo, video")
                        .args("start_time", start_time)
                        .args("end_time", end_time)
                        .version("5.131")
                        .build();
                Class<NewsfeedNewsfeedItem> t = NewsfeedNewsfeedItem.class;
                ArrayList<NewsfeedNewsfeedItem> execute = (ArrayList<NewsfeedNewsfeedItem>) vkApiManager.execute(call, new ResponseApiParser(t));
                return execute;
            }

        }

}
