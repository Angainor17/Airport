package com.example.angai.airport.MakeOrder;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angai.airport.ClientMenuActivity;
import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.R;

public class SelectedOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private long id_flight;
    private ContentValues cv_flight;

    Button btnOrder;

    TextView tvFromTo;
    TextView tvDate;
    TextView tvTime;
    TextView tvCost;

    EditText etPlaces;

    TextView tvTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_order);

        btnOrder = (Button) findViewById(R.id.btn_selected_order_order);
        btnOrder.setOnClickListener(this);

        tvFromTo = (TextView) findViewById(R.id.tv_selected_order_from_to);
        tvDate = (TextView) findViewById(R.id.tv_selected_order_date);
        tvTime = (TextView) findViewById(R.id.tv_selected_order_time);
        tvCost = (TextView) findViewById(R.id.tv_selected_order_cost);
        tvTotalCost = (TextView) findViewById(R.id.tv_selected_order_total_cost);
        etPlaces = (EditText) findViewById(R.id.etSelectedOrderPlaces);

        id_flight = getIntent().getLongExtra("id_flight", -1);
        if (id_flight == -1) {
            finish();
        }
        cv_flight = AirportDbHelper.getTimetableFlightById(getApplicationContext(), id_flight);

        tvFromTo.setText(cv_flight.getAsString(AirportDb.FLIGHT_COLUMN_FROM) + " - " +
                cv_flight.getAsString(AirportDb.FLIGHT_COLUMN_TO)
        );

        tvCost.setText(getString(R.string.cost) + ": "
                + cv_flight.getAsInteger(AirportDb.FLIGHT_COLUMN_COST));

        tvDate.setText(cv_flight.getAsString(AirportDb.TIMETABLE_FLIGHT_COLUMN_DATE));
        tvTime.setText(cv_flight.getAsString(AirportDb.TIMETABLE_FLIGHT_COLUMN_TIME));
        tvTotalCost.setText(getString(R.string.total_cost));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ChangeListener();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ChangeListener();
            }
        };

        etPlaces.addTextChangedListener(textWatcher);

    }

    public void ChangeListener() {
        if (etPlaces.getText().toString().length() >= 1) {
            long totalCost = Integer.valueOf(etPlaces.getText().toString()) * cv_flight.getAsInteger(AirportDb.FLIGHT_COLUMN_COST);
            tvTotalCost.setText(getString(R.string.total_cost) + ": " + totalCost);
        }
    }

    public void FinishOrder() {
        ContentValues cv = new ContentValues();
        cv.put(AirportDb.TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_TIMETABLE_FLIGHT, id_flight);
        cv.put(AirportDb.TIMETABLE_FLIGHT_CLIENT_COLUMN_ID_CLIENT, ClientMenuActivity.ActualClient.getAsInteger(AirportDb.COLUMN_ID));
        AirportDbHelper.Insert(getApplication(), AirportDb.TABLENAME_TIMETABLE_FLIGHT_CLIENT, cv);

        Toast.makeText(this, getString(R.string.order_is_accepted), Toast.LENGTH_SHORT);

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selected_order_order: {
                FinishOrder();
                break;
            }
        }
    }
}
