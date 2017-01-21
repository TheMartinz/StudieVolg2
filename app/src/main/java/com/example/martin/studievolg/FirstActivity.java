package com.example.martin.studievolg;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.example.martin.studievolg.Models.Course;

import java.util.HashMap;
import java.util.Map;

import static com.example.martin.studievolg.R.id.editText;
import static com.example.martin.studievolg.R.id.textView;

public class FirstActivity extends AppCompatActivity {

    Course course1;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button buttonInput = (Button) findViewById(R.id.buttonInput);
        Button buttonOutput = (Button) findViewById(R.id.buttonOutput);

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputValues();
                getCourseFromDB();

            }
        });

    }

    public void getInputValues() {
        // Modulecode
        EditText etModulecode = (EditText) findViewById(editText);
        String modulecode = etModulecode.getText().toString();

        // ECTS
        EditText etEcts = (EditText) findViewById(R.id.editText2);
        String ects = etEcts.getText().toString();

        // Cijfer
        EditText etCijfer = (EditText) findViewById(R.id.editText3);
        String cijfer = etCijfer.getText().toString();

        // Vul het model Course met de informatie van de gebruiker
        course1 = new Course(modulecode, ects, cijfer);

/*
        Context context = getApplicationContext();
        CharSequence text = "Course model gemaakt op basis van user input";       // Vraag de ects op
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();                               // Print de kleur af
*/

        zetDeCourseInDeDatabase();
        Toast toast = Toast.makeText(getApplicationContext(), "MODEL ENTERED IN DB", Toast.LENGTH_SHORT);

        Course course1 = getCourseFromDB();

    }

    public boolean zetDeCourseInDeDatabase() {
        dbHelper = DatabaseHelper.getHelper(this);

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.MODULECODE, course1.getModulecode());
        values.put(DatabaseInfo.CourseColumn.CIJFER, course1.getCijfer());
        values.put(DatabaseInfo.CourseColumn.ECTS, course1.getEcts());

        // INSERT dit values object in DE (ZELFGEMAAKTE) RIJ COURSE,
        dbHelper.insert(DatabaseInfo.CourseTables.COURSETABLE, null, values);
        return true;

    }

    private Course getCourseFromDB() {
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, null, null, null, null, null);
        // * ??  ==> SELECT * FROM COURSETABLE;

        rs.moveToFirst();   // Skip : de lege elementen vooraan de rij.
        // Maar : de rij kan nog steeds leeg zijn // Hoe : lossen we dit op ??

        // Haalt de name uit de resultset
        String modulecode = (String) rs.getString(rs.getColumnIndex("modulecode"));
        String cijfer = (String) rs.getString(rs.getColumnIndex("cijfer"));
        String ects = (String) rs.getString(rs.getColumnIndex("ects"));

        // Even checken of dit goed binnen komt
        Context context = getApplicationContext();
        CharSequence text = "Uit de DB komt :"+ects;       // Vraag de ects op
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();                               // Print de ects af

        return new Course(modulecode, cijfer, ects);
    }

}
