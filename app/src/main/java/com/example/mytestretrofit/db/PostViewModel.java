package com.example.mytestretrofit.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mytestretrofit.models.Patients;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository postRepository;
    private final LiveData<List<Patients>> mAllPosts;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        mAllPosts = postRepository.getAllPost();
    }

    LiveData<List<Patients>> getAllPost() {
        return mAllPosts;
    }

    public void insert(Patients post) {
        postRepository.insert(post);
    }
}
