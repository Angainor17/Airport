package com.example.angai.debt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    public static ArrayList<Debtor> DebtorList;

    Button ButtonAdd;
    static  ListView listView;
    static DebtorAdapter debtorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonAdd = (Button) findViewById(R.id.imageButtonAdd);

        listView = (ListView) findViewById(R.id.listView);

        DebtorList = new ArrayList<>();

        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, AddDebtor.class);
                startActivity(intent);
            }
        });



        debtorAdapter = new DebtorAdapter(this,DebtorList);
        listView.setAdapter(debtorAdapter);

    }

    public static void ShowListView(){
        listView.setAdapter(debtorAdapter);
    }
}
