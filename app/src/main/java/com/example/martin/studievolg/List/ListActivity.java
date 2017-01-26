package com.example.martin.studievolg.List;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.martin.studievolg.EditCijfer;
import com.example.martin.studievolg.Models.Course;
import com.example.martin.studievolg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 26-1-2017.
 */

public class ListActivity extends AppCompatActivity {

    private List<Course> CourseLijst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView CourseListView = (ListView) findViewById(R.id.list_view1);
        CourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Getting the course name
                String value = CourseLijst.get(position).getModulecode();

                FragmentManager manager = getFragmentManager();
                EditCijfer fragment = new EditCijfer();
                Bundle args = new Bundle();
                args.putString("name", value);
                fragment.setArguments(args);
                fragment.show(manager, "dialog");
            }
        }
        );

    }
}
