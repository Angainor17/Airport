package com.example.angai.airport;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.Root.RootMenuActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

/**
 * Created by angai on 07.10.2016.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class InterfaceUnitTest {

    private AuthorizationActivity authorizationActivity;
    private EditText etLogin;
    private EditText etPassword;
    private Button button;

    @Before
    public void setUp() {
        authorizationActivity = Robolectric.setupActivity(AuthorizationActivity.class);
        etLogin = (EditText) authorizationActivity.findViewById(R.id.editTextLogin);
        etPassword = (EditText) authorizationActivity.findViewById(R.id.editTextPassword);
        button = (Button) authorizationActivity.findViewById(R.id.button_aut_login);

    }

    @Test
    public void RootAuthorization() {
        etLogin.setText("root");
        etPassword.setText("root");

        button.performClick();

        Intent intent = Shadows.shadowOf(authorizationActivity).peekNextStartedActivity();
        Assert.assertEquals(RootMenuActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void NoExistClientAuthorization() throws Exception {
        etLogin.setText("1");
        etPassword.setText("1");

        button.performClick();

        if (Shadows.shadowOf(authorizationActivity).peekNextStartedActivity() != null) {
            Intent intent = Shadows.shadowOf(authorizationActivity).peekNextStartedActivity();
            Assert.assertEquals(RootMenuActivity.class.getCanonicalName(), intent.getComponent().getClassName());
        } else {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void ExistClientAuthorization() {
        Context context = RuntimeEnvironment.application;
        ContentValues cv = new ContentValues();
        cv.put(AirportDb.CLIENT_COLUMN_LOGIN, "1");
        cv.put(AirportDb.CLIENT_COLUMN_PASSWORD, "1");

        AirportDbHelper.Insert(context, AirportDb.TABLENAME_CLIENT, cv);

        etLogin.setText("1");
        etPassword.setText("1");

        button.performClick();

        if (Shadows.shadowOf(authorizationActivity).peekNextStartedActivity() != null) {
            Intent intent = Shadows.shadowOf(authorizationActivity).peekNextStartedActivity();
            Assert.assertEquals(ClientMenuActivity.class.getCanonicalName(), intent.getComponent().getClassName());
        } else {
            Assert.assertTrue(false);
        }
    }
}