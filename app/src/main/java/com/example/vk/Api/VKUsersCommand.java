package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.example.vk.Api.model.VKUser;
import com.example.vk.data.Entities.User;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.users.dto.UsersFields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VKUsersCommand extends ApiCommand<List<User>> {
    int CHUNK_LIMIT = 900;
    private final ArrayList<Integer> uids;

    public VKUsersCommand(ArrayList<Integer> usersIds) {
        uids = usersIds;
    }


    @Override
    protected ArrayList<User> onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        ArrayList<User> returnResult = new ArrayList<>();
        if (uids.isEmpty()) {
            // if no uids, send user's data
            VKMethodCall call = new VKMethodCall.Builder()
                    .method("users.get")
                    .args("fields", "photo_200")
                    .version("5.131")
                    .build();
            Class<VKUser> t = VKUser.class;
            ArrayList<VKUser> execute = (ArrayList<VKUser>) vkApiManager.execute(call, new ResponseUsersApiParser());
                VKUser VKuser = execute.get(0);
                User user = new User();
                user.setName(VKuser.firstName+ " " + VKuser.lastName);
                user.setImageLink(VKuser.photo50);
                user.setId(VKuser.id.longValue());
                returnResult.add(user);
                return returnResult;
        } else {
            ArrayList<VKUser> result = new ArrayList<>();
                List<List<Integer>> chunks = Lists.partition(uids, CHUNK_LIMIT);
            for (List<Integer> chunk :chunks)  {
                String listString = chunk.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("users.get")
                        .args("user_ids",listString)
                        .args("fields", "photo_50")
                        .version("5.131")
                        .build();
                result.addAll(vkApiManager.execute(call, new ResponseUsersApiParser()));
            }
            for (int i = 0; i < result.stream().count(); i++) {
                VKUser VKuser = result.get(i);
                User user = new User();
                user.setName(VKuser.firstName+ " " + VKuser.lastName);
                user.setImageLink(VKuser.photo50);
                user.setId(VKuser.id.longValue());
                returnResult.add(user);
            }
            return returnResult;
        }
    }

    public class ResponseUsersApiParser  implements VKApiJSONResponseParser<ArrayList<VKUser>> {


        @Override
        public ArrayList<VKUser> parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {

            try {
                JSONArray jo = jsonObject.getJSONArray("response");
                ArrayList<VKUser> r = new ArrayList<>(jo.length());
                for (int i = 0; i < jo.length(); i++) {
                    VKUser user = new Gson().fromJson(jo.getJSONObject(i).toString() , VKUser.class);
                    r.add(user);
                }
                return r;
            } catch (JSONException e) {
                throw new Exception(e);
            }
        }
    }

}
