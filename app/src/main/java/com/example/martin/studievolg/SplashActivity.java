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

        if (!loggedin.equals("Correct")) {
            requestSubjects();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loggedin.equals("Correct")) {
                    Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }

    private void requestSubjects(){
        Type type = new TypeToken<List<Course>>(){}.getType();

        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) {
                    String test = response.toString();
                    Log.d("Succesvol opgehaald: ", test);
                    processRequestSucces(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                processRequestError(error);
                Log.d("Failed!", "helaas!");
            }
        });
        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    private void processRequestSucces(List<Course> subjects ){

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        List<String> listedCourseArray = new ArrayList<String>();

        /* FETCHER FOR ALL ITEMS */
        // Set the cursor (items fetcher)
        Cursor rsCourse = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, null, null, null, null, null);

        // Get the amount of return
        String array[] = new String[rsCourse.getCount()];
        int j = 0;

        rsCourse.moveToFirst();

        // For all the items we get in the return
        while (!rsCourse.isAfterLast()) {
            String name = rsCourse.getString(rsCourse.getColumnIndex("name"));
            listedCourseArray.add(name);
            j++;
            rsCourse.moveToNext();
        }

        rsCourse.moveToFirst();
        /* END FETCHER FOR ALL ITEMS */

        // iterate via "for loop"
        for (int i = 0; i < subjects.size(); i++) {
            //DE NAAM, ECTS, CIJFER EN DE PERIODE  PER COURSE UIT DE DATABASE OPHALEN
            String name = String.valueOf(subjects.get(i).getName());
            String ects = String.valueOf(subjects.get(i).getEcts());
            String grade = "default";
            String period = String.valueOf(subjects.get(i).getPeriod());


            // Now check if this id already exists in the array
            if (listedCourseArray.contains(name)) {
                // true
            } else {

                // Set values to insert into the database
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.CourseColumn.NAME, name);
                values.put(DatabaseInfo.CourseColumn.PERIOD, period);
                values.put(DatabaseInfo.CourseColumn.GRADE, grade);
                values.put(DatabaseInfo.CourseColumn.ECTS, ects);

                // The insert itself by the dbhelper
                dbHelper.insert(DatabaseInfo.CourseTables.COURSETABLE, null, values);
            }
        }
        Toast.makeText(getApplicationContext(), "Loaded Courses into database", Toast.LENGTH_SHORT).show();

    }

    private String pullSharedPref() {
        SharedPreferences sharedPref = getSharedPreferences("login", MODE_PRIVATE);

        String key = "login";
        String loggedin = sharedPref.getString("login", key);
        Log.d("login: ", key);
        return loggedin;
    }

    private void processRequestError(VolleyError error){
        Log.d("cant pull json: ", "check je code");
    }




}