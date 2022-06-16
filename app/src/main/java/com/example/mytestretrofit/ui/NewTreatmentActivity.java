package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.db.PostViewModel;
import com.example.mytestretrofit.models.Action;
import com.example.mytestretrofit.models.Affiliation;
import com.example.mytestretrofit.models.MedicalTreatments;
import com.example.mytestretrofit.models.Patient;
import com.example.mytestretrofit.models.Patients;

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

public class NewTreatmentActivity extends BaseActivity {
    Spinner spinnerAffiliation, spinnerDoctors;

    PostViewModel postViewModel;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    Bundle arguments;
    EditText edDiagnosis, edMKB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatment);

        initDrawer();

        edDiagnosis = findViewById(R.id.ed_diagnosis);
        edMKB = findViewById(R.id.ed_mkb);

        spinnerAffiliation = findViewById(R.id.spinner_affiliation);
        spinnerDoctors = findViewById(R.id.spinner_doctors);
        initSpinners();

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


    }

    public void createTreatment(View view) {
        arguments = getIntent().getExtras();
        Patient patients;
        patients = arguments.getParcelable(Patient.class.getSimpleName());
        Toast.makeText(getApplicationContext(), "Обращение успешно добавлено", Toast.LENGTH_SHORT).show();

        Affiliation affiliation = new Affiliation("ОМС");
        List<Action> actionList = new ArrayList<>();
//        actionList.add(new Action(1, "od", "12.00",""));


        MedicalTreatments medicalTreatments = new MedicalTreatments(1, edDiagnosis.getText().toString(), edMKB.getText().toString(), affiliation, patients);


        Call<MedicalTreatments> call = jsonPlaceHolderApi.createTreatment(medicalTreatments);
        call.enqueue(new Callback<MedicalTreatments>() {
            @Override
            public void onResponse(Call<MedicalTreatments> call, Response<MedicalTreatments> response) {

            }

            @Override
            public void onFailure(Call<MedicalTreatments> call, Throwable t) {

            }
        });

    }

    void initSpinners() {
        String[] affiliationList = new String[]{"ОМС", "Стационар плановый", "СДП"};
        ArrayAdapter<String> affiliationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, affiliationList);
        affiliationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAffiliation.setAdapter(affiliationArrayAdapter);

        String[] doctors = new String[]{"Попов Н.А.", "Бедикаян Р.С.", "Семыкин В.Д."};
        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctors);
        affiliationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctors.setAdapter(doctorAdapter);
    }

}