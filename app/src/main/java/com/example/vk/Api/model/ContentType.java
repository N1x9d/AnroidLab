package com.example.vk.Api.model;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.notes.dto.NotesNote;
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentType;

public class ContentType {
    @SerializedName("type")
    public WallWallpostAttachmentType type;
    @SerializedName("note")
    public NotesNote note = null;
    @SerializedName("photo")
    public Photo photo = null;
    @SerializedName("doc")
    public Doc doc = null;
}
