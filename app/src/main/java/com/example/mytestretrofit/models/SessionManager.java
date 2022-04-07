package com.example.mytestretrofit.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mytestretrofit.R;

public class SessionManager {
    Context context;

    final String USER_TOKEN = "user_token";
    SharedPreferences preferences = context.getSharedPreferences("sd", Context.MODE_PRIVATE);


    public void saveAuthToken(String token) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        return preferences.getString(USER_TOKEN, null);
    }

}
