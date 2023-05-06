package com.example.vk.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comment")
public class Comment {

    @PrimaryKey
    @ColumnInfo(name="Comment_id")
    private Integer id;

    @ColumnInfo(name="Comment_sourceId")
    private Integer sourceId;

    @ColumnInfo(name="Comment_postId")
    private Integer postId;

    @ColumnInfo(name="Comment_text")
    private String text;

    @ColumnInfo(name="Comment_date")
    private Long date;

    @ColumnInfo(name="Comment_authorId")
    private Integer from;

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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
