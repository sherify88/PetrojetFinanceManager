package com.example.kesho.petrojetfinancemanager;


import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersListFragment extends android.app.Fragment {

    private DatabaseReference mDatabase;
    private DatabaseReference ordersDatabase;
    private DatabaseReference namesDB;
    private DatabaseReference ordersDatabaseQuery;
    TextView vendorTitleChecks;
    ImageView imageView;
    ListView listView;
   List<Order> orders;
    List<Order> searchOrders;
    OrdersAdapter arrayAdapter;
    Order order;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    Button searchBtnByVendorName;
    EditText editTxtForSearch;

    TextView openItemsCounterTxtView;
    TextView ordersizetxtview;
    int counter;
    int matchCounter;
    public static String NAME_PASSING;
    public static String COUNTER_STRING;
   public static String NAME_MATCHING="NAME_MATCHING";
    public static String COUNTER_PASSING="COUNTER_PASSING";
  static public boolean ordersOnchildaddedGetBeenCalled;
   static String counterString;
    ValueEventListener valueEventListener;
    public OrdersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_orders_list, container, false);


        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("orders");
        ordersDatabaseQuery= FirebaseDatabase.getInstance().getReference().child("orders");
        searchBtnByVendorName=view.findViewById(R.id.btnSearchByVendorName);
        editTxtForSearch=view.findViewById(R.id.searchEditText);
        listView=view.findViewById(R.id.list_view);
        openItemsCounterTxtView=view.findViewById(R.id.openItemsCounter);
        counter=0;
        orders =new ArrayList<>();
        searchOrders =new ArrayList<>();
        final Bundle bundle=getArguments();
        ordersOnchildaddedGetBeenCalled=false;

        final Dialog dialog;


        dialog = new SpotsDialog(getActivity());
        dialog.show();




       valueEventListener= ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

dialog.dismiss();

                    orders.clear();
                    searchOrders.clear();
                counter=0;
                matchCounter=0;
                if (dataSnapshot.exists()){

                for (final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    orders.size();


dialog.dismiss();

                    try {
                        final Order order = dataSnapshot1.getValue(Order.class);

                        if (dataSnapshot1.child("checkStatus").getValue().toString().equals("مطلوب الرد")||
                                dataSnapshot1.child("checkStatus").getValue().toString().equals("اعادة طلب مؤجل") ){

                            if (bundle.getString(NamesListBegFragment.NAME_BUNDLE).equals(order.getName())){

                                counter++;


                            }
//


                            counterString = new Integer(counter).toString();
                             openItemsCounterTxtView.setText(" الطلبات المفتوحة لهذا المورد هي    :  "+ counterString);

//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final Order order = dataSnapshot1.getValue(Order.class);

                    searchBtnByVendorName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final SpotsDialog dialog1=new SpotsDialog(getActivity());
                            arrayAdapter.clear();
                            orders.clear();
                            searchOrders.clear();
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                    dialog.dismiss();

                                        final Order order = dataSnapshot1.getValue(Order.class);
                                    String checknoStr = dataSnapshot1.child("checkNumber").getValue().toString();

                                    if (checknoStr.contains(editTxtForSearch.getText().toString())){

                                       // Toast.makeText(getActivity(),editTxtForSearch.getText().toString(),Toast.LENGTH_SHORT).show();
                                  if (bundle.getString(NamesListBegFragment.NAME_BUNDLE).equals(order.getName())){

                                      matchCounter++;
                                      searchOrders.add(order);}
                            arrayAdapter=new OrdersAdapter(getActivity(),R.layout.order_item_layout, searchOrders);
                            listView.setAdapter(arrayAdapter);}else{if (matchCounter==0) {

                                    }                                    } if (editTxtForSearch.getText().toString().isEmpty()){
                                        if (bundle.getString(NamesListBegFragment.NAME_BUNDLE).equals(order.getName())){

                                        orders.add(order);
                                            try {
                                                int counter;
                                                counter = 0;
                                                for (int i = 0; i < (orders.size() + 1); i++) {
                                                    if (orders.get(i).getCheckNumber().equals(dataSnapshot1.child("checkNumber").getValue().toString())) {

                                                        counter++;
                                                        if (counter == 2) {

                                                            orders.remove(i);
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }}

                                    }
                                }
                            dialog1.dismiss();
                        }
                    });


                    try {
                        if (bundle.getString(NamesListBegFragment.NAME_BUNDLE).equals(order.getName())){
                            orders.add(order);

                            try {
                                int counter;
                                counter = 0;
                                for (int i = 0; i < (orders.size() + 1); i++) {
                                    if (orders.get(i).getCheckNumber().equals(dataSnapshot1.child("checkNumber").getValue().toString())) {

                                        counter++;
                                        if (counter == 2) {

                                            orders.remove(i);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            ordersOnchildaddedGetBeenCalled=true;}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                    try {
                        arrayAdapter=new OrdersAdapter(getActivity(),R.layout.order_item_layout, orders);
                        listView.setAdapter(arrayAdapter);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }





            }


            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });






        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

    }

}
