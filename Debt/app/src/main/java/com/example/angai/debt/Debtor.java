package com.example.angai.debt;

import android.graphics.Bitmap;

/**
 * Created by angai on 10.09.2016.
 */
public class Debtor {
    private String Name;
    private String Item;
    private Bitmap Photo;

    public  Debtor(String Name, String Item, Bitmap Photo){
        this.Name = Name;
        this.Item = Item;
        this.Photo = Photo;
    }

    public String getName(){
        return Name;
    }

    public String getItem(){
        return Item;
    }

    public Bitmap getPhoto(){
        return Photo;
    }

}
