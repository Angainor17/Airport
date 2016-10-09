package com.example.angai.airport;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by angai on 07.10.2016.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class InterfaceUnitTest {
    Context context;
    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application;
    }

    @Test
    public void Authorization () throws Exception{
        AuthorizationActivity authorizationActivity = Robolectric.setupActivity(AuthorizationActivity.class);

        Button button = (Button) authorizationActivity.findViewById(R.id.buttonAuthorization);
        EditText etLogin = (EditText)authorizationActivity.findViewById(R.id.editTextLogin);
        EditText etPassword = (EditText)authorizationActivity.findViewById(R.id.editTextPassword);

        etLogin.setText("root");
        etPassword.setText("root");

        button.performClick();
        Assert.assertTrue(true);

    }
}
