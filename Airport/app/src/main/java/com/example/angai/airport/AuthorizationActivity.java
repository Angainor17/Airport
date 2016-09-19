package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angai.airport.DataBase.AirportDatabase;
import com.example.angai.airport.DataBase.AirportDatabaseHelper;
import com.example.angai.airport.Root.RootMenuActivity;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonLogIn;

    EditText etLogin, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonLogIn =(Button)findViewById(R.id.button_aut_login);
        buttonLogIn.setOnClickListener(this);

        etLogin = (EditText) findViewById(R.id.editTextLogin);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_aut_login:{
                if(etLogin.getText().length()==0 || etPassword.getText().length()==0){
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.registration_input_erorr,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    return;
                }

                /////////////////////////////ROOT/////////////////////////////////////////////////
                if(etLogin.getText().toString().equals("root") && etPassword.getText().toString().equals("root")){
                    Intent intent = new Intent(AuthorizationActivity.this,RootMenuActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                AirportDatabase airportDatabase = new AirportDatabase(AuthorizationActivity.this);
                SQLiteDatabase db = airportDatabase.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put(AirportDatabase.CLIENT_COLUMN_LOGIN, etLogin.getText().toString());
                cv.put(AirportDatabase.CLIENT_COLUMN_PASSWORD, etPassword.getText().toString());

                if(AirportDatabaseHelper.Consist(db,AirportDatabase.TABLENAME_CLIENT,cv)){
                    Intent intent = new Intent(AuthorizationActivity.this,ClientMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.wrong_pas_log,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                break;
            }
        }
    }
}
