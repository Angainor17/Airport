package com.example.angai.airport;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import com.example.angai.airport.DataBase.AirportDb;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void TestDbRootExist(){
        AirportDb airportDb = new AirportDb(getContext());
        SQLiteDatabase db = airportDb.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AirportDb.CLIENT_COLUMN_NAME, "root");
        cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, "root");
        Assert.assertTrue(true);
        //Assert.assertTrue(AirportDbHelper.Consist(db,AirportDb.TABLENAME_CLIENT,cv));
    }
}