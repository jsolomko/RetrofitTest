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
//        action = arguments.getParcelable(Action.class.getSimpleName());


        List<Action> actions = medicalTreatments.getActions();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        int x = 0;
        for (Action action : actions) {
            List<String> actionList = new ArrayList<>();
            listDataHeader.add("Добавлен: " + action.getCreatedAt().split("T")[0]);
            actionList.add("Глаз: " + action.getEye());
            actionList.add("ПЗО: " + action.getPzo());
            actionList.add("Особенности: " + action.getFeatures());
            actionList.add("Назначения: " + action.getAppointments());
            actionList.add("Операция: " + action.getSurgeonId());
            actionList.add("Дата создания: " + action.getCreatedAt().split("T")[0]);
            listDataChild.put(listDataHeader.get(x), actionList);
            x++;
            Log.d("MyLog", "Size: " + String.valueOf(actionList.size()));
        }
//        Добавляем данные о пунктах списка:
//        int x = 0;
//        for (Action action : actions) {
//            listDataHeader.add(action.getCreatedAt());
//            listDataChild.put(listDataHeader.get(x), actionList);
//            x++;
//
//        }
//        listDataHeader.add(String.valueOf(medicalTreatments.getId()));


        //Добавляем данные о подпунктах:
//        for (int i = 0; i <= actionList.size(); i++) {
//            listDataChild.put(listDataHeader.get(i), actionList);
//        }
//
//           listDataChild.put(listDataHeader.get(0), actionList);
//           listDataChild.put(listDataHeader.get(2), actionList);
//           listDataChild.put(listDataHeader.get(3), actionList);


    }


    void initData() {
        arguments = getIntent().getExtras();

        patients = arguments.getParcelable(Patient.class.getSimpleName());
        medicalTreatments = arguments.getParcelable(MedicalTreatments.class.getSimpleName());

        tvName.setText(patients.getName());
        tvSurname.setText(patients.getSurname());
        tvAge.setText(patients.getAge());
        tvDiagnosis.setText(medicalTreatments.getDiagnosis());
//        Log.d("MyLog", String.valueOf(medicalTreatments.getCreatedAt()));
        tvDate.setText(medicalTreatments.getCreatedAt().split("T")[0]);
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