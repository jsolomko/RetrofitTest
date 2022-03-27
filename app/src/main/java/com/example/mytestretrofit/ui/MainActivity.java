package com.example.mytestretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.adapters.PostAdapter;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_Main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Создаем билдер ретрофита
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getUser();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                }
                List<Post> users = response.body();
                PostAdapter.OnPostClickListener postClickListener = new PostAdapter.OnPostClickListener() {
                    @Override
                    public void onPostClick(Post post, int position) {
                        Intent i = new Intent(MainActivity.this, SinglePostActivity.class);
                        i.putExtra("id", post.getId());
                        i.putExtra("userId", post.getUserId());
                        i.putExtra("title", post.getTitle());
                        i.putExtra("body", post.getBody());
                        startActivity(i);
                    }
                };

                PostAdapter adapter = new PostAdapter(users, postClickListener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }


}