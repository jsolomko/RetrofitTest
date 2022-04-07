package com.example.mytestretrofit.models;

public class User {
    private Integer id;
    private String login;
    private String name;
    private String middlename;
    private String surname;
    private String email;

    public User(Integer id, String login, String name, String middlename, String surname, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.middlename = middlename;
        this.surname = surname;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
