package com.example.angai.airport.MakeOrder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.R;

import java.util.ArrayList;

/**
 * Created by angai on 01.10.2016.
 */


public class OrderAdapter extends BaseAdapter {

    private final static  String TAG = "database1";

    Context context;
    OrderParameters orderParam;
    SQLiteDatabase db;
    ArrayList<ContentValues> contentValuesArrayList = new ArrayList<ContentValues>();
    LayoutInflater lInflater;

    OrderAdapter(Context context, OrderParameters orderParam){
        this.context = context;
        this.orderParam = orderParam;
        AirportDb airportDb = new AirportDb(context);
        db = airportDb.getWritableDatabase();
        contentValuesArrayList = getOrderByParams(db);
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
            view = lInflater.inflate(R.layout.flight_list_element, parent, false);
        }

        ContentValues cv = (ContentValues) getItem(position);

        ((TextView)view.findViewById(R.id.flight_list_element_from_to)).setText(
                cv.get(AirportDb.FLIGHT_COLUMN_FROM) + " - " +
                        cv.get(AirportDb.FLIGHT_COLUMN_TO)
        );

        ((TextView)view.findViewById(R.id.flight_list_element_date_time)).setText(
                cv.get(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE) + "     " +
                        cv.get(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME)
        );

        ((TextView)view.findViewById(R.id.flight_list_element_cost)).setText(
                context.getString(R.string.cost) + ": "+
                cv.get(AirportDb.FLIGHT_COLUMN_COST) + ".");


