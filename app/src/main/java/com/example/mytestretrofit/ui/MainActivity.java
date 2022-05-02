package com.example.mytestretrofit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.mytestretrofit.R;
import com.example.mytestretrofit.adapters.PostAdapter;
import com.example.mytestretrofit.api.JsonPlaceHolderApi;
import com.example.mytestretrofit.models.MedicalTreatments;
import com.example.mytestretrofit.models.Patient;
import com.example.mytestretrofit.models.Patients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

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

public class MainActivity extends BaseActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    Toolbar toolbar;
    PostAdapter adapter;

    FloatingActionButton floatingActionButton;

    MedicalTreatments medicalTreatments;
    List<MedicalTreatments> listTreatments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initDrawer();

        recyclerView = findViewById(R.id.rv_Main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.fab);

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
        getPost();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPatientActivity.class);
                startActivity(intent);
            }
        });
    }

    void getPost() {
//        MedicalTreatments medicalTreatments = new MedicalTreatments(1, "f", "f", "f");
        Call<List<Patient>> call = jsonPlaceHolderApi.getUser();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (!response.isSuccessful()) {
                }
                List<Patient> users = response.body();

                PostAdapter.OnPostClickListener postClickListener = new PostAdapter.OnPostClickListener() {
                    @Override
                    public void onPostClick(Patient post, int position) {
                        Intent i = new Intent(MainActivity.this, SinglePostActivity.class);
                        //Сериализуем обьект Пациент и сохраняем его
                        i.putExtra(Patient.class.getSimpleName(), post);
//                        i.putExtra("id", post.getId());
//                        i.putExtra("name", post.getName());
//                        i.putExtra("patronymic", post.getPatronymic());
//                        i.putExtra("surname", post.getSurname());
//                        i.putExtra("age", post.getAge());
//                        i.putExtra("treatmentsSize", String.valueOf(post.getTreatmentsSize()));
//                        i.putExtra("treatments", String.valueOf(post.getId()));
                        startActivity(i);
                    }
                };
                adapter = new PostAdapter(users, medicalTreatments, postClickListener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

//    void updatePost() {
////        MedicalTreatments medicalTreatments = new MedicalTreatments(1, "f", "f", "f");
////        List<MedicalTreatments> list = new ArrayList<>();
////        list.add(medicalTreatments);
//        Patient post = new Patient(2, "name", "surname", "pater", "age", "list");
//        Call<Patient> call = jsonPlaceHolderApi.putPost(1, post);
//        call.enqueue(new Callback<Patient>() {
//            @Override
//            public void onResponse(Call<Patient> call, Response<Patient> response) {
//                if (!response.isSuccessful()) {
//                }
//                Patient users = response.body();
//                List<Patient> list = new ArrayList<>();
//                list.add(users);
//                PostAdapter.OnPostClickListener postClickListener = new PostAdapter.OnPostClickListener() {
//                    @Override
//                    public void onPostClick(Patients post, int position) {
//                        Intent i = new Intent(MainActivity.this, SinglePostActivity.class);
//                        i.putExtra("id", post.getId());
//                        i.putExtra("userId", post.getSurname());
//                        i.putExtra("title", post.getSurname());
//                        i.putExtra("body", post.getAge());
//                        startActivity(i);
//                    }
//                };
//
//                adapter = new PostAdapter(list, postClickListener);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<Patients> call, Throwable t) {
//
//            }
//        });
//
//    }

}