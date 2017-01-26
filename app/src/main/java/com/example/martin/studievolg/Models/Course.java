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

    public String name;
    public String ects;
    public String grade;
    public String period;

    public Course (String m, String e, String k, String p) {
        this.name= m;
        this.ects= e;
        this.grade = k;
        this.period = p;
    }

    public void setName(String m){
        this.name = m;
    }

    public void setEcts (String e) {
        this.ects = e;
    }

    public void setGrade(String k){
        this.grade = k;
    }

    public void setPeriod(String p) { this.period = p;}

    public String getName(){
        return name;
    }

    public String getEcts(){
        return ects;
    }

    public String getGrade(){
        return grade;
    }

    public String getPeriod() { return period;}

    @Override
    public String toString() {
        return "CourseTable [modulecode=" + name + ", ects=" + ects + ", cijfer=" + grade+ ", periode=" + period + "]";
    }

}