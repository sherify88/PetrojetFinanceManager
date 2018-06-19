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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by kesho on 4/21/2018.
 */

public class ManagersOrdersAdapter extends ArrayAdapter<DocUnderReview> {


    Activity context;
    List<DocUnderReview> documentList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_PASSING;


    public ManagersOrdersAdapter(@NonNull Activity context, int resource, @NonNull List<DocUnderReview> objects) {
        super(context, resource, objects);
        this.context=context;
        this.documentList =objects;
        this.recource=resource;
        documentList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(recource, parent, false);

        final Button managerOrderStatusBtnId = view.findViewById(R.id.managerOrderStatusBtnId);
        TextView textView = view.findViewById(R.id.checkamount);
        textView.setText(documentList.get(position).getAmount());
        TextView nameCode = view.findViewById(R.id.nameCode);
        nameCode.setText(documentList.get(position).getName());

        TextView textView3 = view.findViewById(R.id.docpo);
        textView3.setText(documentList.get(position).getPo());
             TextView keyCode=view.findViewById(R.id.keyCode);
        keyCode.setText(documentList.get(position).Key);
        TextView docStatusCode=view.findViewById(R.id.docStatusCode);
        docStatusCode.setText(documentList.get(position).docStatus);
        TextView backDate=view.findViewById(R.id.backDate);
        TextView backDateCode=view.findViewById(R.id.backDateCode);
        backDateCode.setText(documentList.get(position).backDate);

        TextView textView2=view.findViewById(R.id.checkNo);
        textView2.setText(documentList.get(position).getCheck());
 TextView checkDate=view.findViewById(R.id.checkDate);
        checkDate.setText(documentList.get(position).getCheckDate());

        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("managerOrders");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


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



        return view; }
}