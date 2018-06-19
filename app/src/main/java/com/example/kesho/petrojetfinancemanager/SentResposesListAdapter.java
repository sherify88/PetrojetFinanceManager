package com.example.kesho.petrojetfinancemanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kesho on 3/22/2018.
 */

public class SentResposesListAdapter extends ArrayAdapter<Order> {

    Activity context;
    List<Order> orderList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_AS_A_KEY;
    public static String CHECK_NUMBER_EDIT_RESPONSE_PASSING="0001";
    public static String AMOUNT_PASSING="0002";


    public SentResposesListAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
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

        TextView textView7=view.findViewById(R.id.showDelayMSG);
        textView7.setText(orderList.get(position).getDelayMSG());

        TextView textView9=view.findViewById(R.id.showDelayResponse);
        textView9.setText(orderList.get(position).getDelayDate());

        TextView textView=view.findViewById(R.id.checkAmountResponseList);
        TextView textView8=view.findViewById(R.id.showResponseFromDB);
        textView8.setText(orderList.get(position).getResponseDate());
        textView.setText(orderList.get(position).getAmount());
        TextView textView2=view.findViewById(R.id.checkNoList);
        textView2.setText(orderList.get(position).getCheckNumberForResposeTree());
        Button editResponseBtn=view.findViewById(R.id.editResponseBtn);
        editResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditResponseFragment fragment = new EditResponseFragment();
                Bundle bundle=new Bundle();
                bundle.putString(CHECK_NUMBER_EDIT_RESPONSE_PASSING, orderList.get(position).getCheckNumberForResposeTree());
                bundle.putString(AMOUNT_PASSING, orderList.get(position).getAmount());
                fm = context.getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                fragment.setArguments(bundle);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        TextView textView3=view.findViewById(R.id.docpoResponseList);
        textView3.setText(orderList.get(position).getName());
 TextView textView4=view.findViewById(R.id.checkStatusIdTxtList);
        try {
            textView4.setText(orderList.get(position).getResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }


notifyDataSetChanged();
        return view;}
    }
