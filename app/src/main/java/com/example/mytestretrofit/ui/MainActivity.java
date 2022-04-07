package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
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
import com.example.mytestretrofit.models.LoginResponse;
import com.example.mytestretrofit.models.Post;
import com.example.mytestretrofit.models.SessionManager;
import com.example.mytestretrofit.models.UserAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
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


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalReq = chain.request();
                        Request newReq = originalReq.newBuilder()
                                .header("Interceptor-Header", "myHeaders")
                                .header("Content-Type", "application/json")
                                .build();
                        return chain.proceed(newReq);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        //Создаем билдер ретрофита
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.222.54:5487/")
//                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//      getPost();
//        updatePost();
//        login();

    }

    void updatePost() {
        Post post = new Post(4, 1, "titte", "body");
        Call<Post> call = jsonPlaceHolderApi.putPost(1, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                }
                Post users = response.body();
                List<Post> list = new ArrayList<>();
                list.add(users);
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

                PostAdapter adapter = new PostAdapter(list, postClickListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    void getPost() {
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

    void login() {

//        Map<String, String> fields = new HashMap<>();
//        fields.put("username", "name");
//        fields.put("password", "123456");
//        UserAuth userAuth = new UserAuth("name", "123456");
//        Call<LoginResponse> call = jsonPlaceHolderApi.login(userAuth);
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                LoginResponse loginResponse = response.body();
//                SessionManager sessionManager = new SessionManager();
//                sessionManager.saveAuthToken(loginResponse.getAccessToken());
//
//                List<Post> posts = new ArrayList<>();
//                Post post = new Post(1, 2, loginResponse.getUser(), loginResponse.getAccessToken().toString());
//                posts.add(post);
//                PostAdapter.OnPostClickListener postClickListener = new PostAdapter.OnPostClickListener() {
//                    @Override
//                    public void onPostClick(Post post, int position) {
//                        Intent i = new Intent(MainActivity.this, SinglePostActivity.class);
//                        i.putExtra("id", post.getId());
//                        i.putExtra("userId", post.getUserId());
//                        i.putExtra("title", post.getTitle());
//                        i.putExtra("body", post.getBody());
//                        startActivity(i);
//                    }
//                };
//                PostAdapter postAdapter = new PostAdapter(posts, postClickListener);
//                recyclerView.setAdapter(postAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//            }
//        });


    }

}