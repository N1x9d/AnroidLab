package com.example.vk.Api;

import com.google.common.collect.Lists;
import com.vk.api.sdk.VKApiManager;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.internal.ApiCommand;
import com.vk.sdk.api.friends.dto.FriendsFriendsList;
import com.vk.sdk.api.users.dto.UsersFields;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VKFriendsCommand extends ApiCommand<List<FriendsFriendsList>> {
    int CHUNK_LIMIT = 900;
    ArrayList<Integer> uids = new ArrayList<Integer>();

    @Override
    protected ArrayList<FriendsFriendsList> onExecute(VKApiManager vkApiManager) throws InterruptedException, IOException, VKApiException {
        if (uids.isEmpty()) {
            // if no uids, send user's data
            VKMethodCall call = new VKMethodCall.Builder()
                    .method("friends.get")
                    .args("fields", "nickname")
                    .args("order", "hints")
                    .version("5.131")
                    .build();
            Class<FriendsFriendsList> t = FriendsFriendsList.class;
            ArrayList<FriendsFriendsList> execute = vkApiManager.execute(call, new ResponseApiParser<FriendsFriendsList>(t));
            return execute;
        } else {
            ArrayList<FriendsFriendsList> result = new ArrayList<>();
            List<List<Integer>> chunks = Lists.partition(uids, CHUNK_LIMIT);
            for (List<Integer> chunk :chunks)  {
                String listString = chunk.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                VKMethodCall call = new VKMethodCall.Builder()
                        .method("friends.get")
                        .args("user_ids",listString)
                        .args("fields", "nickname")
                        .args("order", "hints")
                        .version("5.131")
                        .build();
                Class<FriendsFriendsList> t = FriendsFriendsList.class;
                result.addAll(vkApiManager.execute(call, new ResponseApiParser<FriendsFriendsList>(t)));
            }
            return result;
        }
    }


}
