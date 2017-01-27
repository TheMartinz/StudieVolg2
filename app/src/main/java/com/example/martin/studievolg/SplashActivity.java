package com.example.martin.studievolg;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.example.martin.studievolg.Gson.GsonRequest;
import com.example.martin.studievolg.Gson.VolleyHelper;
import com.example.martin.studievolg.Models.Course;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;

/**
 * Created by Martin on 26-1-2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final String loggedin = pullSharedPref();

        if (!loggedin.equals(getString(R.string.loggedinText))) {
            requestSubjects();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loggedin.equals(getString(R.string.loggedinText))) {
                    Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                    intent.putExtra("SIGNED_IN", "Yes");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("SIGNED_IN", "No");
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }

    private void requestSubjects(){
        Type type = new TypeToken<List<Course>>(){}.getType();

        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                getString(R.string.jSon), type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) {
                    String test = response.toString();
                    Log.d(getString(R.string.downloadOk), test);
                    processRequestSucces(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                processRequestError(error);
                Log.d(getString(R.string.downloadFailed), "helaas!");
            }
        });
        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    private void processRequestSucces(List<Course> subjects ){

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        List<String> listedCourseArray = new ArrayList<String>();
        Cursor rsCourse = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, null, null, null, null, null);
        String array[] = new String[rsCourse.getCount()];
        int j = 0;
        rsCourse.moveToFirst();
        while (!rsCourse.isAfterLast()) {
            String name = rsCourse.getString(rsCourse.getColumnIndex("name"));
            listedCourseArray.add(name);
            j++;
            rsCourse.moveToNext();
        }

        rsCourse.moveToFirst();
        for (int i = 0; i < subjects.size(); i++) {
            String name = String.valueOf(subjects.get(i).getName());
            String ects = String.valueOf(subjects.get(i).getEcts());
            String grade = getString(R.string.cijferStandaard);
            String period = String.valueOf(subjects.get(i).getPeriod());

            if (listedCourseArray.contains(name)) {

            } else {
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.CourseColumn.NAME, name);
                values.put(DatabaseInfo.CourseColumn.PERIOD, period);
                values.put(DatabaseInfo.CourseColumn.GRADE, grade);
                values.put(DatabaseInfo.CourseColumn.ECTS, ects);
                dbHelper.insert(DatabaseInfo.CourseTables.COURSETABLE, null, values);
            }
        }
        Toast.makeText(getApplicationContext(), R.string.courseLoaded, Toast.LENGTH_SHORT).show();

    }

    private String pullSharedPref() {
        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);

        String key = "login";
        String loggedin = sharedPref.getString("login", key);
        Log.d("login: ", key);
        return loggedin;
    }

    private void processRequestError(VolleyError error){
        Log.d(getString(R.string.jsonError), "check je code");
    }




}