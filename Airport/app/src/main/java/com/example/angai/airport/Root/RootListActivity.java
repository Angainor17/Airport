package com.example.angai.airport.Root;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.angai.airport.DataBase.AirportDatabase;
import com.example.angai.airport.R;

public class RootListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listViewClient);

        AirportDatabase airportDatabase = new AirportDatabase(this);
        SQLiteDatabase db = airportDatabase.getWritableDatabase();

        ClientAdapter clientAdapter = new ClientAdapter(this,db);
        listView.setAdapter(clientAdapter);
    }

}
