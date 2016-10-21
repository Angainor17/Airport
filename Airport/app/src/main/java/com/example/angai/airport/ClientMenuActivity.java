package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.angai.airport.ClientOrder.ClientOrderActivity;
import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.MakeOrder.MakeOrderActivity;

public class ClientMenuActivity extends AppCompatActivity implements View.OnClickListener{
    final  int ID_1 =1;
    public static ContentValues  ActualClient;

    Button btn;
    Button btnMyOrders;
    TextView tvActualClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn = (Button) findViewById(R.id.buttonBookTicket);
        btn.setOnClickListener(this);

        btnMyOrders = (Button) findViewById(R.id.btnMyOrders);
        btnMyOrders.setOnClickListener(this);

        ActualClient = AirportDbHelper.getClientById(this, getIntent().getLongExtra("id", 0));
        tvActualClient = (TextView)findViewById(R.id.tvActualClientName);
        String Name = ActualClient.getAsString(AirportDb.CLIENT_COLUMN_NAME);
        tvActualClient.setText(this.getString(R.string.welcome) + " " + Name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item:{
                Intent intent = new Intent(getApplicationContext(),ClientEditActivity.class);
                intent.putExtra("id",ActualClient.getAsInteger("id"));
                startActivityForResult(intent,ID_1);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ID_1){
            if(resultCode == RESULT_OK) {
                ActualClient = AirportDbHelper.getClientById(this,data.getIntExtra("id", 0));
                tvActualClient.setText(getString(R.string.welcome) + " " + ActualClient.getAsString(AirportDb.CLIENT_COLUMN_NAME));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonBookTicket:{
                Intent intent = new Intent(getApplicationContext(), MakeOrderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnMyOrders: {
                Intent intent = new Intent(this, ClientOrderActivity.class);
                startActivity(intent);
                break;
            }
        }


    }
}
