package com.example.angai.airport.ClientOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.angai.airport.R;

public class ClientOrderActivity extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order);

        lv = (ListView) findViewById(R.id.listViewClientOrder);
        ClientOrderAdapter clientOrderAdapter = new ClientOrderAdapter(this);
        lv.setAdapter(clientOrderAdapter);
    }
}
