package com.example.angai.airport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.angai.airport.DataBase.AirportDatabase;

public class ClientMenuActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn = (Button) findViewById(R.id.buttonClientMenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirportDatabase airportDatabase = new AirportDatabase(ClientMenuActivity.this);
                SQLiteDatabase db =  airportDatabase.getWritableDatabase();

                Cursor c = db.query(AirportDatabase.TABLENAME_CLIENT,null,null,null,null,null,null);
                Log.d("database1", "Count: " + c.getCount());

                while(c.moveToNext()){
                    Log.d("database1",c.getString(c.getColumnIndex(c.getColumnNames()[AirportDatabase.CLIENT_COLUMN_LOGIN_INT])));
                }

                db.close();
                airportDatabase.close();


            }
        });

    }

}
