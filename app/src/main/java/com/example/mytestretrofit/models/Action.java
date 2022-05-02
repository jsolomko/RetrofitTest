package com.example.mytestretrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;

public class Action implements Parcelable {
    public Integer id;
    public Integer medicaltreatment_id;
    public Integer actiontype_id;
    public String eye;
    public String pzo;
    public String appointments;
    public Integer surgeonId;
    public String features;
    public String createdAt;
    public String updatedAt;

    public Action(Integer actiontype_id, String eye, String pzo) {
        this.eye = eye;
        this.pzo = pzo;
        this.actiontype_id = actiontype_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicaltreatment_id() {
        return medicaltreatment_id;
    }

    public void setMedicaltreatment_id(Integer medicaltreatment_id) {
        this.medicaltreatment_id = medicaltreatment_id;
    }

    public Integer getActiontype_id() {
        return actiontype_id;
    }

    public void setActiontype_id(Integer actiontype_id) {
        this.actiontype_id = actiontype_id;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getPzo() {
        return pzo;
    }

    public void setPzo(String pzo) {
        this.pzo = pzo;
    }

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    public Integer getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(Integer surgeonId) {
        this.surgeonId = surgeonId;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
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


    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel parcel) {
            int id = parcel.readInt();
            String eye = parcel.readString();
            String pzo = parcel.readString();
            return new Action(1, eye, pzo);
        }

        @Override
        public Action[] newArray(int i) {
            return new Action[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
