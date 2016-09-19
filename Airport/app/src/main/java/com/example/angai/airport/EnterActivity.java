package com.example.angai.airport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EnterActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonRegistration, buttonAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonAuthorization = (Button) findViewById(R.id.buttonAuthorization);
        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);

        buttonRegistration.setOnClickListener(this);
        buttonAuthorization.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.buttonAuthorization:{
                intent = new Intent(EnterActivity.this, AuthorizationActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.buttonRegistration:{
                intent = new Intent(EnterActivity.this, RegistrationActivity.class);
                startActivity(intent);
                break;
            }
        }

    }
}
