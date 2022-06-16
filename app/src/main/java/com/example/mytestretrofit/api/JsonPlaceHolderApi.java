package com.example.mytestretrofit.api;

import androidx.lifecycle.LiveData;

import com.example.mytestretrofit.models.LoginResponse;
import com.example.mytestretrofit.models.MedicalTreatments;
import com.example.mytestretrofit.models.Patient;
import com.example.mytestretrofit.models.Patients;
import com.example.mytestretrofit.models.UserAuth;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("patients?sort=id,DESC")
    Call<List<Patient>> getUser();

    @GET("medicaltreatment")
    Call<List<MedicalTreatments>> getMedicalTreatments();

    @POST("patients")
    Call<Patients> createPost(@Body Patients post);

    @POST("medicaltreatment")
    Call<MedicalTreatments> createTreatment(@Body MedicalTreatments treatment);

    @POST("auth/login")
    Call<LoginResponse> login(@Body UserAuth userAuth);

    @DELETE("patients/{id}")
    Call<Void> delete(@Path("id") int id);

    @FormUrlEncoded
    @POST("auth/login")
    Call<List<LoginResponse>> auth1(
            @Field("name") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("auth/login")
    Call<UserAuth> authMap(@FieldMap Map<String, String> fields);

    @PUT("posts/{id}")
    Call<Patients> putPost(@Path("id") int id, @Body Patients post);

    @PATCH("posts/{id}")
    Call<Patients> patchPost(@Path("id") int id, @Body Patients post);


}
