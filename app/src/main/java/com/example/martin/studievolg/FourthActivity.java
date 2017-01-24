package com.example.martin.studievolg;

import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.martin.studievolg.Database.DatabaseHelper;

public class FourthActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        //TODO graphisch iets van de gemiddeld cijfer/ects per periode/nog iets....

    }
}
