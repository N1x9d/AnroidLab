package com.example.vk.Api.model;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.photos.dto.PhotosImage;
import com.vk.sdk.api.photos.dto.PhotosPhotoSizes;

import java.util.List;

public class Photo {
    @SerializedName("date")
    Integer date;
    @SerializedName("id")
    Integer id;
    @SerializedName("images")
    public List<PhotosImage> images = null;
    @SerializedName("photo_256")
    public String photo256= null;
    @SerializedName("sizes")
    public List<PhotosPhotoSizes> sizes = null;
    @SerializedName("text")
    String text = null;
    @SerializedName("width")
    Integer width = null;
}
