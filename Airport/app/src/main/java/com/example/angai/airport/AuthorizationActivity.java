package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.Root.RootMenuActivity;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonLogIn, btnReg;
    TextView tvReg;
    EditText etLogin, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonLogIn =(Button)findViewById(R.id.button_aut_login);
        btnReg = (Button) findViewById(R.id.btnRegNoAccaunt);

        tvReg = (TextView)findViewById(R.id.tvRegNoAccount);

        btnReg.setOnClickListener(this);
        buttonLogIn.setOnClickListener(this);

        tvReg.setVisibility(View.INVISIBLE);
        btnReg.setVisibility(View.INVISIBLE);

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

                ContentValues cv = new ContentValues();

                cv.put(AirportDb.CLIENT_COLUMN_LOGIN, etLogin.getText().toString());
                cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, etPassword.getText().toString());

                if(AirportDbHelper.Consist(this, AirportDb.TABLENAME_CLIENT,cv)){
                    cv = AirportDbHelper.getClientByLogin(this, etLogin.getText().toString());
                    Intent intent = new Intent(AuthorizationActivity.this,ClientMenuActivity.class);
                    intent.putExtra("id",cv.getAsLong("id"));
                    startActivity(intent);
                    finish();
                }
                else{
                    tvReg.setVisibility(View.VISIBLE);
                    btnReg.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.wrong_pas_log,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                break;
            }
            case R.id.btnRegNoAccaunt:{
                Intent intent = new Intent(this,RegistrationActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}
