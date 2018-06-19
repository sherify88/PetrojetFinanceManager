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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by kesho on 4/14/2018.
 */

public class EachDayCheckAdapter extends ArrayAdapter<Order> {

    Activity context;
    List<Order> orderList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_AS_A_KEY="1000";
    public static String AMOUNT_PASSING="1001";
    public static String NAME_PASSING="1002";
    public static String VENDOR_PASSING="1003";


    public EachDayCheckAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
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
        editResponseBtn.setText("تم التسليم");
        editResponseBtn.setBackgroundColor(context.getResources().getColor(R.color.green));




        try {
            textView4.setText(orderList.get(position).getResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }

        editResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editResponseBtn.getText().toString().contains("جاري")) {

                    try {


                        EditDatesPageInDaysListFragment fragment = new EditDatesPageInDaysListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(CHECK_NUMBER_AS_A_KEY, orderList.get(position).getCheckNumberForResposeTree());
                        bundle.putString(AMOUNT_PASSING, orderList.get(position).getAmount());
                        bundle.putString(NAME_PASSING, orderList.get(position).getName());
                        bundle.putString(VENDOR_PASSING, orderList.get(position).getVendorId());
                        fm = context.getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, fragment);
                        fragment.setArguments(bundle);
                        ft.addToBackStack(null);
                        ft.commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {}


            }
        });


        Query ordersDatabase2 = FirebaseDatabase.getInstance().getReference().child("vendors").orderByChild("check")
                .equalTo(orderList.get(position).getCheckNumberForResposeTree());
        ordersDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    editResponseBtn.setText("جاري التسليم");
                editResponseBtn.setBackgroundColor(context.getResources().getColor(R.color.pendingForReceiveing));

                DatabaseReference ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses").
                        child(orderList.get(position).getCheckNumberForResposeTree());


                    ordersDatabase.child("receiveCheckStatus").getRef().setValue("جاري التسليم");

                dialog1.dismiss();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        notifyDataSetChanged();

        return view;}
}
