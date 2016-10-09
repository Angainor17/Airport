package com.example.angai.airport.MakeOrder;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.example.angai.airport.DataBase.AirportDb;
import com.example.angai.airport.DataBase.AirportDbHelper;
import com.example.angai.airport.R;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {

    TableLayout panel;
    Toolbar toolbar;
    Spinner spinnerFrom, spinnerTo;
    ListView listViewOrderResult;
    EditText etCostLow, etCostHigh, etPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        panel = (TableLayout) findViewById(R.id.panel);

        spinnerFrom = (Spinner)findViewById(R.id.panelSpinnerFrom);
        spinnerTo = (Spinner)findViewById(R.id.panelSpinnerTo);

        etCostLow = (EditText)findViewById(R.id.panelEditTextCostLow);
        etCostHigh = (EditText)findViewById(R.id.panelEditTextCostHigh);
        etPlaces = (EditText)findViewById(R.id.panelEditTextPlaces);


        listViewOrderResult = (ListView)findViewById(R.id.listViewMakeOrder);

        toolbar.setTitle(R.string.press_on_me);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (panel.getVisibility() == View.GONE){
                    toolbar.setTitle(getString(R.string.hide));
                    panel.setVisibility(View.VISIBLE);
                }
                else {
                    toolbar.setTitle(getString(R.string.press_on_me));
                    panel.setVisibility(View.GONE);
                }

            }
        });


        ///////////////////SPINNERS////////////////////////////
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(this,R.layout.spinner_item,
                spinnersFromToConstructor(AirportDb.FLIGHT_COLUMN_FROM));


        ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(this, R.layout.spinner_item,
                spinnersFromToConstructor(AirportDb.FLIGHT_COLUMN_TO));

        adapterFrom.setDropDownViewResource(R.layout.spinner_dropdown_item);
        adapterTo.setDropDownViewResource(R.layout.spinner_dropdown_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        };

        spinnerFrom.setOnItemSelectedListener(onItemSelectedListener);
        spinnerTo.setOnItemSelectedListener(onItemSelectedListener);

        spinnerFrom.setAdapter(adapterFrom);
        spinnerTo.setAdapter(adapterTo);
        // заголовок
        spinnerFrom.setPrompt("Title");
        spinnerTo.setPrompt("Title");


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


        etCostHigh.addTextChangedListener(textWatcher);
        etCostLow.addTextChangedListener(textWatcher);
        etPlaces.addTextChangedListener(textWatcher);


        etCostLow.setText("0");
        etCostHigh.setText("99999");
        etPlaces.setText("0");
    }



    private String[] spinnersFromToConstructor(String column){
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.choose_item));

        String []dataFromColumns  = AirportDbHelper.getColumnArray(this, AirportDb.TABLENAME_FLIGHT,column);

        for(int i = 0; i < dataFromColumns.length; i++){
            if(!list.contains(dataFromColumns[i])){
                list.add(dataFromColumns[i]);
            }
        }

        String []resultStringArray = new String[list.size()];
        int i = 0;
        for(String s: list){
            resultStringArray[i++] = s;
        }

        return resultStringArray;
    }

    private void FillFlightList(OrderParameters orderParameters){
        AirportDb airportDb = new AirportDb(getApplicationContext());
        SQLiteDatabase db = airportDb.getWritableDatabase();
        OrderAdapter orderAdapter = new OrderAdapter(getApplicationContext(), orderParameters);

        listViewOrderResult.setAdapter(orderAdapter);
    }

    private void ChangeListener(){

        Integer costLow = 0;
        Integer costHigh = 0;
        Integer places = 0;

        if(etCostLow.getText().toString().length() > 0){
            costLow = Integer.valueOf(etCostLow.getText().toString());
        }else{
            costLow = 0;
        }

        if(etCostHigh.getText().toString().length() > 0){
            costHigh = Integer.valueOf(etCostHigh.getText().toString());
        }else{
            costHigh = 0;
        }

        if(etPlaces.getText().toString().length() > 0){
            places = Integer.valueOf(etPlaces.getText().toString());
        }else{
            places = 0;
        }

        OrderParameters orderParameters = new OrderParameters(
                spinnerFrom.getSelectedItem().toString(),
                spinnerTo.getSelectedItem().toString(),
                costLow,
                costHigh,
                places
        );
        FillFlightList(orderParameters);
    }

    }
