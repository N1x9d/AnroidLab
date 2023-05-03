package com.example.vk.model;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.base.dto.BaseLikesInfo;
import com.vk.sdk.api.comment.dto.CommentThread;

public class WallComment {
    @SerializedName("id")
    public Integer id;
    @SerializedName("from_id")
    public Integer fromId;
    @SerializedName("date")
    public Long date;
    @SerializedName("text")
    public String text;
    @SerializedName("post_id")
    public Integer postId;
    @SerializedName("owner_id")
    public Integer ownerId;
    @SerializedName("likes")
    public BaseLikesInfo likes;
    @SerializedName("reply_to_user")
    public Integer replyToUser;
    @SerializedName("reply_to_comment")
    public Integer replyToComment;
    @SerializedName("thread")
    public CommentThread thread;
    @SerializedName("deleted")
    public Boolean deleted;
    @SerializedName("pid")
    public Integer pid;
}
