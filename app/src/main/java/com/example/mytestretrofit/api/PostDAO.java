package com.example.mytestretrofit.api;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mytestretrofit.models.Patients;

import java.util.List;

@Dao
public interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Patients post);

    @Query("SELECT * FROM posts_table")
    LiveData<List<Patients>> getPost();

}
