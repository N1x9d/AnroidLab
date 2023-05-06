package com.example.vk.data.Entities;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.vk.Api.model.VKNewsPost;
import com.vk.sdk.api.newsfeed.dto.NewsfeedNewsfeedItemType;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

@Entity(tableName = "post")
public class NewsPost {
    @PrimaryKey
    @ColumnInfo(name="Post_id")
    private Integer id;
    @ColumnInfo(name="Post_sourceId")
    private Integer sourceId;
    @ColumnInfo(name="Post_date")
    private Integer date;

    @Ignore
    private NewsfeedNewsfeedItemType type;
    @ColumnInfo(name="Post_type")
    private Integer typeInt;
    @ColumnInfo(name="Post_canLike")
    private Boolean canLike;

    @ColumnInfo(name="Post_canComment")
    private Boolean canComment;
    @ColumnInfo(name="Post_likeCount")
    private Integer count;

    @ColumnInfo(name="Post_commentCount")
    private Integer commentCount;

    @ColumnInfo(name="Post_isLiked")
    private Boolean userLikes;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Post_image")
    private byte[] image;

    @ColumnInfo(name="Post_nextFrom")
    private String nextFrom;

    @ColumnInfo(name="Post_text")
    private String text;

    @ColumnInfo(name="Post_imageLink")
    private String imageLink;
    @Ignore
    private Bitmap imageBitmap;
    @ColumnInfo(name = "Post_userName")
    private String userName;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "Post_avatar")
    private byte[] userAvatar;

    @ColumnInfo(name = "Post_avatarLink")
    private String userAvatarLink;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Boolean getCanLike() {
        return canLike;
    }

    public void setCanLike(Boolean canLike) {
        this.canLike = canLike;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Boolean userLikes) {
        this.userLikes = userLikes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(Integer typeInt) {
        if(typeInt != null) {
            this.type = NewsfeedNewsfeedItemType.values()[typeInt];
            this.typeInt = typeInt;
        }
    }

    public NewsfeedNewsfeedItemType getType() {
        return type;
    }

    public void setType(NewsfeedNewsfeedItemType type) {
        this.typeInt = type.ordinal();
        this.type = type;
    }

    public String getNextFrom() {
        return nextFrom;
    }

    public void setNextFrom(String nextFrom) {
        this.nextFrom = nextFrom;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCanComment() {
        return canComment;
    }

    public void setCanComment(Boolean canComment) {
        this.canComment = canComment;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatarLink() {
        return userAvatarLink;
    }

    public void setUserAvatarLink(String userAvatarLink) {
        this.userAvatarLink = userAvatarLink;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }
}
