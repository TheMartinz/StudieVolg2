package com.example.martin.studievolg;

import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.example.martin.studievolg.Gson.GsonRequest;
import com.example.martin.studievolg.Gson.VolleyHelper;
import com.example.martin.studievolg.Models.Course;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    public static String TAG = "SecondActivity";
    public static int behaaldeEcts = 0;
    private PieChart pie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        pie = (PieChart) findViewById(R.id.idpiechart);
        pie.setRotationEnabled(true);
        pie.setHoleRadius(25f);
        pie.setCenterText("ECTS");
        pie.setCenterTextSize(10);
        pie.setDrawEntryLabels(true);
        pie.setTransparentCircleAlpha(0);


        final DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());

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

        setData(count);


    }

    private void setData(int ects) {
        behaaldeEcts = ects;

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (behaaldeEcts <10) {
        colors.add(Color.rgb(244,81,30));
        }
        else if (behaaldeEcts < 40){
        colors.add(Color.rgb(235,0,0));
        }
        else if  (behaaldeEcts < 50) {
            colors.add(Color.rgb(253,216,53));
        }
        else {
            colors.add(Color.rgb(67,160,71));
        }
        colors.add(Color.rgb(255,0,0));



        List<PieEntry> waardes = new ArrayList<>();
        waardes.add(new PieEntry(ects, getString(R.string.verkregenEcts)));
        waardes.add(new PieEntry(60 - behaaldeEcts, getString(R.string.chartNogtehalen)));

        PieDataSet set = new PieDataSet(waardes, "");
        PieData data = new PieData(set);
        set.setColors(colors);
        set.setSliceSpace(2);
        pie.setData(data);
        pie.invalidate();



    }


}
