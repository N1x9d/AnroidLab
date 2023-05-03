package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.example.vk.model.NewsPost;
import com.google.gson.Gson;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.base.dto.BaseLikesInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VKPostLikeCommand extends ApiCommand<Integer> {
   private int owner_id;
  private int item_id;
  private String type;

  public VKPostLikeCommand(int owner_id, int item_id, String type) {
    this.owner_id = owner_id;
    this.item_id = item_id;
    this.type = type;
  }

  @Override
  protected Integer onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {

      VKMethodCall call = new VKMethodCall.Builder()
        .method("likes.add")
        .args("type", type)
        .args("owner_id", owner_id)
        .args("item_id", item_id)
        .version("5.131")
        .build();
      int execute = (int) vkApiManager.execute(call, new ResponseApiParser());
      return execute;

  }

  public class ResponseApiParser implements VKApiJSONResponseParser {



    @Override
    public Integer parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {

      try {
        JSONObject jo = jsonObject.getJSONObject("response");
        Integer user = jo.getInt("likes");

        return user;
      } catch (JSONException e) {
        throw new Exception(e);
      }
    }
  }
}
