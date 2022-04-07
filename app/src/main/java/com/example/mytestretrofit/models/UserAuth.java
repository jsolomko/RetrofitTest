package com.example.mytestretrofit.models;

import com.google.gson.annotations.SerializedName;

public class UserAuth {
    @SerializedName("name")
    String username;
    @SerializedName("password")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
