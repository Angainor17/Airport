package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;

public class ClientEditActivity extends AppCompatActivity {

    EditText etName, etLogin, etPassword, etPassport;

    int idElement = 0;
    ContentValues client;

    Button btnEditClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AirportDb ad = new AirportDb(getApplicationContext());
        SQLiteDatabase db = ad.getWritableDatabase();

        idElement = getIntent().getIntExtra("id",0);
        client = AirportDbHelper.getClientById(this,idElement);

        setContentView(R.layout.activity_client__edit_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etLogin = (EditText)findViewById(R.id.editTextClientEditLogin);
        etName = (EditText)findViewById(R.id.editTextClientEditName);
        etPassport = (EditText)findViewById(R.id.editTextClientEditPassport);
        etPassword = (EditText)findViewById(R.id.editTextClientEditPassword);

        btnEditClient = (Button) findViewById(R.id.btnEditClient);
        btnEditClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClientChanged()){
                    client.put(AirportDb.CLIENT_COLUMN_ID, idElement);
                    client.put(AirportDb.CLIENT_COLUMN_PASSPORT, etPassport.getText().toString());
                    client.put(AirportDb.CLIENT_COLUMN_PASSWORD, etPassword.getText().toString());
                    client.put(AirportDb.CLIENT_COLUMN_LOGIN, etLogin.getText().toString());
                    client.put(AirportDb.CLIENT_COLUMN_NAME, etName.getText().toString());

                    AirportDbHelper.Edit(getApplicationContext(), AirportDb.TABLENAME_CLIENT,idElement,client);

                    Toast.makeText(getApplicationContext(),R.string.element_was_updated,Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent();
                    intent.putExtra("id",client.getAsInteger("id"));
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),R.string.no_changes,Toast.LENGTH_SHORT).show();
                }
            }
        });

        setClientData();
    }

    private void setClientData(){
        etName.setText(client.getAsString(AirportDb.CLIENT_COLUMN_NAME));
        etPassport.setText(client.getAsString(AirportDb.CLIENT_COLUMN_PASSPORT));
        etPassword.setText(client.getAsString(AirportDb.CLIENT_COLUMN_PASSWORD));
        etLogin.setText(client.getAsString(AirportDb.CLIENT_COLUMN_LOGIN));
    }

    private boolean isClientChanged(){
        if(!etName.getText().toString().equals(client.getAsString(AirportDb.CLIENT_COLUMN_NAME))) return true;
        if(!etPassport.getText().toString().equals(client.getAsString(AirportDb.CLIENT_COLUMN_PASSPORT))) return true;
        if(!etPassword.getText().toString().equals(client.getAsString(AirportDb.CLIENT_COLUMN_PASSWORD))) return true;
        if(!etLogin.getText().toString().equals(client.getAsString(AirportDb.CLIENT_COLUMN_LOGIN))) return true;

        return  false;
    }

}
