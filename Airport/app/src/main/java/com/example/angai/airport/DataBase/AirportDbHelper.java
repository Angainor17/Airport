package com.example.angai.airport.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by angai on 18.09.2016.
 */
public class AirportDbHelper {

    public static boolean Consist(Context context, String tableName, ContentValues cv){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getReadableDatabase();

        Cursor cursor = db.query(tableName,null,null,null,null,null,null);
        if(cursor.getCount()==0) return false;

        db.beginTransaction();
        cursor.moveToFirst();
        do{
            String login = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[AirportDb.CLIENT_COLUMN_LOGIN_INT]));
            String password = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[AirportDb.CLIENT_COLUMN_PASSWORD_INT]));

            Log.d("database1", login + " " + password);

            if(cv.get(AirportDb.CLIENT_COLUMN_LOGIN).equals(login) && cv.get(AirportDb.CLIENT_COLUMN_PASSWORD).equals(password)){

                db.setTransactionSuccessful();
                db.endTransaction();

                return true;
            }
        }while(cursor.moveToNext());


        db.setTransactionSuccessful();
        db.endTransaction();

        return false;
    }

    public static void Delete(Context context, String tableName, long id){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(tableName,"id = " + id, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    public static void Edit(Context context, String TableName, long id, ContentValues cv){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        try {
            db.beginTransaction();
            db.update(TableName,cv,"id = ?", new String[]{""+id});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static ContentValues getClientById(Context context, long id){
            AirportDb airportDb = new AirportDb(context);
            SQLiteDatabase db = airportDb.getWritableDatabase();

            db.beginTransaction();
            ContentValues cv = new ContentValues();
            String TableName = AirportDb.TABLENAME_CLIENT;
            Cursor c = db.query(TableName,null,null,null,null,null,null);

            c.moveToFirst();
            do{
                if(c.getInt(c.getColumnIndex("id")) == id){

                    cv.put(AirportDb.CLIENT_COLUMN_ID,id);
                    cv.put(AirportDb.CLIENT_COLUMN_LOGIN,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_LOGIN)));
                    cv.put(AirportDb.CLIENT_COLUMN_PASSWORD,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_PASSWORD)));
                    cv.put(AirportDb.CLIENT_COLUMN_NAME,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_NAME)));
                    cv.put(AirportDb.CLIENT_COLUMN_PASSPORT,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_PASSPORT)));
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    return cv;
                }
            }
               while(c.moveToNext());

            db.setTransactionSuccessful();
            db.endTransaction();

        return null;
    }

    public static ContentValues getTimetableFlightById(Context context, long id) {
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        db.beginTransaction();
        ContentValues cv = new ContentValues();
        String TableName = AirportDb.TABLENAME_TIMETABLE_FLIGHT;
        Cursor c = db.query(TableName, null, null, null, null, null, null);

        c.moveToFirst();
        do {
            if (c.getInt(c.getColumnIndex("id")) == id) {
                cv.put(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE, c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE)));
                cv.put(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME, c.getString(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME)));
                long id_flight = c.getInt(c.getColumnIndex(AirportDb.TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT));
                Cursor cursor = db.query(AirportDb.TABLENAME_FLIGHT, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getInt(cursor.getColumnIndex(AirportDb.COLUMN_ID)) == id_flight) {
                            cv.put(AirportDb.FLIGHT_COLUMN_FROM, cursor.getString(cursor.getColumnIndex(AirportDb.FLIGHT_COLUMN_FROM)));
                            cv.put(AirportDb.FLIGHT_COLUMN_TO, cursor.getString(cursor.getColumnIndex(AirportDb.FLIGHT_COLUMN_TO)));
                            cv.put(AirportDb.FLIGHT_COLUMN_COST, cursor.getInt(cursor.getColumnIndex(AirportDb.FLIGHT_COLUMN_COST)));
                            db.setTransactionSuccessful();
                            db.endTransaction();
                            return cv;
                        }
                    }
                    while (cursor.moveToNext());
                }

                db.setTransactionSuccessful();
                db.endTransaction();
                return null;
            }
        }
        while (c.moveToNext());

        db.setTransactionSuccessful();
        db.endTransaction();

        return null;
    }

    public static ContentValues getClientByLogin(Context context, String Login){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        db.beginTransaction();
        ContentValues cv = new ContentValues();
        String TableName = AirportDb.TABLENAME_CLIENT;
        Cursor c = db.query(TableName,null,null,null,null,null,null);

        c.moveToFirst();
        do{
            if(c.getString(c.getColumnIndex("login")).equals(Login)){

                cv.put(AirportDb.CLIENT_COLUMN_ID,c.getInt(c.getColumnIndex(AirportDb.CLIENT_COLUMN_ID)));
                cv.put(AirportDb.CLIENT_COLUMN_LOGIN,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_LOGIN)));
                cv.put(AirportDb.CLIENT_COLUMN_PASSWORD,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_PASSWORD)));
                cv.put(AirportDb.CLIENT_COLUMN_NAME,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_NAME)));
                cv.put(AirportDb.CLIENT_COLUMN_PASSPORT,c.getString(c.getColumnIndex(AirportDb.CLIENT_COLUMN_PASSPORT)));
                db.setTransactionSuccessful();
                db.endTransaction();
                return cv;
            }
        }
        while(c.moveToNext());

        db.setTransactionSuccessful();
        db.endTransaction();

        return null;
    }

    public static int Insert(Context context, String tableName, ContentValues cv){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();
        long id = 0;

            db.beginTransaction();
        id = db.insert(tableName, null, cv);

            db.setTransactionSuccessful();
            db.endTransaction();
            return  (int)id;

    }

    public static String[] getColumnArray(Context context, String TableName, String ColumnName){
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        Cursor c = db.query(TableName,new String[]{ColumnName},null,null,null,null,null,null);
        String []arr;
        if(c.getCount()>0) {
            c.moveToFirst();
            arr = new String[c.getCount()];

            int i = 0;
            do {
                arr[i++] = c.getString(c.getColumnIndex(ColumnName));
            }
            while (c.moveToNext());
            return arr;
        }
        return  null;
    }

    public static Cursor getTickets(Context context) {
        AirportDb airportDb = new AirportDb(context);
        SQLiteDatabase db = airportDb.getWritableDatabase();

        Cursor c = db.query(AirportDb.VIEW_TICKET, null, null, null, null, null, null);
        return c;
    }
}
