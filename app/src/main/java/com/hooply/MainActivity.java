package com.hooply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Delete;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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

    public void login(String username) {
        boolean wrongUsername = !userExists(username);       //USE FOR CHECKING IF THE USERNAME IS CORRECT

        if(wrongUsername || !username.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Wrong Username")
                    .setNegativeButton("Exit app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setPositiveButton("Retry", null).show();
        } else {
            storeUsername(username);
        }
    }

    public void register(String username) {
        if(!username.equals("")){
            Intent i = new  Intent(MainActivity.this, RegisterUser.class);
            i.putExtra("UserName",username);
            Log.d("MEN",username);
            startActivity(i);
        }
    }

    public void storeUsername(String uid) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("uid", uid);
        editor.apply();
    }


    public synchronized boolean userExists(String userid) {
        //TODO: DOES NOT WORK MEN / need to run the actual thread i think
        final String id = userid;
        final boolean[] found = {false};
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
                found[0] = true;
            }
        });
        thread.start();
            while (found[0] == false) {
                try{
                    this.wait();
                } catch(InterruptedException e){

                }
            }
        return empty[0];
    }

    public void synchDb() throws IOException {
        URL restDb = null;
        try {
            restDb = new URL("http://caracal.imada.sdu.dk/app2020/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create connection
        HttpsURLConnection myConnection = (HttpsURLConnection) restDb.openConnection();

        myConnection.setRequestProperty("User-Agent", "my-restdb-app");
        myConnection.setRequestProperty("Accept", "application/json");
        myConnection.setRequestProperty("x-apikey", "560bd47058e7ab1b2648f4e7");

        if (myConnection.getResponseCode() == 200) {

        } else {

        }
    }

    public void synchUsers() throws IOException {
        URL url = null;
        try {
            url = new URL("http://example.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try {
            con.setRequestMethod("GET");
            String test = con.getResponseMessage();
            Log.d("mennn",test);

        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }
    public void synchPosts(){

    }


}