        return  view;
    }

    private ArrayList<ContentValues> getOrderByParams(SQLiteDatabase db){
        ArrayList<ContentValues> resultList = new ArrayList<ContentValues>();

         String selection = "";

         ArrayList<String> selectionArgsArrayList = new ArrayList<>();

         if(!orderParam.getPlaceFrom().equals(context.getString(R.string.choose_item))){
             selection = selection.concat(" " + AirportDb.FLIGHT_COLUMN_FROM + " = ? ");
             selectionArgsArrayList.add(orderParam.getPlaceFrom());
         }

         if(!orderParam.getPlaceTo().equals(context.getString(R.string.choose_item))){
             if(selection.length()>0) selection = selection.concat(" AND ");
             selection = selection.concat(" " + AirportDb.FLIGHT_COLUMN_TO + " = ? ");
             selectionArgsArrayList.add(orderParam.getPlaceTo());
         }

        if(orderParam.getCostLow() >= 0){
            if(selection.length()>0) selection = selection.concat(" AND ");
            selection = selection.concat(" " + AirportDb.FLIGHT_COLUMN_COST + " > ? ");
            selectionArgsArrayList.add(Integer.toString(orderParam.getCostLow()));
        }

        if(orderParam.getCostHigh() >= 0){
            if(selection.length()>0) selection = selection.concat(" AND ");
            selection = selection.concat(" " + AirportDb.FLIGHT_COLUMN_COST + " < ? ");
            selectionArgsArrayList.add(Integer.toString(orderParam.getCostHigh()));
        }

        Cursor cursorFlight = db.query(AirportDb.TABLENAME_FLIGHT,
                null,
                selection,
                selectionArgsArrayList.toArray(new String[selectionArgsArrayList.size()]),
                        null,null,null,null);

        if(cursorFlight.moveToFirst()) {
            do {
                int idFlight = cursorFlight.getInt(cursorFlight.getColumnIndex(AirportDb.COLUMN_ID));

                Cursor cursorClient = db.query(AirportDb.TABLENAME_TIMETABLE_FLIGHT_CLIENT, null, null, null, null, null, null);
                int clientsInFlightCounter = 0;

                if (cursorClient.moveToFirst()) {
                    if (cursorClient.isFirst())
                    do {
                        try {
                            if (cursorFlight.getLong(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_ID)) ==
                                    cursorClient.getInt(cursorClient.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT))) {
                                clientsInFlightCounter++;
                            }
                        } catch (Exception e) {
                            clientsInFlightCounter = 0;
                        }

                    } while (cursorClient.moveToNext());
                }

                /////////////////////////////////////////////////////////////////////////
                int maxPlaces = 0;
                int planeID = cursorFlight.getInt(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_ID_PLANE));
                Cursor c = db.query(AirportDb.TABLENAME_PLANE,null,null,null,null,null,null);
                c.moveToFirst();
                do{
                    if(c.getInt(c.getColumnIndex(AirportDb.COLUMN_ID)) == planeID){
                        maxPlaces = c.getInt(c.getColumnIndex(AirportDb.PLANE_COLUMN_CAPACITY));
                        break;
                    }
                }
                while(c.moveToNext());
                /////////////////////////////////////////////////////////////////////////
                if (clientsInFlightCounter + orderParam.getPlaces() < maxPlaces) {
                    ContentValues cv = new ContentValues();

                    cv.put(AirportDb.FLIGHT_COLUMN_ID, cursorFlight.getInt(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_ID)));
                    cv.put(AirportDb.FLIGHT_COLUMN_ID_PLANE, cursorFlight.getInt(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_ID_PLANE)));
                    cv.put(AirportDb.FLIGHT_COLUMN_FROM, cursorFlight.getString(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_FROM)));
                    cv.put(AirportDb.FLIGHT_COLUMN_TO, cursorFlight.getString(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_TO)));
                    cv.put(AirportDb.FLIGHT_COLUMN_COST, cursorFlight.getInt(cursorFlight.getColumnIndex(AirportDb.FLIGHT_COLUMN_COST)));

                    resultList.add(cv);
                }
                /////////////////////////////////////////////////////////////////////////
            }
            while (cursorFlight.moveToNext());
        }
        {
            Log.d(TAG, "resultList debug");
            for (ContentValues cv : resultList) {
                Log.d(TAG, cv.getAsString(AirportDb.FLIGHT_COLUMN_FROM));
                Log.d(TAG, cv.getAsString(AirportDb.FLIGHT_COLUMN_TO));
                Log.d(TAG, "---------------------------------------");
            }
        }

        debugTableFlight();
        debugTableTimetableFlight();
        //////////////////////////////////////////////////////////////////////////////
        Cursor c = db.query(AirportDb.TABLENAME_TIMETABLE_FLIGHT, null, null, null, null, null, null);
        ArrayList<ContentValues> List = new ArrayList<ContentValues>();
        if(c.moveToFirst()){
            do{
                Log.d("TAG","--------------------------------------------------");
                for(ContentValues contentValues: resultList){
                    ContentValues cv = new ContentValues(contentValues);

                    if(c.getInt(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT))==
                    cv.getAsInteger(AirportDb.COLUMN_ID)){

                        Log.d("TAG","DATE: " + c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE)));
                        Log.d("TAG","TIME: " + c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME)));
                        Log.d("TAG", cv.getAsString(AirportDb.FLIGHT_COLUMN_FROM) + "-" +cv.getAsString(AirportDb.FLIGHT_COLUMN_TO));

                        Log.d("TAG"," ");

                        cv.put(AirportDb.COLUMN_ID, c.getInt(c.getColumnIndex(AirportDb.COLUMN_ID)));
                        cv.put(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE,c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE)));
                        cv.put(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME,c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME)));
                        List.add(cv);
                        {
                            for(ContentValues cv1: List){
                                Log.d("TAG2", cv1.getAsString(AirportDb.FLIGHT_COLUMN_FROM));
                                Log.d("TAG2", cv1.getAsString(AirportDb.FLIGHT_COLUMN_TO));
                                Log.d("TAG2", cv1.getAsString(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE));
                                Log.d("TAG2", cv1.getAsString(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME));
                                Log.d("TAG2", "");

                            }
                            Log.d("TAG2", "---------------------------------------");
                        }
                        break;
                    }

                }
            }
            while (c.moveToNext());
        }

        return List;
    }

    public void debugTableFlight(){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        Cursor c = db.query(AirportDb.TABLENAME_FLIGHT,null,null,null,null,null,null);
        Log.d(TAG,AirportDb.TABLENAME_FLIGHT + " debug ");
        if(c.moveToFirst()){
            do{
                Log.d(TAG,"FLIGHT_COLUMN_ID: " + c.getInt(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_ID)) );
                Log.d(TAG,"FLIGHT_COLUMN_ID_PLANE: " + c.getInt(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_ID_PLANE)) );
                Log.d(TAG,"FLIGHT_COLUMN_FROM: " + c.getString(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_FROM)) );
                Log.d(TAG,"FLIGHT_COLUMN_TO: " + c.getString(c.getColumnIndex(AirportDb.FLIGHT_COLUMN_TO)) );
                Log.d(TAG,"-----------------------------------");

            }
            while(c.moveToNext());
        }
    }

    public void debugTableTimetableFlight(){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        Cursor c = db.query(AirportDb.TABLENAME_TIMETABLE_FLIGHT,null,null,null,null,null,null);
        Log.d(TAG, AirportDb.TABLENAME_TIMETABLE_FLIGHT + " debug ");
        if(c.moveToFirst()){
            do{
                Log.d(TAG,"ID: " + c.getInt(c.getColumnIndex(AirportDb.COLUMN_ID)) );
                Log.d(TAG,"TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT: " + c.getInt(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT)) );
                Log.d(TAG,"TIMETABLE_FLIGHT_COLUMN_DATE: " + c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE)) );
                Log.d(TAG,"-----------------------------------");

            }
            while(c.moveToNext());
        }
    }

}
