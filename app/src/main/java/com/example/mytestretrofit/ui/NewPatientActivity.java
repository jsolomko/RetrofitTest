package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.models.Patients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPatientActivity extends AppCompatActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    EditText edName, edPatronymic, edSurname, edAge, edTretments;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public int year;
    public int month;
    public int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        edName = findViewById(R.id.editPersonName);
        edPatronymic = findViewById(R.id.editPersonPatronymic);
        edPatronymic = findViewById(R.id.editPersonPatronymic);
        edSurname = findViewById(R.id.editPersonSurname);
        edAge = findViewById(R.id.editPersonAge);
        edAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                 year = cal.get(Calendar.YEAR);
                 month = cal.get(Calendar.MONTH);
                 day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NewPatientActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                edAge.setText(date);
            }

        };

        edTretments = findViewById(R.id.editPersonTretments);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

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
    }

    public void btn_addPatient(View view) {
        Patients person = new Patients(String.valueOf(edName.getText()), String.valueOf(edSurname.getText()), String.valueOf(edPatronymic.getText())
                , String.valueOf(edAge.getText()), String.valueOf(edTretments.getText()));
        Call<Patients> call = jsonPlaceHolderApi.createPost(person);
        call.enqueue(new Callback<Patients>() {
            @Override
            public void onResponse(Call<Patients> call, Response<Patients> response) {
                if (response.code() == 201) {
                    Intent i = new Intent(NewPatientActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Patients> call, Throwable t) {

            }
        });


    }
}