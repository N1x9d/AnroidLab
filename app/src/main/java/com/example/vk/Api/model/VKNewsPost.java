package com.example.vk.Api.model;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.base.dto.BaseBoolInt;
import com.vk.sdk.api.base.dto.BaseCommentsInfo;
import com.vk.sdk.api.base.dto.BaseLikesInfo;
import com.vk.sdk.api.base.dto.BaseRepostsInfo;
import com.vk.sdk.api.newsfeed.dto.NewsfeedNewsfeedItemType;
import com.vk.sdk.api.wall.dto.WallPostType;

import java.util.List;

public class VKNewsPost {
    @SerializedName("type")
    public NewsfeedNewsfeedItemType type;
    @SerializedName("source_id")
    public Integer sourceId;
    @SerializedName("date")
    public Integer date;
    @SerializedName("comments")
    public BaseCommentsInfo comments = null;
    @SerializedName("marked_as_ads")
    public BaseBoolInt markedAsAds = null;
    @SerializedName("attachments")
    public List<ContentType> attachments = null;
    @SerializedName("id")
    public Integer id = null;
    @SerializedName("likes")
    public BaseLikesInfo likes = null;
    @SerializedName("post_type")
    public WallPostType postType = null;
    @SerializedName("reposts")
    public BaseRepostsInfo reposts= null;
    @SerializedName("text")
    public String text = null;

}
