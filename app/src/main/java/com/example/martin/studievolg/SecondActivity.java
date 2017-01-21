package com.example.martin.studievolg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.martin.studievolg.List.CourseListAdapter;
import com.example.martin.studievolg.Models.Course;

import java.util.ArrayList;
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

        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast t = Toast.makeText(SecondActivity.this,"Click" + position,Toast.LENGTH_LONG);
                t.show();
            }
        }
        );

        courseModels.add(new Course("IKPMD", "3", "10"));             // DUMMY DATA
        courseModels.add(new Course("IOPR1", "4", "9"));             // DUMMY DATA
        courseModels.add(new Course("IPSEN", "6", "8"));             // DUMMY DATA

        mAdapter = new CourseListAdapter(SecondActivity.this, 0, courseModels);
        mListView.setAdapter(mAdapter);
    }

}
