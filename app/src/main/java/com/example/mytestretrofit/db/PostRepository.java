package com.example.mytestretrofit.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mytestretrofit.api.PostDAO;
import com.example.mytestretrofit.models.Post;

import java.util.List;

public class PostRepository {
    private PostDAO postDAO;
    private LiveData<List<Post>> mAllPostLiveData;

    PostRepository(Application application) {
        PostRoomDatabase db = PostRoomDatabase.getDatabase(application);
        postDAO = db.postDAO();
        mAllPostLiveData = postDAO.getPost();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Post>> getAllPost() {
        return mAllPostLiveData;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Post post) {
        PostRoomDatabase.databaseWriteExecutor.execute(() -> {
            postDAO.insert(post);
        });
    }

}
