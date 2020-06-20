package com.hooply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Delete;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HooplyDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(), HooplyDatabase.class, "hooply-database").build();
        setContentView(R.layout.activity_main);
        Button registerButton = (Button) findViewById(R.id.signUpButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailText = (EditText) findViewById(R.id.usernameText);
                String email = emailText.getText().toString();
                register(email);
                startActivity(new Intent(MainActivity.this, RegisterUser.class));
                TextView debug = (TextView) findViewById(R.id.debug);
                debug.setText("register:" + email);
            }
        });

        Button loginButton = (Button) findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailText = (EditText) findViewById(R.id.usernameText);
                String email = emailText.getText().toString();
                login(email);
                TextView debug = (TextView) findViewById(R.id.debug);
                debug.setText("login: " + email);
            }
        });

    }

    public void login(String email) {
        Log.d("login", email);
    }

    public void register(String email) {

    }

    public boolean userExists(String userid){
        final String id = userid;
        final boolean[] empty = {false};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> users = db.myDao().userIdExists(id);
                if(users.size() > 0){
                    empty[0] = true;
                }
                else{
                    empty[0] =  false;
                }
            }
        });
        return empty[0];
    }
}