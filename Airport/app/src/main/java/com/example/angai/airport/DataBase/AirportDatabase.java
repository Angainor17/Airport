package com.example.angai.airport.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by angai on 17.09.2016.
 */
public class AirportDatabase extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "airportDb";
    final static int DATABASE_VERSION = 1;

    public final static String TABLENAME_CLIENT = "client";
    public final static String TABLENAME_FLIGHT = "flight";
    public final static String TABLENAME_PLANE = "plane";
    public final static String TABLENAME_FILGHT_CLIENT = "flight_client";

    public final static int CLIENT_COLUMN_NAME_INT = 1;
    public final static int CLIENT_COLUMN_LOGIN_INT = 2;
    public final static int CLIENT_COLUMN_PASSWORD_INT = 3;
    public final static int CLIENT_COLUMN_PASSPORT_INT = 4;

    public final static String CLIENT_COLUMN_NAME = "name";
    public final static String CLIENT_COLUMN_LOGIN = "login";
    public final static String CLIENT_COLUMN_PASSWORD = "password";
    public final static String CLIENT_COLUMN_PASSPORT = "passport";

    public AirportDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table  " + TABLENAME_CLIENT + "("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "login text, "
                + "password text, "
                + "passport text" + ");");


        ContentValues root = new ContentValues();
        root.put(CLIENT_COLUMN_LOGIN,"root");
        root.put(CLIENT_COLUMN_PASSWORD,"root");
        db.insert(TABLENAME_CLIENT,null,root);


        db.execSQL("create table  " + TABLENAME_PLANE + "("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "capacity integer not NULL" + ");");

        db.execSQL("create table  " + TABLENAME_FLIGHT + "("
                + "id integer primary key autoincrement, "
                + "plane integer, "
                + "date numeric not null, "
                + "time numeric not null, "
                + "fromPlace text, "
                + "toPlace text, "
                + "password text, "
                + "FOREIGN KEY(plane) REFERENCES " + TABLENAME_PLANE + "(id)"+ ");");

        db.execSQL("create table  " + TABLENAME_FILGHT_CLIENT + "("
                + "id_flight integer, "
                + "id_client integer, "
                + "FOREIGN KEY(id_flight) REFERENCES " + TABLENAME_FLIGHT + "(id), "
                + "FOREIGN KEY(id_client) REFERENCES "+ TABLENAME_CLIENT + "(id)"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
