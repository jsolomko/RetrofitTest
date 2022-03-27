package com.example.mytestretrofit.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytestretrofit.models.Post;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository postRepository;
    private final LiveData<List<Post>> mAllPosts;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        mAllPosts = postRepository.getAllPost();
    }

    LiveData<List<Post>> getAllPost() {
        return mAllPosts;
    }

    public void insert(Post post) {
        postRepository.insert(post);
    }
}
