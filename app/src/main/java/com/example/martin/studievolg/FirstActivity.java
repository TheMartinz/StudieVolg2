package com.example.martin.studievolg;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.example.martin.studievolg.List.ListActivity;
import com.example.martin.studievolg.Models.Course;

import java.util.ArrayList;
import java.util.List;

import static com.example.martin.studievolg.R.id.editText;

public class FirstActivity extends AppCompatActivity {

    Course course1;
    private DatabaseHelper dbHelper;
    private ListView mListView;
    private List<Course> courseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        DatabaseHelper dbhelper = DatabaseHelper.getHelper(getApplicationContext());
        int count = 0;

        Button modules = (Button) findViewById(R.id.button3);
        modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        TextView gebruikersnaam = (TextView) findViewById(R.id.textView2);

        SharedPreferences sharedpref = getSharedPreferences("username", Context.MODE_PRIVATE);
        String name = sharedpref.getString("username", "");
        gebruikersnaam.setText("hallo " + name);


        TextView ectsTotaal = (TextView) findViewById(R.id.textView3);
        Cursor getCourse = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, "grade>=?", new String[]{"5.5"}, null, null, null);
        getCourse.moveToFirst();
        while (!getCourse.isAfterLast()) {
            int ectsAlles = getCourse.getInt(getCourse.getColumnIndex("ects"));
            count = count + ectsAlles;
            getCourse.moveToNext();
        }


        Cursor getWaarde = dbhelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, "grade=?", new String[]{"default"}, null, null, null);
        getWaarde.moveToFirst();
        while (!getWaarde.isAfterLast()) {
            int ectsWaarde = getWaarde.getInt(getWaarde.getColumnIndex("ects"));
            count = count + ectsWaarde;
            getWaarde.moveToNext();
        }

        ectsTotaal.setText("Je hebt " + String.valueOf(count + " ECTS"));



    }



}
