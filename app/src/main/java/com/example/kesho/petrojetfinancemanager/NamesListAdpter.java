package com.example.kesho.petrojetfinancemanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by kesho on 3/23/2018.
 */

public class NamesListAdpter extends ArrayAdapter<Order> {

    private DatabaseReference ordersDatabase;
    FragmentActivity activity= (FragmentActivity) getContext();
    int counter;
    Activity context;
    List<Order> namesList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_AS_A_KEY;


    public NamesListAdpter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context=context;
        this.namesList =objects;
        this.recource=resource;
        namesList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {



counter=0;
        LayoutInflater inflater=context.getLayoutInflater();
        final View view=  inflater.inflate(recource,parent,false);

        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("orders");

        TextView textView3=view.findViewById(R.id.NameIdForListItem);
        textView3.setText(namesList.get(position).getName());


        final TextView nameCounter = view.findViewById(R.id.nameCounter);

        ordersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                counter=0;

                try {
                    if (dataSnapshot.child("name").getValue().toString().equals(namesList.get(position).getName())){

                        counter++;
                        String total2= new Integer(counter).toString();



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



        Query ordersDatabase3 = FirebaseDatabase.getInstance().getReference().child("orders").
                orderByChild("name").equalTo(namesList.get(position).getName());

        ordersDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                    String total2= new Double(dataSnapshot.getChildrenCount()).toString();
                double editpass = Double.parseDouble(total2);

                String firstNumberAsString = String.format ("%.0f", editpass);



                nameCounter.setText(firstNumberAsString);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;}
}

