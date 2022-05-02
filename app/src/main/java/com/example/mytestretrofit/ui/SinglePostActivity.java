package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.adapters.TreatmentAdapter;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.db.PostViewModel;
import com.example.mytestretrofit.models.Action;
import com.example.mytestretrofit.models.MedicalTreatments;
import com.example.mytestretrofit.models.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SinglePostActivity extends BaseActivity {
    TextView id, name, surname, age, treatments;
    PostViewModel postViewModel;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    Bundle arguments;
    Intent i;
    Intent i2;

    TreatmentAdapter treatmentAdapter;
    RecyclerView recyclerView;
    FloatingActionButton fab_new_treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        initDrawer();

        id = findViewById(R.id.tv_PostId);
        name = findViewById(R.id.tv_PostName);
        surname = findViewById(R.id.tv_PostSurname);
        age = findViewById(R.id.tv_PostAge);
        treatments = findViewById(R.id.tv_PostTreatments);

        recyclerView = findViewById(R.id.rv_patient_treatment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab_new_treatment = findViewById(R.id.fab_treatment);

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

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        arguments = getIntent().getExtras();

        Patient patients;
        MedicalTreatments medicalTreatments;
        Action action;
        if (arguments != null) {
            patients = arguments.getParcelable(Patient.class.getSimpleName());


            i = new Intent(SinglePostActivity.this, NewTreatmentActivity.class);
            i.putExtra(Patient.class.getSimpleName(), patients);

            id.setText(String.valueOf(patients.getId()));
            name.setText(String.valueOf(patients.getName()));
            surname.setText(String.valueOf(patients.getSurname()));
            age.setText(String.valueOf(patients.getAge()));
            treatments.setText(String.valueOf(patients.getTreatmentsSize()));

            TreatmentAdapter.OnTreatmentsListener treatmentsListener = new TreatmentAdapter.OnTreatmentsListener() {
                @Override
                public void onTreatmentClick(MedicalTreatments medicalTreatment1, int position) {
                    i2 = new Intent(SinglePostActivity.this, TreatmentDetailActivity.class);
                    i2.putExtra(MedicalTreatments.class.getSimpleName(), medicalTreatment1);
                    i2.putExtra(Patient.class.getSimpleName(), patients);
                    i2.putExtra(Action.class.getSimpleName(), (Parcelable) medicalTreatment1.getActions());
                    startActivity(i2);
                }
            };

            treatmentAdapter = new TreatmentAdapter(patients.getTreatments(), treatmentsListener);
            recyclerView.setAdapter(treatmentAdapter);

        }

        fab_new_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });

//        if (arguments != null) {
//            id.setText(String.valueOf(arguments.get("id")));
//            name.setText(String.valueOf(arguments.get("name")));
//            surname.setText(String.valueOf(arguments.get("surname")));
//            age.setText(String.valueOf(arguments.get("age")));
//            treatments.setText(String.valueOf(arguments.get("treatmentsSize")));
//            treatments.setText(String.valueOf(arguments.get("treatments")));
//        }

    }


//    public void createPost(View view) {
//        Patients patients = new Patients("IVAN3", "PEROV", "HOHLUHOV", "2002-09-12", new MedicalTreatments());
//
//        Call<Patients> call = jsonPlaceHolderApi.createPost(patients);
//        call.enqueue(new Callback<Patients>() {
//            @Override
//            public void onResponse(Call<Patients> call, Response<Patients> response) {
//                if (response.code() == 201) {
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Patients> call, Throwable t) {
//
//            }
//        });
//    }

    public void deletePatient(View view) {
        Call<Void> call = jsonPlaceHolderApi.delete(Integer.parseInt(arguments.get("id").toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent i = new Intent(SinglePostActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    //    public void saveToDB(View view) {
//        id.getText();
//        Patients post = new Patients(Integer.parseInt(String.valueOf(id.getText())), Integer.parseInt(String.valueOf(userId.getText())),
//                title.getText().toString(),
//                body.getText().toString());
//        postViewModel.insert(post);
//    }
//


}