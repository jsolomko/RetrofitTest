package com.example.mytestretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.db.PostViewModel;
import com.example.mytestretrofit.models.Post;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SinglePostActivity extends AppCompatActivity {
    TextView id, userId, title, body;
    PostViewModel postViewModel;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        id = findViewById(R.id.tv_PostId);
        userId = findViewById(R.id.tv_PostUserId);
        title = findViewById(R.id.tv_PostTitle);
        body = findViewById(R.id.tv_PostBody);


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        //Создаем билдер ретрофита
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            id.setText(arguments.get("id").toString());
            userId.setText(arguments.get("userId").toString());
            title.setText(arguments.get("title").toString());
            body.setText(arguments.get("body").toString());
        }
    }

    public void saveToDB(View view) {
        id.getText();
        Post post = new Post(Integer.parseInt(String.valueOf(id.getText())), Integer.parseInt(String.valueOf(userId.getText())),
                title.getText().toString(),
                body.getText().toString());
        postViewModel.insert(post);
    }

    public void createPost(View view) {
        Post post = new Post(Integer.parseInt(String.valueOf(id.getText())), Integer.parseInt(String.valueOf(userId.getText())),
                title.getText().toString(),
                body.getText().toString());
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(getApplicationContext(), post.getBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }
}