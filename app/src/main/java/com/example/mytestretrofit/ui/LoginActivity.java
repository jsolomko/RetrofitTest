package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.adapters.PostAdapter;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.db.PostViewModel;
import com.example.mytestretrofit.models.LoginResponse;
import com.example.mytestretrofit.models.Post;
import com.example.mytestretrofit.models.SessionManager;
import com.example.mytestretrofit.models.UserAuth;

import org.jetbrains.annotations.NotNull;

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

public class LoginActivity extends AppCompatActivity {
    TextView tvLogin;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    String x = "";
    PostViewModel postViewModel;
    List<LoginResponse> list;
    EditText edName, edPassword;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvLogin = findViewById(R.id.tv_Login);

        edName = findViewById(R.id.ed_Name);
        edPassword = findViewById(R.id.ed_Password);


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

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
//        getPost();
//        updatePost();
//        login();
    }

//    void login() {
//
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
//
//                    }
//                };
//                tvLogin.setText("loginResponse.getUser()");
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//            }
//        });
//    }

    public void initLogin(View view) {

        UserAuth userAuth = new UserAuth("zyrra", "123456");
        Call<LoginResponse> call = jsonPlaceHolderApi.login(userAuth);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TOKEN", loginResponse.getAccessToken());
                editor.apply();


                tvLogin.setText(sharedPreferences.getString("TOKEN","fsd"));


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }

    public void btn_Login(View view) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}