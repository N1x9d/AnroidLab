package com.example.vk.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.vk.CommentActivity;
import com.example.vk.MainActivity2;
import com.example.vk.adapters.NewsFeedAdapter;
import com.example.vk.data.DAO.NewsPostDAO;
import com.example.vk.data.DAO.UserDAO;
import com.example.vk.data.Entities.NewsPost;
import com.example.vk.data.Entities.User;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AsyncTasks {
    public static class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        NewsPost item;

        ImageView imageView;
        public DownLoadImageTask(ImageView imageView, NewsPost item){
            this.imageView = imageView;
            this.item = item;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage)
                        .openStream();
                logo = BitmapFactory.decodeStream(is);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                item.getImageBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                item.setImage(stream.toByteArray());
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    public static class DownLoadAvatarTask extends AsyncTask<String,Void,Bitmap> {
        User item;
        ImageView imageView;
        public DownLoadAvatarTask(ImageView imageView, User item){
            this.imageView = imageView;
            this.item = item;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage)
                        .openStream();
                logo = BitmapFactory.decodeStream(is);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                item.getImageBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                item.setImage(stream.toByteArray());
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    public static  class TaskInsertUsers extends AsyncTask<Void,Void, Void>{

        private UserDAO userDAO;
        private MainActivity2 activity;
        private List<User> users;

        public TaskInsertUsers(MainActivity2 activity, List<User> users) {
            this.userDAO = activity.userDAO;
            this.activity = activity;
            this.users = users;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            userDAO.insertAll(users);
            return null;
        }
    }

    public static  class TaskGetUsers extends AsyncTask<Void,Void, List<User>>{

        private UserDAO userDAO;
        private MainActivity2 activity;
        public TaskGetUsers(MainActivity2 activity) {
            this.userDAO = activity.userDAO;


            this.activity = activity;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            try {
                List<User> f = userDAO.getAll();
                if (f == null)
                    return null;
                return f;
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> result){
            if(result != null) {
                activity.users = result;
            }
        }
    }

    public static  class TaskGetCommentUsers extends AsyncTask<Void,Void, List<User>>{

        private UserDAO userDAO;
        private CommentActivity activity;
        public TaskGetCommentUsers(CommentActivity activity) {
            this.userDAO = activity.userDAO;
            this.activity = activity;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            try {
                List<User> f = userDAO.getAll();
                if (f == null)
                    return null;
                return f;
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> result){
            if(result != null) {
                activity.users = result;
            }
        }
    }

    public static  class TaskInsertPosts extends AsyncTask<Void,Void, Void>{

        private NewsPostDAO newsPostDAO;
        private MainActivity2 activity;
        private List<NewsPost> newsPosts;

        public TaskInsertPosts(MainActivity2 activity, List<NewsPost> newsPosts) {
            this.newsPostDAO = activity.newsPostDAO;


            this.activity = activity;
            this.newsPosts = newsPosts;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsPostDAO.insertAll(newsPosts);

            return null;
        }
    }

    public static  class TaskGetPosts extends AsyncTask<Void,Void, List<NewsPost>>{

        private NewsPostDAO newsPostDAO;
        private MainActivity2 activity;
        public TaskGetPosts(MainActivity2 activity) {
            this.newsPostDAO = activity.newsPostDAO;


            this.activity = activity;
        }

        @Override
        protected List<NewsPost> doInBackground(Void... voids) {
            try {
                List<NewsPost> f = newsPostDAO.getAll();
                if (f == null)
                    return null;
                return f;
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<NewsPost> result){
            if(result != null) {
                activity.news.setAdapter(new NewsFeedAdapter(result, activity));
            }
        }
    }


}
