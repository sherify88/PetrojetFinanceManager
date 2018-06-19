package com.example.kesho.petrojetfinancemanager;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.res.Resources;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsEachDayResponsesListFragment extends android.app.Fragment {
    private DatabaseReference mDatabase;
    ValueEventListener valueEventListener;
    private DatabaseReference ordersDatabase;
    TextView vendorTitleChecks;
    double total;
    TextView totalAmount;
    TextView actualTotal;
    TextView creditResult;
    TextView resultExpression;
    Double creditResultDbl;
    ImageView imageView;
    ListView listView;
    List<Order> orders;
    List<Double> searchOrders;
    List<Double> actualReceived;
    VendorsChecksByDaysAdapter arrayAdapter;
    Order order;
    Button searchBtn;
    Button computeCreditResultBtn;
    String total2;
    String total3;
    EditText searchEditText;
    TextView dayCreditEditText;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    double editpass;
    double editpass2;
    static AlertDialog dialog;

    public VendorsEachDayResponsesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_each_day_checks_list, container, false);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("responses");
        orders=new ArrayList<>();
        searchOrders=new ArrayList<>();
        actualReceived=new ArrayList<>();
        listView=view.findViewById(R.id.eachDayList);
        actualTotal=view.findViewById(R.id.actualTotal);
        actualTotal.setVisibility(View.INVISIBLE);
        actualTotal.setVisibility(View.GONE);
        resultExpression=view.findViewById(R.id.resultExpression);
        creditResult=view.findViewById(R.id.creditResult);
        computeCreditResultBtn=view.findViewById(R.id.computeCreditResultBtn);
        dayCreditEditText=view.findViewById(R.id.dayCreditEditText);
        totalAmount=view.findViewById(R.id.totalAmount);
        searchBtn=view.findViewById(R.id.btnSearchBycheckNumberResponse);
        searchEditText=view.findViewById(R.id.searchEditTextResponse);

        final Bundle bundle=getArguments();



        valueEventListener=mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                try {
                    arrayAdapter.clear();
                    orders.clear();
                    searchOrders.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){



                    Order order =dataSnapshot1.getValue(Order.class);

                    if (bundle.getString(CreditDailyComputingFragment.DATE_PASSING).equals(order.getResponse())
                            &&
                            !dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {


                        orders.add(order);


                    }


                }

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Order order = dataSnapshot1.getValue(Order.class);


                    try {
                        if (dataSnapshot1.child("response").getValue().toString().
                                equals(bundle.getString(CreditDailyComputingFragment.DATE_PASSING))) {
                            try {
                                total = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            searchOrders.add(total);

                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    double sum = 0;

                    for (Double i : searchOrders) {
                        sum = sum + i;

                        String firstNumberAsString = String.format("%.0f", sum);

                        totalAmount.setText(firstNumberAsString);
                    }

                }

                arrayAdapter=new VendorsChecksByDaysAdapter(getActivity(),R.layout.response_list_item, orders);
                listView.setAdapter(arrayAdapter);







            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query responsesDB2 = FirebaseDatabase.getInstance().getReference().child("responses").orderByChild("response").
                equalTo(bundle.getString(CreditDailyComputingFragment.DATE_PASSING));
        responsesDB2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                dataSnapshot.child("effectOfChange").getRef().setValue("manager");

                double totalDouble = 0;
                try {
                    totalDouble = Double.parseDouble(totalAmount.getText().toString() );
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double creditDouble = 0;
                try {
                    try {
                        if (dataSnapshot.child("dayCredit").exists()){
                            creditDouble = Double.parseDouble(dataSnapshot.child("dayCredit").getValue().toString());}
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double dayNetResultDouble=creditDouble-totalDouble;
                String firstNumberAsString = String.format ("%.0f", dayNetResultDouble);

                dataSnapshot.child("dayNetResult").getRef().setValue(firstNumberAsString);
                dataSnapshot.child("typeOfResult").getRef().setValue(resultExpression.getText().toString());
                dataSnapshot.child("dayTotal").getRef().setValue(totalAmount.getText().toString());

                //   dataSnapshot.child("dayCredit").getRef().setValue(dataSnapshot.child("dayCredit").getValue().toString());



                try {
                    dayCreditEditText.setText( dataSnapshot.child("dayCredit").getValue().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Double dayCredit = Double.parseDouble(dataSnapshot.child("dayCredit").getValue().toString());
                    Double totalChecksAmount = Double.parseDouble(totalAmount.getText().toString());
                    creditResultDbl = dayCredit-totalChecksAmount;
                    String firstNumberAsString2 = String.format ("%.0f", creditResultDbl);

                    creditResult.setText(firstNumberAsString2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (creditResult.getText().toString().contains("-")){

                    try {
                        try {
                            if (creditResult.isInLayout()){
                                creditResult.setTextColor(getResources().getColor(R.color.openOrderMarkBtn));}
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }

                    resultExpression.setText("عجز");
                    resultExpression.setTextColor(getResources().getColor(R.color.openOrderMarkBtn));


                }else {
                    try {
                        if (creditResult.isInLayout()){
                            creditResult.setTextColor(getResources().getColor(R.color.darkGreen));}
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    resultExpression.setText("فائض");
                    if (resultExpression.isInLayout()){
                        resultExpression.setTextColor(getResources().getColor(R.color.darkGreen));}

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

             return view;


    }


    @Override
    public void onPause() {
        super.onPause();
        mDatabase.removeEventListener(valueEventListener);
    }
}
