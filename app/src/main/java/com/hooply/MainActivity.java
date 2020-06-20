package com.hooply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Log.d("register", email);
    }

}