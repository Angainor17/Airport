package com.example.angai.airport.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by angai on 18.09.2016.
 */
public class AirportDatabaseHelper {

    public static boolean Consist(SQLiteDatabase db, String tableName,ContentValues cv){
        Cursor cursor = db.query(tableName,null,null,null,null,null,null);
        if(cursor.getCount()==0) return false;

        while(cursor.moveToNext()){
            String login = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[AirportDatabase.CLIENT_COLUMN_LOGIN_INT]));
            String password = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[AirportDatabase.CLIENT_COLUMN_PASSWORD_INT]));

            Log.d("database1", login + " " + password);

            if(cv.get(AirportDatabase.CLIENT_COLUMN_LOGIN).equals(login) && cv.get(AirportDatabase.CLIENT_COLUMN_PASSWORD).equals(password)){
                cursor.close();
                db.close();
                return true;
            }
        }

        cursor.close();
        db.close();
        return false;
    }

}
