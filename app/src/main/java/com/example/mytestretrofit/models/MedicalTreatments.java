package com.example.mytestretrofit.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MedicalTreatments implements Parcelable {
    public int id;
    public int attending_doctor_id;
    public String diagnosis;
    public String mkb;
    public String createdAt;
    public String updatedAt;
    public List<Action> actions;
    public Patient patient;
    public Affiliation affiliation;

    public MedicalTreatments(int attending_doctor_id, String diagnosis, String mkb, String createdAt,
                             String updatedAt, List<Action> actions, Patient patient, Affiliation affiliation) {
        this.attending_doctor_id = attending_doctor_id;
        this.diagnosis = diagnosis;
        this.mkb = mkb;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.actions = actions;
        this.patient = patient;
        this.affiliation = affiliation;
    }

    public MedicalTreatments(int attending_doctor_id, String diagnosis, String mkb, Affiliation affiliation, Patient patient) {
        this.attending_doctor_id = attending_doctor_id;
        this.diagnosis = diagnosis;
        this.mkb = mkb;
        this.affiliation = affiliation;
        this.patient = patient;
    }

    public MedicalTreatments(int id, String diagnosis, String mkb, String createdAt) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.mkb = mkb;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAttending_doctor_id() {
        return attending_doctor_id;
    }

    public void setAttending_doctor_id(Integer attending_doctor_id) {
        this.attending_doctor_id = attending_doctor_id;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMkb() {
        return mkb;
    }

    public void setMkb(String mkb) {
        this.mkb = mkb;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public String getDiagnosis() {
        return diagnosis;
    }


    public static final Creator<MedicalTreatments> CREATOR = new Creator<MedicalTreatments>() {
        @Override
        public MedicalTreatments createFromParcel(Parcel parcel) {
            int id = parcel.readInt();
            String diagnosis = parcel.readString();
            String mkb = parcel.readString();
            String createdAt = parcel.readString();
            return new MedicalTreatments(id, diagnosis, mkb, createdAt);
        }

        @Override
        public MedicalTreatments[] newArray(int i) {
            return new MedicalTreatments[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(diagnosis);
        parcel.writeString(mkb);
        parcel.writeString(getUpdatedAt());
    }
}
