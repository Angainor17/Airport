package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angai.airport.DataBase.AirportDatabase;

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

                    AirportDatabase airportDatabase = new AirportDatabase(this);
                    SQLiteDatabase db = airportDatabase.getWritableDatabase();
                    ContentValues cv = new ContentValues();

                   // cv.put("id",0);
                    cv.put(AirportDatabase.CLIENT_COLUMN_LOGIN, etLogin.getText().toString());
                    cv.put(AirportDatabase.CLIENT_COLUMN_PASSWORD, etPassword.getText().toString());
                    cv.put(AirportDatabase.CLIENT_COLUMN_PASSPORT, etPassport.getText().toString());
                    cv.put(AirportDatabase.CLIENT_COLUMN_NAME, etName.getText().toString());

                //    db.beginTransaction();

                     db.insert(AirportDatabase.TABLENAME_CLIENT, null, cv);
          //          db.setTransactionSuccessful();
                    //db.endTransaction();
                    db.close();
                    airportDatabase.close();

                    Intent intent = new Intent(RegistrationActivity.this,ClientMenuActivity.class);
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
