package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.adapters.TreatmentAdapter;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.models.MedicalTreatments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TreatmentActivity extends BaseActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    RecyclerView recyclerView;

    TreatmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tretment_activity);

        recyclerView = findViewById(R.id.rc_Tretments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        initDrawer();
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
                                .header("Authorization", "Bearer " + sharedPreferences.getString("TOKEN", "fsd"))
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
        getTreatments();
    }

    void getTreatments() {
        Call<List<MedicalTreatments>> call = jsonPlaceHolderApi.getMedicalTreatments();
        call.enqueue(new Callback<List<MedicalTreatments>>() {
            @Override
            public void onResponse(Call<List<MedicalTreatments>> call, Response<List<MedicalTreatments>> response) {
                List<MedicalTreatments> list = response.body();
                TreatmentAdapter.OnTreatmentsListener onTreatmentsListener = new TreatmentAdapter.OnTreatmentsListener() {
                    @Override
                    public void onTreatmentClick(MedicalTreatments medicalTreatment, int position) {

                    }
                };

                adapter = new TreatmentAdapter(list, onTreatmentsListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MedicalTreatments>> call, Throwable t) {
            }
        });
    }
}