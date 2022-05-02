package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.adapters.ActionAdapter;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.db.PostViewModel;
import com.example.mytestretrofit.models.Action;
import com.example.mytestretrofit.models.MedicalTreatments;
import com.example.mytestretrofit.models.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TreatmentDetailActivity extends BaseActivity {
    Bundle arguments;
    PostViewModel postViewModel;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Patient patients;
    MedicalTreatments medicalTreatments;
    Action action;

    TextView tvName, tvSurname, tvDiagnosis, tvDate, tvAge;

    ActionAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatmen_detail);
        tvName = findViewById(R.id.tvDetailNamePatient);
        tvSurname = findViewById(R.id.tvDetailSurnamePatient);
        tvDiagnosis = findViewById(R.id.tvDetailDiagnosis);
        tvDate = findViewById(R.id.tv_detaild_date);
        tvAge = findViewById(R.id.tvDetailAgePatient);

        initDrawer();
        initConnection();
        initData();

        //Связываемся с нашим ExpandableListView:
        expListView = (ExpandableListView) findViewById(R.id.lv_Exp);

        //Подготавливаем список данных:
        prepareListData();

        listAdapter = new ActionAdapter(this, listDataHeader, listDataChild);

        //Настраиваем listAdapter:
        expListView.setAdapter(listAdapter);
    }

    /*
     * Подготавливаем данные для списка:
     */
    private void prepareListData() {
        arguments = getIntent().getExtras();

        medicalTreatments = arguments.getParcelable(MedicalTreatments.class.getSimpleName());
        List<Action> actionList = arguments.getParcelable(Action.class.getSimpleName());

        tvName.setText(patients.getName());
        tvSurname.setText(patients.getSurname());
        tvAge.setText(patients.getAge());
        tvDiagnosis.setText(medicalTreatments.getDiagnosis());
        tvDate.setText(medicalTreatments.getCreatedAt());


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //Добавляем данные о пунктах списка:
        for (Action action : actionList) {
            listDataHeader.add(action.getPzo());
        }
//        listDataHeader.add(String.valueOf(medicalTreatments.getId()));
//        listDataHeader.add("Пункт 2");
//        listDataHeader.add("Пункт 3");

//        List<String> actionList =

        for (MedicalTreatments treatments : patients.medicalTreatments) {
//            listDataChild.put(listDataHeader, )
        }
        //Добавляем данные о подпунктах:
        List<String> top250 = new ArrayList<String>();
        top250.add("Подпункт 1.1");
        top250.add("Подпункт 1.2");
        top250.add("Подпункт 1.3");
        top250.add("Подпункт 1.4");
        top250.add("Подпункт 1.5");
        top250.add("Подпункт 1.6");
        top250.add("Подпункт 1.7");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Подпункт 2.1");
        nowShowing.add("Подпункт 2.2");
        nowShowing.add("Подпункт 2.3");
        nowShowing.add("Подпункт 2.4");
        nowShowing.add("Подпункт 2.5");
        nowShowing.add("Подпункт 2.6");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("Подпункт 3.1");
        comingSoon.add("Подпункт 3.2");
        comingSoon.add("Подпункт 3.3");
        comingSoon.add("Подпункт 3.4");
        comingSoon.add("Подпункт 3.5");

        listDataChild.put(listDataHeader.get(0), top250);
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    void initData() {
        arguments = getIntent().getExtras();

        patients = arguments.getParcelable(Patient.class.getSimpleName());
        medicalTreatments = arguments.getParcelable(MedicalTreatments.class.getSimpleName());

        tvName.setText(patients.getName());
        tvSurname.setText(patients.getSurname());
        tvAge.setText(patients.getAge());
        tvDiagnosis.setText(medicalTreatments.getDiagnosis());
        Log.d("MyLog", String.valueOf(medicalTreatments.getCreatedAt()));
        tvDate.setText(medicalTreatments.getCreatedAt());
    }

    void initConnection() {
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
}