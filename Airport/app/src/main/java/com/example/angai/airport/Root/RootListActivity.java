package com.example.angai.airport.Root;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.angai.airport.ClientEditActivity;
import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.R;

public class RootListActivity extends AppCompatActivity {
    ListView listView;
    final int DELETE_ID = 0;
    final int EDIT_ID = 1;
    final int REQUEST_CODE_1 = 1;

    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listViewClient);

        registerForContextMenu(listView);

        header = getLayoutInflater().inflate(R.layout.client_list_header, null);
        ((TextView)header.findViewById(R.id.header)).setText(R.string.client_list_header);
        listView.addHeaderView(header);

        ClientAdapter clientAdapter = new ClientAdapter(this);
        listView.setAdapter(clientAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.delete_client);
        menu.add(0, EDIT_ID, 0, R.string.edit_client);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case DELETE_ID:{

                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


                int idDelete = ((Cursor)listView.getItemAtPosition(acmi.position)).getInt(
                        ((Cursor)listView.getItemAtPosition(acmi.position)).getColumnIndex("id"));

                AirportDbHelper.Delete(this, AirportDb.TABLENAME_CLIENT,idDelete);

                ClientAdapter clientAdapter = new ClientAdapter(this);
                listView.setAdapter(clientAdapter);

                break;
            }
            case EDIT_ID:{
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int idEdit = ((Cursor)listView.getItemAtPosition(acmi.position)).getInt(
                        ((Cursor)listView.getItemAtPosition(acmi.position)).getColumnIndex("id"));

                Intent intent = new Intent(getApplicationContext(),ClientEditActivity.class);
                intent.putExtra("id",idEdit);
                startActivityForResult(intent,REQUEST_CODE_1);
                
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_1){
            ClientAdapter clientAdapter = new ClientAdapter(this);

            listView.setAdapter(clientAdapter);
        }
    }
}
