package com.example.vk.Api;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.vk.api.sdk.VKApiJSONResponseParser;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.exceptions.VKApiExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseApiParser <T> implements VKApiJSONResponseParser<ArrayList<T>> {

        private final Class<T> type;

        public ResponseApiParser(Class<T> type) {
            this.type = type;
        }

        @Override
        public ArrayList<T> parse(@NonNull JSONObject jsonObject) throws VKApiException, VKApiExecutionException, JSONException, Exception {

            try {
                JSONObject jo = jsonObject.getJSONObject("response");
                JSONArray ja = jo.getJSONArray("items");
                ArrayList<T> r = new ArrayList<>(ja.length());
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject users = ja.getJSONObject(i);
                    T user = new Gson().fromJson(ja.getJSONObject(i).toString() , type);
                    r.add(user);
                }
                return r;
            } catch (JSONException e) {
                throw new Exception(e);
            }
        }
    }

