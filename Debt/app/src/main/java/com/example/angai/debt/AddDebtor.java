package com.example.angai.debt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AddDebtor extends AppCompatActivity {
    int CAMERA_RESULT = 1;

    Bitmap Photo = null;

    ImageButton imageButton;
    Button buttonOK;

    EditText editTextName;
    EditText editTextItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debtor);

        imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setImageResource(R.drawable.camera);
        buttonOK = (Button) findViewById(R.id.buttonOK);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextItem = (EditText) findViewById(R.id.editTextItem);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextItem.getText().length() > 0 && editTextName.getText().length() > 0){
                        AddNewDebtor(new Debtor(
                                editTextName.getText().toString(),
                                editTextItem.getText().toString(),
                                Photo)
                        );

                    Main.ShowListView();
                    finish();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_RESULT);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_RESULT){
            if(resultCode == Activity.RESULT_OK){
                Photo = (Bitmap) data.getExtras().get("data");
                imageButton.setImageBitmap(Photo);
            }
        }
    }

    public void AddNewDebtor(Debtor debtor){
        Main.DebtorList.add(debtor);
    }
}
