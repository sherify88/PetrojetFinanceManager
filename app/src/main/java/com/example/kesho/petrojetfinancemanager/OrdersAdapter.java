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

/**
 * Created by kesho on 3/11/2018.
 */

public class OrdersAdapter extends ArrayAdapter<Order> {


    DatabaseReference ordersDatabase;

    Activity context;
    List<Order> orderList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_AS_A_KEY="keyNum";
    public static String AMOUNT_PASSING="amountKey";
public static String rrrrrr;


    public OrdersAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
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

        TextView textView=view.findViewById(R.id.checkamount);
        final TextView orderDateCode=view.findViewById(R.id.orderDateCode);
        orderDateCode.setText(orderList.get(position).getOrderDate());

        final TextView viewDelayMsgCode=view.findViewById(R.id.viewDelayMsgCode);
        final TextView delayMsgDate=view.findViewById(R.id.delayMsgDate);
        textView.setText(orderList.get(position).getAmount());
        TextView textView2=view.findViewById(R.id.checkNo);
        textView2.setText(orderList.get(position).getCheckNumber());


        TextView textView3=view.findViewById(R.id.docpo);

        textView3.setText(orderList.get(position).getName());



            rrrrrr=orderList.get(position).getName();

       final Button btnGoToSendMsgFrag=view.findViewById(R.id.responseConfirmationBtnId);

        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("responses");
        Query query=ordersDatabase.orderByChild("checkStatus").equalTo("تم تأجيل الرد");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                try {
                    if (dataSnapshot.child("checkNumberForResposeTree").getValue().toString().equals(orderList.get(position).getCheckNumber())){

                        btnGoToSendMsgFrag.setText("طلب مؤجل");


    //                   DatabaseReference ordersDatabase2 = FirebaseDatabase.getInstance().getReference().child("responses").child(orderList.get(position).getCheckNumber());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
  Query query2=ordersDatabase.orderByChild("checkStatus").equalTo("تم تنفيذ الطلب مرة اخري");
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                try {
                    if (dataSnapshot.child("checkNumberForResposeTree").getValue().toString().equals(orderList.get(position).getCheckNumber())){

                        btnGoToSendMsgFrag.setText("اعادة طلب مؤجل");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        DatabaseReference ordersDatabase2 = FirebaseDatabase.getInstance().getReference().child("responses");

        Query query3=ordersDatabase2.orderByChild("checkStatus").equalTo("تم تنفيذ الطلب مرة اخري");
        query3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot.child("checkNumberForResposeTree").getValue().toString().equals(orderList.get(position).getCheckNumber())){

                    viewDelayMsgCode.setText(dataSnapshot.child("delayMSG").getValue().toString());
                    delayMsgDate.setText(dataSnapshot.child("delayDate").getValue().toString());
                }
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


        btnGoToSendMsgFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {





                        ResponseFragment fragment = new ResponseFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(CHECK_NUMBER_AS_A_KEY, orderList.get(position).getCheckNumber());
                        bundle.putString(AMOUNT_PASSING, orderList.get(position).getAmount());
                        fm = context.getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, fragment);
                        fragment.setArguments(bundle);
                        ft.addToBackStack(null);
                        ft.commit();

                    } catch(Exception e){
                        e.printStackTrace();
                    }


            }
        });







        return view;




    }

}
