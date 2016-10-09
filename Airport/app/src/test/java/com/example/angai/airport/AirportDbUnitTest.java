package com.example.angai.airport;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.suitebuilder.annotation.SmallTest;
import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
@SmallTest
public class AirportDbUnitTest {
    Context context;

    @Before
    public void setUp(){
        context = RuntimeEnvironment.application;
    }

    @Test
    public void TestDbRootConsist(){
        ContentValues cv = new ContentValues();
        cv.put(AirportDb.CLIENT_COLUMN_LOGIN, "root");
        cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, "root");

        Assert.assertTrue(AirportDbHelper.Consist(context, AirportDb.TABLENAME_CLIENT, cv));
    }

    @Test
    public void TestDbInsert(){
        ContentValues cv = new ContentValues();
        cv.put(AirportDb.CLIENT_COLUMN_LOGIN, "test");
        cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, "test");

        int id = AirportDbHelper.Insert(context, AirportDb.TABLENAME_CLIENT, cv);

        Assert.assertTrue(AirportDbHelper.Consist(context, AirportDb.TABLENAME_CLIENT,cv));
    }

    @Test
    public void TestDbDelete(){
        ContentValues cv = new ContentValues();
        cv.put(AirportDb.CLIENT_COLUMN_LOGIN, "test");
        cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, "test");

        int id = AirportDbHelper.Insert(context, AirportDb.TABLENAME_CLIENT, cv);

        AirportDbHelper.Delete(context, AirportDb.TABLENAME_CLIENT,id);

        Assert.assertFalse(AirportDbHelper.Consist(context, AirportDb.TABLENAME_CLIENT,cv));
    }
}