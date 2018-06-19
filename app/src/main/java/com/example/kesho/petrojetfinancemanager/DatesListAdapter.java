package com.example.kesho.petrojetfinancemanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kesho on 4/14/2018.
 */

public class DatesListAdapter extends ArrayAdapter<Order> {

    Activity context;
    public static String DATE_PASSING="102010";
    List<Order> namesList;
    int recource;
    List<Double> searchOrders;
    List<Double> vendorsOrders;
    List<Double> urgentOrders;
    double total;
    double vendorsTotal;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_AS_A_KEY;


    public DatesListAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context=context;
        this.namesList =objects;
        this.recource=resource;
        namesList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        LayoutInflater inflater=context.getLayoutInflater();
        final View view=  inflater.inflate(recource,parent,false);

        searchOrders=new ArrayList<>();
        vendorsOrders=new ArrayList<>();
        urgentOrders=new ArrayList<>();
        TextView textView3=view.findViewById(R.id.NameIdForListItem);
        String day=namesList.get(position).getResponse();
        textView3.setText(day);

        return view;}
}

