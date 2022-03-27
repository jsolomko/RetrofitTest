package com.example.mytestretrofit.api;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mytestretrofit.models.Post;

import java.util.List;

@Dao
public interface PostDAO {
    @Insert
    void insert(Post post);

    @Query("SELECT * FROM posts_table")
    LiveData<List<Post>> getPost();
}
