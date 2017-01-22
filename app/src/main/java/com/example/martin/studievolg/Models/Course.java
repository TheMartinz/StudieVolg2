package com.example.martin.studievolg.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Martin on 20-1-2017.
 */

public class Course {

    @SerializedName("name")
    private String modulecode;
    @SerializedName("ects")
    private String ects;
    @SerializedName("grade")
    private String cijfer;

    public Course (String m, String e, String k) {
        this.modulecode= m;
        this.ects= e;
        this.cijfer = k;
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

    public String getEcts(){
        return this.ects;
    }

    public String getModulecode(){
        return this.modulecode;
    }

    public String getCijfer(){
        return this.cijfer;
    }

}