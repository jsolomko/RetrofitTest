package com.example.mytestretrofit.models;


import java.util.List;

public class MedicalTreatments {
    public int id;
    public int attending_doctor_id;
    public String diagnosis;
    public String mkb;
    public String createdAt;
    public String updatedAt;
    public List<Action>  actions;
    public Patients patient;
    public Affiliation affiliation;

    public MedicalTreatments(int id, int attending_doctor_id, String diagnosis, String mkb, String createdAt, String updatedAt, List<Action>  actions, Patients patient, Affiliation affiliation) {
        this.id = id;
        this.attending_doctor_id = attending_doctor_id;
        this.diagnosis = diagnosis;
        this.mkb = mkb;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.actions = actions;
        this.patient = patient;
        this.affiliation = affiliation;
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

    public List<Action>  getActions() {
        return actions;
    }

    public void setActions(List<Action>  actions) {
        this.actions = actions;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
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
}
