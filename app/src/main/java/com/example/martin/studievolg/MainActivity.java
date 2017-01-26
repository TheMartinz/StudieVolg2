package com.example.martin.studievolg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button page_1 = (Button) findViewById(R.id.button1);
        final EditText edittextnaam = (EditText) findViewById(R.id.editText5);


        page_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefLogin();
                String username = edittextnaam.getText().toString();
                SharedPreferences SPusername = getSharedPreferences("username", MODE_PRIVATE);
                SharedPreferences.Editor usernameSPedit = SPusername.edit();
                usernameSPedit.putString("username", username);
                usernameSPedit.commit();

                Toast.makeText(getApplicationContext(), "hallo " + username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);

            }
        });
        
    }

    private void prefLogin() {
        SharedPreferences SPlogin = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor loginSPedit = SPlogin.edit();
        loginSPedit.putString("login", "Correct");
        loginSPedit.commit();
    }
}