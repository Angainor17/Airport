package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etLogin, etPassword, etPassport, etName;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etLogin = (EditText) findViewById(R.id.editTextRegLogin);
        etName = (EditText) findViewById(R.id.editTextRegName);
        etPassport = (EditText) findViewById(R.id.editTextRegPassport);
        etPassword = (EditText) findViewById(R.id.editTextRegPassword);

        btnRegister = (Button) findViewById(R.id.buttonRegRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonRegRegister:{
                if(etName.getText().length() > 0 &&
                        etPassport.getText().length() > 0 &&
                        etPassword.getText().length() > 0 &&
                        etLogin.getText().length() > 0){

                    ContentValues cv = new ContentValues();

                    cv.put(AirportDb.CLIENT_COLUMN_LOGIN, etLogin.getText().toString());
                    cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, etPassword.getText().toString());
                    cv.put(AirportDb.CLIENT_COLUMN_PASSPORT, etPassport.getText().toString());
                    cv.put(AirportDb.CLIENT_COLUMN_NAME, etName.getText().toString());

                    long newId = AirportDbHelper.Insert(this, AirportDb.TABLENAME_CLIENT, cv);

                    Intent intent = new Intent(RegistrationActivity.this,ClientMenuActivity.class);
                    intent.putExtra("id", newId);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.registration_input_erorr,Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
