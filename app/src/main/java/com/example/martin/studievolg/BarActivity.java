package com.example.martin.studievolg;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.martin.studievolg.Database.DatabaseHelper;
import com.example.martin.studievolg.Database.DatabaseInfo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 25-2-2017.
 */

public class BarActivity extends AppCompatActivity {

    private final String TAG = "BarActivity";
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_activity);

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

        barChart = (BarChart) findViewById(R.id.bargraph);


        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(0f,count, "voldoende"));
        barEntries.add(new BarEntry(1f,60-count, "onvoldoende"));
        BarDataSet set = new BarDataSet(barEntries, "cijfers");

        BarData data = new BarData(set);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
}
