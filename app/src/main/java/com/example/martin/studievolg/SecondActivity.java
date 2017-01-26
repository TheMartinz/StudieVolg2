package com.example.martin.studievolg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dbHelper = DatabaseHelper.getHelper(this);

        Button req = (Button) findViewById(R.id.buttonReq);
        Button sub = (Button) findViewById(R.id.buttonSub);

        //TODO listview van alle gegevens uit de Json.
        //deze gegevens moeten geupdate kunnen worden door de firstactivity

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSubjects();
            }
        });
        //TODO dit moet het opslaan

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCourseFromDB();
            }
        });
        //TODO de gegevens update?
    }

    private void requestSubjects(){
        Type type = new TypeToken<List<Course>>(){}.getType();

        GsonRequest<List<Course>> request = new GsonRequest<>("http://www.mverhaagen.nl/subject_lijst.json",
                type, null, new Response.Listener<List<Course>>() {
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

        // putting all received classes in my database.
        for (Course cm : subjects) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseInfo.CourseColumn.NAME, cm.getModulecode());
            cv.put(DatabaseInfo.CourseColumn.ECTS, cm.getEcts());
            cv.put(DatabaseInfo.CourseColumn.GRADE, cm.getCijfer());
            cv.put(DatabaseInfo.CourseColumn.PERIOD , cm.getPeriode());
            dbHelper.insert(DatabaseInfo.CourseTables.COURSETABLE, null, cv);
        }
        Toast.makeText(getApplicationContext(), "Added to database", Toast.LENGTH_SHORT).show();

    }

    private Course getCourseFromDB () {

        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSETABLE, new String[]{"*"}, null, null, null, null, null);

        // Haalt de name uit de resultset
        String modulecode = (String) rs.getString(rs.getColumnIndex("modulecode"));
        String ects = (String) rs.getString(rs.getColumnIndex("ects"));
        String cijfer = (String) rs.getString(rs.getColumnIndex("cijfer"));
        String periode = (String) rs.getString(rs.getColumnIndex("periode"));

        /*rs.moveToFirst(); */  // kan leeg zijn en faalt dan
        DatabaseUtils.dumpCursor(rs);

        Context context = getApplicationContext();
        CharSequence text = "Uit de DB komt : " + modulecode + " " + ects + " " + cijfer;       // Vraag de ects op
        int duration = Toast.LENGTH_SHORT;

        Toast.makeText(context, text, duration).show();

        return new Course(modulecode, ects, cijfer, periode);
    }

    private void processRequestError(VolleyError error){
        // WAT ZULLEN WE HIERMEE DOEN ?? - niets..
    }




}
