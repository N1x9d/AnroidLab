package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.internal.ApiCommand;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VKSendCommentCommand extends ApiCommand{
    private Integer postId;
    private Integer ownerId;
    private String text;

    public VKSendCommentCommand(Integer postId, Integer ownerId, String text) {
        this.postId = postId;
        this.ownerId = ownerId;
        this.text = text;
    }

    @Override
    protected Integer onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        VKMethodCall call = new VKMethodCall.Builder()
                .method("wall.createComment")
                .args("post_id", postId)
                .args("owner_id", ownerId)
                .args("message", text)
                .version("5.131")
                .build();
        Integer execute = (Integer) vkApiManager.execute(call, new ResponseApiParser());
        return execute;
    }
    public class ResponseApiParser implements VKApiJSONResponseParser {

        @Override
        public Integer parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {

            try {
                JSONObject jo = jsonObject.getJSONObject("response");
                Integer user = jo.getInt("comment_id");

                return user;
            } catch (JSONException e) {
                throw new Exception(e);
            }
        }
    }
}
