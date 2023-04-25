package com.example.vk.model;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.docs.dto.DocsDocPreview;

public class Doc {
    @SerializedName("title")
    public String title;
    @SerializedName("size")
    public Integer size;
    @SerializedName("ext")
    public String ext;
    @SerializedName("date")
    public Integer date;
    @SerializedName("type")
    public Integer type;
    @SerializedName("url")
    public String url = null;
    @SerializedName("preview")
    public DocsDocPreview preview = null;
}
