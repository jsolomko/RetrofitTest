package com.example.mytestretrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Patient implements Parcelable {

    private int id;
    public String name;
    public String surname;
    public String patronymic;
    public String age;
    public List<MedicalTreatments> medicalTreatments;

    //конструктор для Parcelable
    public Patient(String name, String surname, String patronymic, String age) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.age = age;
    }

    public Patient(String name, String surname, String patronymic, String age, List<MedicalTreatments> treatments) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.age = age;
        this.medicalTreatments = treatments;
    }

    public Patient(int id, String name, String surname, String patronymic, String age, List<MedicalTreatments> treatments) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.age = age;
        this.medicalTreatments = treatments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<MedicalTreatments> getTreatments() {
        return medicalTreatments;
    }

    public int getTreatmentsSize() {
        return medicalTreatments.size();
    }

    public void setTreatments(List<MedicalTreatments> treatments) {
        this.medicalTreatments = treatments;
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel parcel) {
            int id = parcel.readInt();
            String name = parcel.readString();
            String surname = parcel.readString();
            String age = parcel.readString();
            String patronymic = parcel.readString();
            List<MedicalTreatments> medicalTreatmentsList = new ArrayList<>();
            parcel.readList(medicalTreatmentsList, MedicalTreatments.class.getClassLoader());
            return new Patient(id, name, surname, patronymic, age, medicalTreatmentsList);
        }

        @Override
        public Patient[] newArray(int i) {
            return new Patient[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(age);
        parcel.writeString(patronymic);
        parcel.writeList(medicalTreatments);
    }
}
