package com.example.vk.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.vk.data.DAO.NewsPostDAO;
import com.example.vk.data.DAO.UserDAO;
import com.example.vk.data.Entities.Comment;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;

@Database(entities = {NewsPost.class, User.class, Comment.class}, version = 1, exportSchema = false)
public abstract class AppData extends RoomDatabase{
    public abstract NewsPostDAO newsPostDAO();
    public abstract UserDAO userDAO();
}
