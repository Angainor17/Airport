package com.example.angai.debt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angai on 10.09.2016.
 */
public class DebtorAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Debtor> objects;

    DebtorAdapter(Context context, ArrayList<Debtor> debtors) {
        ctx = context;
        objects = debtors;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Debtor debtor = (Debtor) getItem(position);

        ((TextView) view.findViewById(R.id.tvDescr)).setText(debtor.getName() + " : " + debtor.getItem());
        ((ImageView) view.findViewById(R.id.ivImage)).setImageBitmap(debtor.getPhoto());

        return view;
    }
}
