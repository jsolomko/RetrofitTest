package com.example.mytestretrofit.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private int statusCode;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("user")
    private User user;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResponse(int statusCode, String accessToken, User user) {
        this.statusCode = statusCode;
        this.access_token = accessToken;
        this.user = user;
    }
}
