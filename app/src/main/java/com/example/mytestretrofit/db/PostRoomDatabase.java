package com.example.mytestretrofit.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mytestretrofit.api.PostDAO;
import com.example.mytestretrofit.models.Post;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class PostRoomDatabase extends RoomDatabase {
    public abstract PostDAO postDAO();

    private static volatile PostRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PostRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PostRoomDatabase.class, "post_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
