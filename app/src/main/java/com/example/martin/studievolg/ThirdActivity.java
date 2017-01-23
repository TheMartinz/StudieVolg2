package com.example.martin.studievolg;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.example.martin.studievolg.Gson.RecyclerViewAdapter;
import com.example.martin.studievolg.Models.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private final String TAG = "ThirdActivity";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private DatabaseHelper dbHelper;
    List<Course> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(ThirdActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        requestJsonObject();

    }

    private void requestJsonObject(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.mverhaagen.nl/subject_lijst.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<Course> posts = new ArrayList<Course>();
                posts = Arrays.asList(mGson.fromJson(response, Course[].class));
                adapter = new RecyclerViewAdapter(ThirdActivity.this, posts);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

/*    public boolean zetDeCourseInDeDatabase() {
        dbHelper = DatabaseHelper.getHelper(this);

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.MODULECODE, posts.getModulecode());
        values.put(DatabaseInfo.CourseColumn.ECTS, course1.getEcts());
        values.put(DatabaseInfo.CourseColumn.CIJFER, course1.getCijfer());

        // INSERT dit values object in DE (ZELFGEMAAKTE) RIJ COURSE,
        dbHelper.insert(DatabaseInfo.CourseTables.COURSETABLE, null, values);
        return true;

    }*/
}
