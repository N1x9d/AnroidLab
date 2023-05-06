package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.example.vk.Api.model.ContentType;
import com.example.vk.Api.model.Group;
import com.example.vk.Api.model.VKNewsPost;
import com.example.vk.Api.model.VKUser;
import com.example.vk.MainActivity2;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;
import com.example.vk.tools.AsyncTasks;
import com.google.gson.Gson;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.base.dto.BaseBoolInt;
import com.vk.sdk.api.docs.dto.DocsDocPreviewPhotoSizes;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                        .method("newsfeed.getComments")
                        .args("filters", "post")
                        .args("start_from", start_from)
                        .args("start_time", start_time)
                        .args("end_time", end_time)
                        .version("5.131")
                        .build();
                ArrayList<NewsPost> execute = (ArrayList<NewsPost>) vkApiManager.execute(call, new NewsApiParser());
                return execute;
            } else if(end_time == 0){
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("newsfeed.get")
                        .args("filters", "post")
                        .args("start_time", start_time)
                        .args("count", maxCount)
                        .version("5.131")
                        .build();
                ArrayList<NewsPost> execute = (ArrayList<NewsPost>) vkApiManager.execute(call, new NewsApiParser());
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
                ArrayList<NewsPost> execute = (ArrayList<NewsPost>) vkApiManager.execute(call, new NewsApiParser());
                return execute;
            }

        }

    class NewsApiParser implements VKApiJSONResponseParser {

        @Override
        public ArrayList<NewsPost> parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {

            try {
                JSONObject jo = jsonObject.getJSONObject("response");
                JSONArray ja = jo.getJSONArray("items");
                JSONArray jprofiles = jo.getJSONArray("profiles");
                JSONArray jgroups = jo.getJSONArray("groups");
                String nextFrom = jo.getString("next_from");
                ArrayList<NewsPost> r = new ArrayList<>(ja.length());
                ArrayList<User> users = new ArrayList<>(jprofiles.length()+jgroups.length());
                for (int i = 0; i < jprofiles.length(); i++) {
                    VKUser VKUSer = new Gson().fromJson(jprofiles.getJSONObject(i).toString() , VKUser.class);
                    User user = new User();
                    user.setName(VKUSer.firstName+ " " + VKUSer.lastName);
                    user.setImageLink(VKUSer.photo50);
                    user.setId(VKUSer.id.longValue());
                    users.add(user);
                }
                for (int i = 0; i < jgroups.length(); i++) {
                    Group VKUSer = new Gson().fromJson(jgroups.getJSONObject(i).toString() , Group.class);
                    User user = new User();
                    user.setName(VKUSer.name);
                    user.setImageLink(VKUSer.photo50);
                    user.setId(VKUSer.id.longValue() * -1);
                    users.add(user);
                }
                MainActivity2.StartFrom = nextFrom;
                new AsyncTasks.TaskInsertUsers(MainActivity2.na, users).execute();
                for (int i = 0; i < ja.length(); i++) {
                    VKNewsPost user = new Gson().fromJson(ja.getJSONObject(i).toString() , VKNewsPost.class);

                    NewsPost newsPost = new NewsPost();
                    newsPost.setId(user.id);
                    newsPost.setText(user.text);
                    newsPost.setSourceId(user.sourceId);
                    newsPost.setDate(user.date);
                    newsPost.setType(user.type);
                    newsPost.setCommentCount(user.comments.getCount());
                    User curUser = users.stream().filter(userItem -> userItem.getId() == newsPost.getSourceId().longValue()).findFirst().orElse(null);
                    newsPost.setUserName(curUser.getName());
                    newsPost.setUserAvatarLink(curUser.getImageLink());
                    for (ContentType content:
                            user.attachments) {
                        if(content.type == WallWallpostAttachmentType.PHOTO) {
                            newsPost.setImageLink(content.photo.sizes.get(content.photo.sizes.size()-1).getUrl());
                            break;
                        }
                        else if(content.type == WallWallpostAttachmentType.DOC && content.doc.type == 4) {
                            List<DocsDocPreviewPhotoSizes> s = content.doc.preview.getPhoto().getSizes();
                            DocsDocPreviewPhotoSizes ls = s.get(s.size() - 1);
                            newsPost.setImageLink( ls.getSrc());
                            break;
                        }
                    }

                    newsPost.setCount(user.likes.getCount());
                    newsPost.setCanLike(Boolean.TRUE);
                    if(user.likes.getCanLike() == BaseBoolInt.NO && user.likes.getUserLikes() == 1) {
                        newsPost.setCanLike(Boolean.TRUE);
                        newsPost.setUserLikes(Boolean.TRUE);
                    } else if (user.likes.getCanLike() == BaseBoolInt.NO) {
                        newsPost.setCanLike(Boolean.FALSE);
                    }
                    if(user.comments.getCanPost() == BaseBoolInt.NO){
                        newsPost.setCanComment(Boolean.FALSE);
                    }
                    else {
                        newsPost.setCanComment(Boolean.TRUE);
                    }
                    r.add(newsPost);
                }



                return r;
            } catch (JSONException e) {
                throw new Exception(e);
            }
        }
    }


}
