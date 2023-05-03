package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.example.vk.model.NewsPost;
import com.example.vk.model.WallComment;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.comment.dto.CommentThread;
import com.vk.sdk.api.wall.dto.WallWallComment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VKGetCommentsCommand extends ApiCommand<List<WallComment>> {
    private Integer postId;
    private Integer ownerId;

    public VKGetCommentsCommand(Integer postId, Integer ownerId) {
        this.postId = postId;
        this.ownerId = ownerId;
    }

    @Override
    protected ArrayList<WallComment> onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        VKMethodCall call = new VKMethodCall.Builder()
                .method("wall.getComments")
                .args("post_id", postId)
                .args("owner_id", ownerId)
                .version("5.131")
                .build();
        Class<WallComment> t = WallComment.class;
        ArrayList<WallComment> execute = (ArrayList<WallComment>) vkApiManager.execute(call, new ResponseApiParser(t));
        return execute;
    }
}
