package com.example.angai.airport.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by angai on 17.09.2016.
 */
public class AirportDb extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "airportDb";
    final static int DATABASE_VERSION = 1;

    public final static String VIEW_TICKET = "ticket";

    public final static String TABLENAME_CLIENT = "client";
    public final static String TABLENAME_FLIGHT = "flight";
    public final static String TABLENAME_PLANE = "plane";
    public final static String TABLENAME_TIMETABLE_FLIGHT = "timetable_flight";
    public final static String TABLENAME_TIMETABLE_FLIGHT_CLIENT = "timetableFlight_client";

    public final static String COLUMN_ID = "id";

    public final static int CLIENT_COLUMN_ID_INT = 0;
    public final static int CLIENT_COLUMN_NAME_INT = 1;
    public final static int CLIENT_COLUMN_LOGIN_INT = 2;
    public final static int CLIENT_COLUMN_PASSWORD_INT = 3;
    public final static int CLIENT_COLUMN_PASSPORT_INT = 4;

    public final static String CLIENT_COLUMN_ID = "id";
    public final static String CLIENT_COLUMN_NAME = "name";
    public final static String CLIENT_COLUMN_LOGIN = "login";
    public final static String CLIENT_COLUMN_PASSWORD = "password";
    public final static String CLIENT_COLUMN_PASSPORT = "passport";

    public final static String FLIGHT_COLUMN_ID = "id";
    public final static String FLIGHT_COLUMN_ID_PLANE = "id_plane";
    public final static String FLIGHT_COLUMN_FROM = "fromPlace";
    public final static String FLIGHT_COLUMN_TO = "toPlace";
    public final static String FLIGHT_COLUMN_COST = "cost";

    public final static String PLANE_COLUMN_NAME = "name";
    public final static String PLANE_COLUMN_CAPACITY = "capacity";

    public final static String TIMETABLE_FLIGHT_COLUMN_DATE = "date";
    public final static String TIMETABLE_FLIGHT_COLUMN_TIME = "time";
    public final static String TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT = "id_flight";

    public final static String FLIGHTClIENT_COLUMN_ID_FLIGHT = "id_flight";

    public final static String TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT = "id_client";
    public final static String TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT = "id_timetable_flight";
    public final static String TIMETABLE_FLIGHT_CLIENT_COLUMN_ID = "id";

    public AirportDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  " + TABLENAME_CLIENT + "("
                + COLUMN_ID +" INTEGER primary key autoincrement,"
                + CLIENT_COLUMN_NAME + " TEXT,"
                + CLIENT_COLUMN_LOGIN + " TEXT, "
                + CLIENT_COLUMN_PASSWORD + " TEXT, "
                + CLIENT_COLUMN_PASSPORT + " TEXT " + ");");

        db.execSQL("create table  " + TABLENAME_PLANE + "("
                + COLUMN_ID +" INTEGER primary key autoincrement,"
                + PLANE_COLUMN_NAME + " TEXT,"
                + PLANE_COLUMN_CAPACITY + " INTEGER not NULL" + ");");

        db.execSQL("create table  " + TABLENAME_FLIGHT + "("
                + COLUMN_ID +" INTEGER primary key autoincrement,"
                + FLIGHT_COLUMN_ID_PLANE + " INTEGER, "
                + FLIGHT_COLUMN_FROM + " TEXT, "
                + FLIGHT_COLUMN_TO + " TEXT, "
                + FLIGHT_COLUMN_COST + " INTEGER, "
                + "FOREIGN KEY(" + FLIGHT_COLUMN_ID_PLANE + ") REFERENCES " + TABLENAME_PLANE + "(" + COLUMN_ID + ")"+ ");");

        db.execSQL("create table  " + TABLENAME_TIMETABLE_FLIGHT + "("
                + COLUMN_ID + " INTEGER primary key autoincrement, "
                + TIMETABLE_FLIGHT_COLUMN_DATE + " TEXT, "
                + TIMETABLE_FLIGHT_COLUMN_TIME + " TEXT, "
                + TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT + " INTEGER, "
                + "FOREIGN KEY(" + TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT + ") REFERENCES " + TABLENAME_FLIGHT + "(" + COLUMN_ID + ") "
                + ");");

        db.execSQL("create table  " + TABLENAME_TIMETABLE_FLIGHT_CLIENT + "("
                + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID + " INTEGER primary key autoincrement, "
                + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT + " INTEGER, "
                + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT + " INTEGER, "
                + "FOREIGN KEY(" + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT + ") REFERENCES " + TABLENAME_TIMETABLE_FLIGHT + "(" + COLUMN_ID + "), "
                + "FOREIGN KEY(" + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT + ") REFERENCES "+ TABLENAME_CLIENT + "(" + COLUMN_ID + ")"
                + ");");



        makeRoot(db);
        LoadPlanes(db);
        LoadFlight(db);
        LoadTimetableFlight(db);
       // LoadTimetableFlightClient(db);

        createViewTicket(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void LoadPlanes(SQLiteDatabase db){
       String names[]={"TY-126","Pion","BOING-666","MIG-17"};
       Integer capacities[] = {190,50,250,87};
        for(int i = 0; i < names.length; i++) {
            ContentValues element = new ContentValues();
            element.put(PLANE_COLUMN_NAME, names[i]);
            element.put(PLANE_COLUMN_CAPACITY, capacities[i]);

            db.insert(TABLENAME_PLANE, null, element);
        }
    }

    private void LoadFlight(SQLiteDatabase db){
        Integer planes[] = {1,1,2,3};
        String fromPlaces[] = {"Sevastopol", "Moscow", "Sevastopol", "New-York"};
        String toPlaces[] = {"Madagascar", "Berlin", "Berlin", "Berlin"};
        Integer costs[] = {7890, 1, 3213, 1000};

        for(int i = 0; i < planes.length; i++) {
            ContentValues element = new ContentValues();

            element.put(FLIGHT_COLUMN_ID_PLANE, planes[i]);
            element.put(FLIGHT_COLUMN_FROM, fromPlaces[i]);
            element.put(FLIGHT_COLUMN_TO, toPlaces[i]);
            element.put(FLIGHT_COLUMN_COST, costs[i]);

            db.insert(TABLENAME_FLIGHT, null, element);
        }
    }

    public void LoadTimetableFlight(SQLiteDatabase db){

        String dates[] = {"17:10:2016","29:01:2017","03:05:2016","03:03:2016","04:01:2017"};
        String times[] = {"17:11","08:00","12:30","17:10","18:18"};
        int id_flights[] = {4, 1, 2, 4, 2};

        for(int i = 0; i < dates.length; i++) {
            ContentValues element = new ContentValues();

            element.put(TIMETABLE_FLIGHT_COLUMN_DATE, dates[i]);
            element.put(TIMETABLE_FLIGHT_COLUMN_TIME, times[i]);
            element.put(TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT, id_flights[i]);

            db.insert(TABLENAME_TIMETABLE_FLIGHT, null, element);
        }
    }

    private void LoadTimetableFlightClient(SQLiteDatabase db) {
        Integer id_timetable_flight[] = {0, 1, 1, 0, 1};
        Integer id_client[] = {1, 2, 2, 3, 1};

        for (int i = 0; i < id_client.length; i++) {
            ContentValues element = new ContentValues();
            element.put(TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT, id_client[i]);
            element.put(TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT, id_timetable_flight[i]);

            db.insert(TABLENAME_TIMETABLE_FLIGHT_CLIENT, null, element);
        }
    }

    private void makeRoot(SQLiteDatabase db){
        ContentValues root = new ContentValues();
        root.put(CLIENT_COLUMN_LOGIN, "root");
        root.put(CLIENT_COLUMN_PASSWORD, "root");
        root.put(CLIENT_COLUMN_PASSPORT, "root");
        root.put(CLIENT_COLUMN_NAME, "root");

        db.insert(TABLENAME_CLIENT, null, root);
    }

    private void createViewTicket(SQLiteDatabase db) {
        String string = "CREATE VIEW " + VIEW_TICKET + " AS SELECT " +
                TABLENAME_TIMETABLE_FLIGHT_CLIENT + "." + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT + ", " +
                TABLENAME_TIMETABLE_FLIGHT_CLIENT + "." + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT + ", " +
                TABLENAME_TIMETABLE_FLIGHT + "." + TIMETABLE_FLIGHT_COLUMN_DATE + ", " +
                TABLENAME_TIMETABLE_FLIGHT + "." + TIMETABLE_FLIGHT_COLUMN_TIME + ", " +
                TABLENAME_FLIGHT + "." + FLIGHT_COLUMN_FROM + ", " +
                TABLENAME_FLIGHT + "." + FLIGHT_COLUMN_TO + ", " +
                TABLENAME_FLIGHT + "." + FLIGHT_COLUMN_COST + " " +
                "FROM " + TABLENAME_TIMETABLE_FLIGHT_CLIENT + " " +
                "INNER JOIN " + TABLENAME_TIMETABLE_FLIGHT + " ON " +
                TABLENAME_TIMETABLE_FLIGHT_CLIENT + "." + TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT + " = " +
                TABLENAME_TIMETABLE_FLIGHT + "." + COLUMN_ID + " " +
                "INNER JOIN " + TABLENAME_FLIGHT + " ON " +
                TABLENAME_TIMETABLE_FLIGHT + "." + TIMETABLE_FLIGHT_COLUMN_ID_FLIGHT + " = " +
                TABLENAME_FLIGHT + "." + COLUMN_ID + " ";
        db.execSQL(string);
    }
}