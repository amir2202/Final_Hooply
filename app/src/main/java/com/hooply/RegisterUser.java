package com.hooply;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUser extends AppCompatActivity {

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        EditText userIdText = (EditText) findViewById(R.id.userId);
        userIdText.setText("User ID: " + userName);
        userIdText.setFocusable(false);
        configureBackButton();
    }

    private void configureBackButton(){
        Button backButton = (Button) findViewById(R.id.goBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText firstNameText = (EditText) findViewById(R.id.firstName);
                String firstName = firstNameText.getText().toString();
                EditText lastNameText = (EditText) findViewById(R.id.lastName);
                String lastName = lastNameText.getText().toString();

                storeName(firstName, lastName);
            }
        });
    }

    public void storeName(String firstName, String lastName){
        final Object lock = new Object();
        final boolean[] ready = {false};
        Log.d("NAMES:", firstName + " " + lastName);
        if (!lastName.equals("") && !firstName.equals("") && !MainActivity.userExists(userName)){
            MainActivity.instance.storeUsername(userName);
            final User user = new User(userName,firstName+" "+ lastName,"2");
            String teststring = "false";
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (lock){
                        MainActivity.db.myDao().addUser(user);
                        ready[0] = true;
                        lock.notify();
                    }
                }
            });
            thread.start();
            try{
                synchronized (lock){
                    while (ready[0] == false){
                        lock.wait();
                    }
                }
            } catch(InterruptedException e){

            }
            if(MainActivity.userExists(userName) == true){
                teststring = "true";
            }
            Log.d("wwwwww", teststring);
            finish();
            //TODO: store NAME TO DATABASE
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Some fields are empty!")
                    .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    }).show();
        }

    }

}