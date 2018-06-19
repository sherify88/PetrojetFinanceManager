package com.example.kesho.petrojetfinancemanager;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreditDailyComputingFragment extends android.app.Fragment {
    Button computeCreditResultBtn;

    TextView resultExpression;
    EditText searchEditText;
    EditText dayCreditEditText;
    Double creditResultDbl;
    List<Order> namesList;
    List<Double> searchOrders;
    List<Double> vendorsOrders;
    List<Double> urgentOrders;
    SpotsDialog dialog1;
    double total;
    double vendorsTotal;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String DATE_PASSING="1234";
    public CreditDailyComputingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_credit_daily_computing, container, false);
        dialog1=new SpotsDialog(getActivity());
        dialog1.show();
        searchOrders=new ArrayList<>();
        vendorsOrders=new ArrayList<>();
        urgentOrders=new ArrayList<>();
        TextView textView3=view.findViewById(R.id.NameIdForListItem);
        final Bundle bundle=getArguments();
       final String day=bundle.getString(DatesListFragment.DATE_PASSING);
        textView3.setText(day);
        final   TextView totalAmount=view.findViewById(R.id.totalAmount);
        final Button urgentChecksView=view.findViewById(R.id.urgentChecksView);
        final   Button vendorOrdersView=view.findViewById(R.id.vendorOrdersView);
        final   TextView registeredCreditId=view.findViewById(R.id.registeredCreditId);
        resultExpression=view.findViewById(R.id.resultExpression);
        final TextView creditResult=view.findViewById(R.id.creditResult);
        computeCreditResultBtn=view.findViewById(R.id.computeCreditResultBtn);
        dayCreditEditText=view.findViewById(R.id.dayCreditEditText);

        final DatabaseReference responsesDB = FirebaseDatabase.getInstance().getReference().child("responses");
        responsesDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                searchOrders.clear();
                vendorsOrders.clear();
                urgentOrders.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Order order = dataSnapshot1.getValue(Order.class);


                    try {
                        if (dataSnapshot1.child("response").getValue().toString().equals(day)) {
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

                    for (Double i : searchOrders){
                        sum = sum + i;

                        String firstNumberAsString = String.format ("%.0f", sum);

                        totalAmount.setText(firstNumberAsString);}
                    try {
                        if (dataSnapshot1.child("response").getValue().toString().equals(day)&&
                                !dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {
                            try {
                                vendorsTotal = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            vendorsOrders.add(vendorsTotal);

                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    double sum1 = 0;

                    for (Double i : vendorsOrders){
                        sum1 = sum1 + i;

                        String firstNumberAsString = String.format ("%.0f", sum1);

                        vendorOrdersView.setText( "اجمالي شيكات طلبات الموردين   =" + firstNumberAsString );}

                    try {
                        if (dataSnapshot1.child("response").getValue().toString().equals(day)&&
                                dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {
                            try {
                                vendorsTotal = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            urgentOrders.add(vendorsTotal);

                            //
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    double sum2 = 0;

                    for (Double i : urgentOrders){
                        sum2 = sum2 + i;

                        String firstNumberAsString = String.format ("%.0f", sum2);

                        urgentChecksView.setText("اجمالي الشيكات المستعجل عليها   =" + firstNumberAsString);}

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        urgentChecksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditableEachDayChecksListFragment fragment = new EditableEachDayChecksListFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(DATE_PASSING,day);
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                fragment.setArguments(bundle1);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        vendorOrdersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VendorsEachDayResponsesListFragment fragment = new VendorsEachDayResponsesListFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(DATE_PASSING,day);
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                fragment.setArguments(bundle1);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        Query responsesDB2 = FirebaseDatabase.getInstance().getReference().child("responses").orderByChild("response").
                equalTo(day);
        responsesDB2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dialog1.dismiss();


                dataSnapshot.child("effectOfChange").getRef().setValue("manager");

                double totalDouble = Double.parseDouble(totalAmount.getText().toString() );
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
                    registeredCreditId.setText("تم تسجيل رصيد اليوم    = "+ dataSnapshot.child("dayCredit").getValue().toString());
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

        computeCreditResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(dayCreditEditText.getText().toString())||TextUtils.isEmpty(totalAmount.getText().toString())){
                    Toast.makeText(getActivity(),"من فضلك املا الخانات المطلوبة",Toast.LENGTH_SHORT).show();
                }else {
                    final SpotsDialog dialog2=new SpotsDialog(getActivity());

                    dialog2.show();
                    Double dayCredit = Double.parseDouble(dayCreditEditText.getText().toString());
                    Double totalChecksAmount = Double.parseDouble(totalAmount.getText().toString());
                      creditResultDbl = dayCredit-totalChecksAmount;
                    final String dayNetResult = new Double(creditResultDbl).toString();
                    Query responsesDB2 = FirebaseDatabase.getInstance().getReference().child("responses").orderByChild("response").
                            equalTo(day);
                    responsesDB2.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            dialog2.dismiss();
                            dataSnapshot.child("effectOfChange").getRef().setValue("manager");

                            String firstNumberAsString = String.format ("%.0f", creditResultDbl);

                            dataSnapshot.child("dayNetResult").getRef().setValue(firstNumberAsString);
                            dataSnapshot.child("dayCredit").getRef().setValue(dayCreditEditText.getText().toString());
                            dataSnapshot.child("typeOfResult").getRef().setValue(resultExpression.getText().toString());
                            dataSnapshot.child("dayTotal").getRef().setValue(totalAmount.getText().toString());

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



                                    }
                android.app.Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
                if (currentFragment instanceof CreditDailyComputingFragment) {
                    FragmentTransaction fragTransaction =   (getActivity()).getFragmentManager().beginTransaction();
                    fragTransaction.detach(currentFragment);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fragTransaction.attach(currentFragment);
                    fragTransaction.commit();}
            }


        });

        return view;
    }

}
