package com.example.angai.airport.Root;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.R;

/**
 * Created by angai on 18.09.2016.
 */
public class ClientAdapter extends BaseAdapter {
    Context context;
    SQLiteDatabase db;
    LayoutInflater lInflater;

    public ClientAdapter(Context context){
        this.context = context;
        AirportDb airportDb = new AirportDb(context);
        db = airportDb.getWritableDatabase();
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        Cursor c = db.query(AirportDb.TABLENAME_CLIENT,null,null,null,null,null, AirportDb.CLIENT_COLUMN_NAME);
        return c.getCount();
    }

    @Override
    public Object getItem(int position) {
        Cursor c = db.query(AirportDb.TABLENAME_CLIENT,null,null,null,null,null, AirportDb.CLIENT_COLUMN_NAME);

        if(position > c.getCount()){
            return null;
        }

        c.moveToFirst();
        for(int i = 0; i < position; i++){
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


        String name = c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_NAME));
        String login = "login: " + c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_LOGIN));
        String password = "password: " + c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_PASSWORD));
        String passport = c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_PASSPORT));

        ((TextView) view.findViewById(R.id.elementLogin)).setText(login);
        ((TextView) view.findViewById(R.id.elementPassport)).setText(passport);
        ((TextView) view.findViewById(R.id.elementName)).setText(name);
        ((TextView) view.findViewById(R.id.elementPassword)).setText(password);

        return view;
    }
}
