package com.example.martin.studievolg.List;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.example.martin.studievolg.EditCijfer;
import com.example.martin.studievolg.FirstActivity;
import com.example.martin.studievolg.Models.Course;
import com.example.martin.studievolg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 26-1-2017.
 */

public class ListActivity extends AppCompatActivity {

    private List<Course> CourseLijst = new ArrayList<>();
    private ListView CourseListView;
    private CourseListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        CourseListView = (ListView) findViewById(R.id.list_view1);
        CourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value = CourseLijst.get(position).getName();
                FragmentManager manager = getFragmentManager();
                EditCijfer fragment = new EditCijfer();
                Bundle args = new Bundle();
                args.putString("name", value);
                fragment.setArguments(args);
                fragment.show(manager, "dialog");
            }
        }
        );

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());
        dbHelper.open();
        Cursor rsCourse = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, null, null, null, null,  DatabaseInfo.CourseColumn.PERIOD + " DESC");
        String array[] = new String[rsCourse.getCount()];
        int i = 0;
        rsCourse.moveToFirst();
        while (!rsCourse.isAfterLast()) {
            String name = rsCourse.getString(rsCourse.getColumnIndex("name"));
            String ects = rsCourse.getString(rsCourse.getColumnIndex("ects"));
            String grade = rsCourse.getString(rsCourse.getColumnIndex("grade"));
            String period = rsCourse.getString(rsCourse.getColumnIndex("period"));

            CourseLijst.add(new Course(name, ects, grade, period));
            array[i] = rsCourse.getString(0);
            i++;
            rsCourse.moveToNext();
        }

        mAdapter = new CourseListAdapter(ListActivity.this, 0, CourseLijst);
        CourseListView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FirstActivity.class));
        finish();
    }
}
