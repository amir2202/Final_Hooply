package com.hooply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

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

                if(firstName != "" && lastName != ""){
                    storeName(firstName, lastName);
                    finish();
                }
            }
        });
    }

    public void storeName(String firstName, String lastName){
        Log.d("NAMES:", firstName + " " + lastName);
        //TODO: store NAMES TO DATABASE
    }

}