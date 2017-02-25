package com.example.martin.studievolg;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());
        dbHelper.open();

        Button modules = (Button) findViewById(R.id.button3);
        modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        Button weergave = (Button) findViewById(R.id.button2);
        weergave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button weergave2 = (Button) findViewById(R.id.weergave2);
        weergave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, BarActivity.class);
                startActivity(intent);
            }
        });

        TextView gebruikersnaam = (TextView) findViewById(R.id.textView2);

        SharedPreferences sharedpref = getSharedPreferences("username", Context.MODE_PRIVATE);
        String name = sharedpref.getString("username", "");
        gebruikersnaam.setText("Hallo " + name);


        TextView ectsTotaal = (TextView) findViewById(R.id.textView3);
        Cursor getCourse = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, "grade>=?", new String[]{"5.5"}, null, null, null);

        int count = 0;
        getCourse.moveToFirst();
        while (!getCourse.isAfterLast()) {
            int ectsAlles = getCourse.getInt(getCourse.getColumnIndex("ects"));
            count = count + ectsAlles;
            getCourse.moveToNext();
        }

        Cursor rsCourseMax = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, "grade=?", new String[]{"10"}, null, null, null);

        rsCourseMax.moveToFirst();

        while(!rsCourseMax.isAfterLast()) {
            int ects = rsCourseMax.getInt(rsCourseMax.getColumnIndex("ects"));
            count = count + ects;
            rsCourseMax.moveToNext();
        }


        Cursor getWaarde = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, "grade=?", new String[]{"default"}, null, null, null);
        getWaarde.moveToFirst();
        while (!getWaarde.isAfterLast()) {
            int ectsWaarde = getWaarde.getInt(rsCourseMax.getColumnIndex("ects"));
            count = count - ectsWaarde;
            getWaarde.moveToNext();
        }

        Cursor getWaardeO = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, "grade=?", new String[]{"O"}, null, null, null);
        getWaardeO.moveToFirst();
        while (!getWaardeO.isAfterLast()) {
            int ectsWaardeO = getWaardeO.getInt(rsCourseMax.getColumnIndex("ects"));
            count = count - ectsWaardeO;
            getWaardeO.moveToNext();
        }

        assert ectsTotaal != null;
        ectsTotaal.setText("Je hebt " + String.valueOf(count + " ECTS"));



    }





}
