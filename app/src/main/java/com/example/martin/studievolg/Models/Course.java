package com.example.martin.studievolg.Models;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Martin on 20-1-2017.
 */

public class Course {

    private String modulecode;
    private String ects;
    private String cijfer;
    private String periode;

    public Course (String m, String e, String k, String p) {
        this.modulecode= m;
        this.ects= e;
        this.cijfer = k;
        this.periode = p;
    }

    public void setModulecode(String m){
        this.modulecode = m;
    }

    public void setEcts (String e) {
        this.ects = e;
    }

    public void setCijfer(String k){
        this.cijfer = k;
    }

    public void setPeriode(String p) { this.periode = p;}

    public String getModulecode(){
        return modulecode;
    }

    public String getEcts(){
        return ects;
    }

    public String getCijfer(){
        return cijfer;
    }

    public String getPeriode() { return periode;}

    @Override
    public String toString() {
        return "CourseTable [modulecode=" + modulecode + ", ects=" + ects + ", cijfer=" + cijfer+ ", periode=" + periode + "]";
    }

}