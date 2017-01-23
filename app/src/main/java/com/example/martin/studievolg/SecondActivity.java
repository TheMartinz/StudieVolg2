package com.example.martin.studievolg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.martin.studievolg.Gson.RecyclerViewAdapter;
import com.example.martin.studievolg.List.CourseListAdapter;
import com.example.martin.studievolg.Models.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {


    private ListView mListView;
    private CourseListAdapter mAdapter;
    private List<Course> courseModels = new ArrayList<>();
    // WE MAY NEED A METHOD TO FILL THIS. WE COULD RETRIEVE THE DATA FROM AN ONLINE JSON SOURCE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        fillList();
    }

    private void fillList() {
        mListView = (ListView) findViewById(R.id.ListView1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast t = Toast.makeText(SecondActivity.this,"Click" + position,Toast.LENGTH_LONG);
                t.show();
            }
        }
        );

        courseModels.add(new Course("IKPMD", "3", "10", "4"));             // DUMMY DATA

        mAdapter = new CourseListAdapter(SecondActivity.this, 0, courseModels);
        mListView.setAdapter(mAdapter);
    }


}
