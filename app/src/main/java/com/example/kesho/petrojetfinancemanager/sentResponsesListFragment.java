package com.example.kesho.petrojetfinancemanager;


import android.app.AlertDialog;
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
public class sentResponsesListFragment extends android.app.Fragment {
    private DatabaseReference mDatabase;
    ValueEventListener valueEventListener;
    private DatabaseReference ordersDatabase;
    TextView vendorTitleChecks;
    ImageView imageView;
    ListView listView;
    List<Order> orders;
    List<Order> searchOrders;
    SentResposesListAdapter arrayAdapter;
    Order order;
    Button searchBtn;
    EditText searchEditText;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    static AlertDialog dialog;

    public sentResponsesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.sent_responses_list, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("responses");
        orders=new ArrayList<>();
        searchOrders=new ArrayList<>();
        listView=view.findViewById(R.id.sentResponsesList);
        searchBtn=view.findViewById(R.id.btnSearchBycheckNumberResponse);
        searchEditText=view.findViewById(R.id.searchEditTextResponse);

        final Bundle bundle=getArguments();

       valueEventListener=mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){



                    Order order =dataSnapshot1.getValue(Order.class);

                    if (bundle.getString(ShowTotalSentListByNameFragment.NAME_PASSING).equals(order.getName())){
                        if (!dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {


                            orders.add(order);
                        }

                        searchBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final SpotsDialog dialog1=new SpotsDialog(getActivity());
                                arrayAdapter.clear();
                                orders.clear();
                                searchOrders.clear();
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                                    final Order order = dataSnapshot1.getValue(Order.class);
                                    String checknoStr = dataSnapshot1.child("checkNumberForResposeTree").getValue().toString();

                                    if (checknoStr.contains(searchEditText.getText().toString())){

                                        // Toast.makeText(getActivity(),editTxtForSearch.getText().toString(),Toast.LENGTH_SHORT).show();
                                        if (bundle.getString(ShowTotalSentListByNameFragment.NAME_PASSING).equals(order.getName())){
                                            if (!dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {

                                            searchOrders.add(order);}}
                                        arrayAdapter=new SentResposesListAdapter(getActivity(),R.layout.response_list_item, searchOrders);
                                        listView.setAdapter(arrayAdapter);}if (searchEditText.getText().toString().isEmpty()){
                                        if (bundle.getString(NamesListBegFragment.NAME_BUNDLE).equals(order.getName())){
                                            if (!dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {

                                            orders.add(order);}}

                                    }
                                }
                                dialog1.dismiss();
                            }
                        });



                    }

                }

                arrayAdapter=new SentResposesListAdapter(getActivity(),R.layout.response_list_item, orders);
                listView.setAdapter(arrayAdapter);







            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
