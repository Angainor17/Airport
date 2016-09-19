package com.example.angai.airport.Root;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.angai.airport.DataBase.AirportDatabase;
import com.example.angai.airport.R;

/**
 * Created by angai on 18.09.2016.
 */
public class ClientAdapter extends BaseAdapter {
    Context context;
    SQLiteDatabase db;
    LayoutInflater lInflater;

    public ClientAdapter(Context context,SQLiteDatabase db){
        this.db = db;
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        Cursor c = db.query(AirportDatabase.TABLENAME_CLIENT,null,null,null,null,null,AirportDatabase.CLIENT_COLUMN_NAME);
        return c.getCount();
    }

    @Override
    public Object getItem(int position) {
        AirportDatabase airportDatabase = new AirportDatabase(context);
        SQLiteDatabase db1 = airportDatabase.getWritableDatabase();
        Cursor c = db1.query(AirportDatabase.TABLENAME_CLIENT,null,null,null,null,null,AirportDatabase.CLIENT_COLUMN_NAME);
        c.moveToFirst();
        if(position > c.getCount()){
            return null;
        }

        for(int i=0; i < position; i++){
            c.moveToNext();
        }

        return c;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.client_list_element, parent, false);
        }

        Cursor c = (Cursor)getItem(position);


        String name = c.getString(c.getColumnIndex(AirportDatabase.CLIENT_COLUMN_NAME));
        String login = "login: " + c.getString(c.getColumnIndex(AirportDatabase.CLIENT_COLUMN_LOGIN));
        String password = "password: " + c.getString(c.getColumnIndex(AirportDatabase.CLIENT_COLUMN_PASSWORD));
        String passport = c.getString(c.getColumnIndex(AirportDatabase.CLIENT_COLUMN_PASSPORT));

        ((TextView) view.findViewById(R.id.elementLogin)).setText(login);
        ((TextView) view.findViewById(R.id.elementPassport)).setText(passport);
        ((TextView) view.findViewById(R.id.elementName)).setText(name);
        ((TextView) view.findViewById(R.id.elementPassword)).setText(password);

        return view;
    }
}
