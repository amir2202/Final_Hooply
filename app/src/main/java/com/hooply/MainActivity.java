package com.hooply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    public static HooplyDatabase db;
    public static MainActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalVar global = new GlobalVar();
        instance = this;
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
        try {
            synchUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void login(String username) {
        boolean wrongUsername = !userExists(username);       //USE FOR CHECKING IF THE USERNAME IS CORRECT
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String storeduser = sharedPref.getString("uid","");
        Log.d("eee2",userExists(username)?"true":"false");
        Log.d("eee",storeduser);
        if(wrongUsername || username.equals("") || !storeduser.equals(username)) {
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
            Intent i = new  Intent(MainActivity.this, Posting.class);
            startActivity(i);
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
        GlobalVar.userid=uid;
    }



    public static boolean userExists(String userid) {
        final Object lock = new Object();

        final String id = userid;
        final boolean[] found = {false};
        final boolean[] empty = {false};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    List<User> users = db.myDao().userIdExists(id);
                    if(users.size() > 0){
                        empty[0] = true;
                    }
                    else{
                        empty[0] =  false;
                    }
                    found[0] = true;
                    lock.notify();
                }
            }
        });

        thread.start();

        try {
            synchronized(lock) {
                while(found[0] == false) {
                    lock.wait();
                }
                // return the result
                return empty[0];
            }
        } catch (InterruptedException e) {
            // maybe do smth for exception handling ? or just ignore lol
            return empty[0];
        }
    }
/*
    public void synchDb() throws IOException {
        URL restDb = null;
        try {
            restDb = new URL("https://caracal.imada.sdu.dk/app2020/");
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
*/
    public void synchUsers() throws IOException {
        final Object lock = new Object();

        final boolean[] found = {false};

        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    URL url = null;
                    try {
                        url = new URL("https://caracal.imada.sdu.dk/app2020/users");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        String test = con.getResponseMessage();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder out = new StringBuilder();
                        String line;


                        final int bufferSize = 1024;
                        final char[] buffer = new char[bufferSize];
                        Reader in = new InputStreamReader(con.getInputStream());
                        int charsRead;
                        while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
                            out.append(buffer, 0, charsRead);
                        }

                        List<User> stuff = Parser.parseUsers(out.toString(),db.myDao());
                        for(User user:stuff){
                            if(db.myDao().userIdExists(user.getId()).size() == 0) {
                                db.myDao().addUser(user);
                            }
                            else{
                                continue;
                            }
                        }
                        in.close();

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    found[0] = true;
                    lock.notify();
                }
            }
        });

        thread.start();

        try {
            synchronized(lock) {
                while(found[0] == false) {
                    lock.wait();
                }
                // add remote db data to local db ???
                return;
            }
        } catch (InterruptedException e) {
            // maybe do smth for exception handling ? or just ignore lol
            return;
        }

    }

}