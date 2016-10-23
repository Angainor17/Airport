package com.example.angai.airport.ClientOrder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.angai.airport.ClientMenuActivity;
import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.R;

public class ClientOrderActivity extends AppCompatActivity {
    ListView lv;

    final int DELETE_ID = 0;
    final int DELETE_ALL_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_client_order);

        lv = (ListView) findViewById(R.id.listViewClientOrder);

        View header = getLayoutInflater().inflate(R.layout.client_order_header, null);
        lv.addHeaderView(header);
        registerForContextMenu(lv);
        lv.setAdapter(new ClientOrderAdapter(this));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.return_ticket);
        menu.add(0, DELETE_ALL_ID, 0, R.string.return_all_tickets);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID: {
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                long idDelete = lv.getItemIdAtPosition(acmi.position);

                AirportDbHelper.DeleteTicket(getApplicationContext(),
                        ClientMenuActivity.ActualClient.getAsInteger(AirportDb.COLUMN_ID),
                        idDelete,
                        1
                );

                ClientOrderAdapter clientAdapter = new ClientOrderAdapter(this);
                lv.setAdapter(clientAdapter);

                break;
            }

            case DELETE_ALL_ID: {
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                long idDelete = lv.getItemIdAtPosition(acmi.position);

                AirportDbHelper.DeleteTicket(getApplicationContext(),
                        ClientMenuActivity.ActualClient.getAsInteger(AirportDb.COLUMN_ID),
                        idDelete,
                        0
                );

                ClientOrderAdapter clientAdapter = new ClientOrderAdapter(this);
                lv.setAdapter(clientAdapter);

                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    public static void Debug1(Context context) {
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();
        Cursor c = db.query(AirportDb.TABLENAME_TIMETABLE_FLIGHT_CLIENT, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Log.d("CLIENT", "id_flight " + c.getInt(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT)));
                Log.d("CLIENT", "id_client " + c.getInt(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT)));
                Log.d("CLIENT", " ");
            }
            while (c.moveToNext());
        }
    }

    public static void Debug2(Context context) {
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();
        Cursor c = db.query(AirportDb.VIEW_TICKET, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Log.d("CLIENT", "id_flight " + c.getInt(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT)));
                Log.d("CLIENT", "from " + c.getString(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_FROM)));
                Log.d("CLIENT", "to " + c.getString(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_TO)));
                Log.d("CLIENT", " ");
            }
            while (c.moveToNext());
        }
    }
}
