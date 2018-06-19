package com.example.kesho.petrojetfinancemanager;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
public class NamesListBegFragment extends android.app.Fragment {


    private DatabaseReference mDatabase;
    ValueEventListener valueEventListener;
    ListView listView;
    TextView stringTrial;
    List<Order> namesList;
    List<Order> searchNames;
    NamesListAdpter arrayAdapter;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    public static String NAME_BUNDLE;
    static AlertDialog dialog;
    int totalCounter;
    int nameCounter;
    EditText searchNameEditTxt;
    Button btnTotalSearch;
    String totalCounterString;
    TextView txtViewShowTotalOpenOrders;
    int openOrdersCounter;
    public int matchCount;

    public NamesListBegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_names_list_beg, container, false);
        searchNameEditTxt = view.findViewById(R.id.totalSearchEditText);
        btnTotalSearch = view.findViewById(R.id.btnTotalSearchByVendorName);
        txtViewShowTotalOpenOrders = view.findViewById(R.id.totalOpenItemsCounter);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("orders");
        namesList = new ArrayList<>();
        searchNames = new ArrayList<>();
        listView = view.findViewById(R.id.names_list_id);
        openOrdersCounter = 0;
        nameCounter=0;

              dialog = new SpotsDialog(getActivity());
        dialog.show();



        valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                try {
                    arrayAdapter.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                openOrdersCounter = 0;
                nameCounter=0;
                searchNames.clear();
                namesList.clear();

                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    final Order name = dataSnapshot1.getValue(Order.class);

                    try {
                        if (dataSnapshot1.child("checkStatus").getValue().toString().equals("مطلوب الرد")||
                                dataSnapshot1.child("checkStatus").getValue().toString().equals("اعادة طلب مؤجل")) {

                            openOrdersCounter++;

                            totalCounterString = new Integer(openOrdersCounter).toString();
                            txtViewShowTotalOpenOrders.setText(" اجمالي الطلبات المفتوحة هو    :  " + totalCounterString);



                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (name.getName().contains("")){
                            matchCount++;
                            namesList.add(name);

                            try {
                                int counter;
                                counter = 0;
                                for (int i = 0; i < (namesList.size() + 1); i++) {
                                    if (namesList.get(i).getName().equals(dataSnapshot1.child("name").getValue().toString())) {

                                        counter++;
                                        if (counter == 2) {

                                            namesList.remove(i);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            arrayAdapter=new NamesListAdpter(getActivity(),R.layout.names_list, namesList);
                            listView.setAdapter(arrayAdapter);




                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    btnTotalSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String matchCounterStr;
                            final SpotsDialog dialog1=new SpotsDialog(getActivity());
                          //  namesList.clear();
                            searchNames.clear();
                           arrayAdapter.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                dialog.dismiss();
                                final Order order = dataSnapshot1.getValue(Order.class);
                                try {
                                    if (order.getName().contains(searchNameEditTxt.getText().toString())){
                                        matchCount++;
                                            searchNames.add(order);

                                              try {
                                            int counter;
                                            counter = 0;
                                            for (int i = 0; i < (searchNames.size() + 1); i++) {
                                                if (searchNames.get(i).getName().equals(dataSnapshot1.child("name").getValue().toString())) {
                                                 //   matchCount++;
                                                    counter++;
                                                    if (counter == 2) {

                                                        searchNames.remove(i);
                                                    }

                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        arrayAdapter=new NamesListAdpter(getActivity(),R.layout.names_list, searchNames);
                                        listView.setAdapter(arrayAdapter);
                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                OrdersListFragment fragment = new OrdersListFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putString(NAME_BUNDLE, searchNames.get(position).getName());
                                                fm = getFragmentManager();
                                                ft = fm.beginTransaction();
                                                ft.replace(R.id.fragment_container, fragment);
                                                fragment.setArguments(bundle);
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            }

                                        });



                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }



                            }
                            dialog1.dismiss();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OrdersListFragment fragment = new OrdersListFragment();
                    Bundle bundle = new Bundle();
                    try {
                        bundle.putString(NAME_BUNDLE, namesList.get(position).getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    fragment.setArguments(bundle);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });





        return view;


    }


    @Override
    public void onPause() {
        super.onPause();
        mDatabase.removeEventListener(valueEventListener);
    }
}
