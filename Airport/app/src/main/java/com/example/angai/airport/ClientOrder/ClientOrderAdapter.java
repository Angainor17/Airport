package com.example.angai.airport.ClientOrder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.R;

import java.util.ArrayList;

public class ClientOrderAdapter extends BaseAdapter {
    Context context;
    SQLiteDatabase db;
    ArrayList<ContentValues> contentValuesArrayList = new ArrayList<ContentValues>();
    LayoutInflater lInflater;

    ClientOrderAdapter(Context context) {
        this.context = context;
        AirportDb airportDb = new AirportDb(context);
        db = airportDb.getWritableDatabase();
        contentValuesArrayList = FillArrayList();
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contentValuesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contentValuesArrayList.toArray(new ContentValues[contentValuesArrayList.size()])[position];
    }

    @Override
    public long getItemId(int position) {
        return contentValuesArrayList.toArray(new ContentValues[contentValuesArrayList.size()])[position].getAsLong("id");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.ticket_list_element, parent, false);
        }

        ContentValues cv = (ContentValues) getItem(position);

        ((TextView) view.findViewById(R.id.ticket_list_element_from_to)).setText(
                cv.get(AirportDb.FLIGHT_COLUMN_FROM) + " - " +
                        cv.get(AirportDb.FLIGHT_COLUMN_TO)
        );

        ((TextView) view.findViewById(R.id.ticket_list_element_date_time)).setText(
                cv.get(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE) + "     " +
                        cv.get(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME)
        );

        ((TextView) view.findViewById(R.id.ticket_list_element_cost)).setText(
                context.getString(R.string.cost) + ": " +
                        cv.get(AirportDb.FLIGHT_COLUMN_COST) + ".");
        return view;
    }

    public ArrayList<ContentValues> FillArrayList() {
        ArrayList<ContentValues> List = new ArrayList<>();
        Cursor c = AirportDbHelper.getTickets(context);
        if (c.moveToFirst()) {
            do {
                ContentValues cv = new ContentValues();

                cv.put(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE, c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE)));
                cv.put(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME, c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME)));
                cv.put(AirportDb.FLIGHT_COLUMN_COST, c.getInt(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_COST)));
                cv.put(AirportDb.FLIGHT_COLUMN_FROM, c.getString(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_FROM)));
                cv.put(AirportDb.FLIGHT_COLUMN_TO, c.getString(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_TO)));

                List.add(cv);
            }
            while (c.moveToNext());
        }
        return List;
    }
}
