package com.example.martin.studievolg.Models;

/**
 * Created by Martin on 20-1-2017.
 */

public class Course {

    private String modulecode;
    private String ects;
    private String cijfer;

    public Course (String m, String p, String k) {
        this.modulecode= m;
        this.ects= p;
        this.cijfer = k;
    }

    public void setModulecode(String m){
        this.modulecode = m;
    }

    public void setEcts (String k) {
        this.ects = k;
    }

    public void setCijfer(String p){
        this.cijfer = p;
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