package com.example.vk.Api.model;

import com.google.gson.annotations.SerializedName;

public class VKUser {
    @SerializedName("id")
    public Integer id;
    @SerializedName("photo_50")
    public String photo50;
    @SerializedName("photo_100")
    public String photo100;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
}
