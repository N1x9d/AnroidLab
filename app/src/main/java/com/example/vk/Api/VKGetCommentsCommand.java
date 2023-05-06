package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.example.vk.Api.model.WallComment;
import com.example.vk.CommentActivity;
import com.example.vk.MainActivity2;
import com.example.vk.data.Entities.Comment;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;
import com.example.vk.tools.AsyncTasks;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKApiCallback;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.users.dto.UsersFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VKGetCommentsCommand extends ApiCommand<List<Comment>> {
    private Integer postId;
    private Integer ownerId;
    private CommentActivity activity;

    public VKGetCommentsCommand(Integer postId, Integer ownerId, CommentActivity activity) {
        this.postId = postId;
        this.ownerId = ownerId;
        this.activity = activity;
    }

    @Override
    protected ArrayList<Comment> onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        VKMethodCall call = new VKMethodCall.Builder()
                .method("wall.getComments")
                .args("post_id", postId)
                .args("owner_id", ownerId)
                .version("5.131")
                .build();
        Class<WallComment> t = WallComment.class;
        ArrayList<Comment> comments = new ArrayList<Comment>();
        ArrayList<WallComment> execute = (ArrayList<WallComment>) vkApiManager.execute(call, new ResponseApiParser(t));
        ArrayList<Integer> usersIds = new ArrayList<>();
        for (WallComment comment:
             execute) {
            Comment com = new Comment();
            com.setText(comment.text);
            com.setDate(comment.date);
            com.setPostId(ownerId);
            com.setId(comment.id);
            com.setSourceId(postId);
            com.setFrom(comment.fromId);
            if(!usersIds.contains(comment.fromId)){
                usersIds.add(comment.fromId);
            }
            comments.add(com);
        }
        activity.usersIds = usersIds;
        return comments;
    }
}
