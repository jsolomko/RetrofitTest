package com.example.mytestretrofit.converter;

import androidx.room.TypeConverter;

import com.example.mytestretrofit.models.MedicalTreatments;

import java.util.List;
import java.util.stream.Collectors;

public class PatientConverter {
    @TypeConverter
    public String fromPatients(List<MedicalTreatments> medicalTreatments) {
        return "";
    }

}
