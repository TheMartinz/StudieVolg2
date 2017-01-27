package com.example.martin.studievolg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = getIntent().getStringExtra("SIGNED_IN");
        Log.d(TAG, s);


        Button page_1 = (Button) findViewById(R.id.button1);
        final EditText edittextnaam = (EditText) findViewById(R.id.editText5);


        page_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences SPlogin = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor loginSPedit = SPlogin.edit();
                loginSPedit.putString("login", "Correct");
                loginSPedit.commit();

                String username = edittextnaam.getText().toString();
                SharedPreferences SPusername = getSharedPreferences("username", MODE_PRIVATE);
                SharedPreferences.Editor usernameSPedit = SPusername.edit();
                usernameSPedit.putString("username", username);
                usernameSPedit.commit();

                Toast.makeText(getApplicationContext(), getString(R.string.hallo) + username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                finish();

            }
        });
        
    }

}