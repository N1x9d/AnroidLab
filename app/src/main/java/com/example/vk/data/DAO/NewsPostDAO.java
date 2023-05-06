package com.example.vk.data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vk.data.Entities.NewsPost;

import java.util.List;

@Dao
public interface  NewsPostDAO {
    @Query("SELECT * FROM post ORDER BY Post_date DESC ")
    List<NewsPost> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsPost> Post);

    @Delete
    void delete(NewsPost user);

    @Query("SELECT * FROM post ORDER BY Post_date DESC LIMIT 1")
    NewsPost getLast();

    @Update
    void updatePostImage(NewsPost newsPost);
}
