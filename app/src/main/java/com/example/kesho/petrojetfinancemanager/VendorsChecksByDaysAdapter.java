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
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by kesho on 5/4/2018.
 */

public class VendorsChecksByDaysAdapter extends ArrayAdapter<Order> {

    Activity context;
    List<Order> orderList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_AS_A_KEY="1000";
    public static String AMOUNT_PASSING="1001";
    public static String NAME_PASSING="1002";
    public static String VENDOR_PASSING="1003";


    public VendorsChecksByDaysAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context=context;
        this.orderList =objects;
        this.recource=resource;
        orderList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        LayoutInflater inflater=context.getLayoutInflater();
        final View view=  inflater.inflate(recource,parent,false);

        final SpotsDialog dialog1=new SpotsDialog(context);

        TextView textView=view.findViewById(R.id.checkAmountResponseList);
        TextView textView8=view.findViewById(R.id.showResponseFromDB);
        textView8.setText(orderList.get(position).getResponseDate());
        textView.setText(orderList.get(position).getAmount());
        TextView textView2=view.findViewById(R.id.checkNoList);
        textView2.setText(orderList.get(position).getCheckNumberForResposeTree());

        TextView textView3=view.findViewById(R.id.docpoResponseList);
        final Button editResponseBtn=view.findViewById(R.id.editResponseBtn);
        textView3.setText(orderList.get(position).getName());
        TextView textView4=view.findViewById(R.id.checkStatusIdTxtList);

        editResponseBtn.setVisibility(View.INVISIBLE);
        editResponseBtn.setVisibility(View.GONE);


        return view;}
}
