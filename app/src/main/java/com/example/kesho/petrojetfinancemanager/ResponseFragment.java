package com.example.kesho.petrojetfinancemanager;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResponseFragment extends android.app.Fragment {



    EditText dayId;
    EditText monthId;
    TextView slashId;
    TextView amountCheckerResult;
    TextView amountCheckerTxtView;
    TextView currentAmountCheckerTxtView;
    TextView currentAmountCheckerResult;
    TextView txtViewDelayDate;
    TextView txtViewreceiptDate;
    Button btnSendResponse;
    Button delayOrderbtn;
    Button checkAmountBtn;
    String total2;
    List<Double> searchOrders;
    double editpass;
    DatabaseReference ordersDatabase;
    double sum = 0;

    Query query1;
    Query query2;
    ChildEventListener childEventListener1;
    ChildEventListener childEventListener2;
    DatabaseReference receiptDates;
    boolean delayBtnPressed;
    FragmentTransaction ft;
    FragmentManager fm;
    TextView vendorNameTxtView;
    List<Order> orders=new ArrayList<>();
        public ResponseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_response, container, false);



        dayId=view.findViewById(R.id.dayId);
        amountCheckerTxtView=view.findViewById(R.id.amountCheckerTxtView);
        currentAmountCheckerResult=view.findViewById(R.id.currentAmountCheckerResult);
        currentAmountCheckerTxtView=view.findViewById(R.id.currentAmountCheckerTxtView);
        slashId=view.findViewById(R.id.slashId);
        txtViewreceiptDate=view.findViewById(R.id.txtViewreceiptDate);
        checkAmountBtn=view.findViewById(R.id.checkAmountBtn);
        monthId=view.findViewById(R.id.monthId);
        amountCheckerResult=view.findViewById(R.id.amountCheckerResult);
        final Bundle bundle=getArguments();
        searchOrders=new ArrayList<>();

        amountCheckerTxtView.setVisibility(View.INVISIBLE);
        currentAmountCheckerTxtView.setVisibility(View.INVISIBLE);
        final String string=bundle.getString(OrdersAdapter.CHECK_NUMBER_AS_A_KEY);
delayBtnPressed=false;
        btnSendResponse =view.findViewById(R.id.btnSendResponse);
        delayOrderbtn =view.findViewById(R.id.delayOrderbtn);
              vendorNameTxtView =view.findViewById(R.id.txtviewForResponseForCheckNum);
          final String day=dayId.getText().toString();
       vendorNameTxtView.setText(" رد علي طلب استلام شيك رقم : "+ string + "  بقيمة  " + bundle.getString(OrdersAdapter.AMOUNT_PASSING)  );

        DateFormat df2 = new SimpleDateFormat("yyyy");
        final String now2 = df2.format(new Date());
     //   monthId.setText(now2);
        final DateFormat df3= new SimpleDateFormat("MMM");

        btnSendResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayId.getText().toString().length()==2&& monthId.getText().toString().length()==2) {

                    if (btnSendResponse.getText().toString().contains("تم")) {
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("تأكيد الارسال")
                                .setMessage("هل تريد تأكيد تحديد هذا الموعد للأستلام")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses").child(string);

                                        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
                                        String now = df.format(new Date());


                                        String slash = slashId.getText().toString();
                                        final String day = dayId.getText().toString();
                                        String month = monthId.getText().toString();
                                        final String receiptDate = month + slash + day;

//                                        String now3 = df3.format(monthId.getText().toString());

                                        ordersDatabase.child("response").setValue(day + "/" + monthId.getText().toString() + "/" + now2);
                                        ordersDatabase.child("responseMonth").setValue(month + " , " + now2);
                                        ordersDatabase.child("checkNumberForResposeTree").setValue(string);
                                        ordersDatabase.child("checkStatus").setValue("تم الرد");
                                        ordersDatabase.child("dayCredit").setValue("0");
                                        ordersDatabase.child("responseDate").setValue(now);
                                        ordersDatabase.child("vendorRequest").setValue("vendorRequest");
                                        ordersDatabase.child("effectOfChange").getRef().setValue("vendor");



                                        Toast.makeText(getActivity(), "تم ارسال الرد", Toast.LENGTH_SHORT).show();
                                        // OrdersAdapter adapter=new OrdersAdapter(getActivity(),R.layout.order_item_layout,orders);
                                        query1 = FirebaseDatabase.getInstance().getReference().child("orders").orderByChild("checkNumber").
                                                equalTo(string);

                                        childEventListener1 = query1.addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                ordersDatabase.child("name").setValue(dataSnapshot.child("name").getValue());
                                                ordersDatabase.child("vendor").setValue(dataSnapshot.child("vendor").getValue());
                                                ordersDatabase.child("Amount").setValue(dataSnapshot.child("Amount").getValue());
                                                dataSnapshot.child("checkStatus").getRef().removeValue();
                                                dataSnapshot.child("checkNumber").getRef().removeValue();
                                                dataSnapshot.child("Amount").getRef().removeValue();
                                                dataSnapshot.child("name").getRef().removeValue();
                                                dataSnapshot.child("vendor").getRef().removeValue();
                                                dataSnapshot.child("orderDate").getRef().removeValue();


                                                try {
                                                    getFragmentManager().popBackStackImmediate();
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


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"يرجي كتابة التاريخ علي رقمين مثال 01  وليس   1 ",Toast.LENGTH_LONG).show();
                }
            }




        });



        delayOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dayId.getText().toString().length()==2&& monthId.getText().toString().length()==2) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("سيتم التأجيل")
                            .setMessage("تأكيد تحديد هذا الموعد للتأجيل")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses").child(string);

                                    DateFormat df = new SimpleDateFormat("MMM d, yyyy");
                                    String now = df.format(new Date());
                                    String slash = slashId.getText().toString();
                                    final String day = dayId.getText().toString();
                                    String month = monthId.getText().toString();
                                    final String delayDate = month + slash + day;

                                    ordersDatabase.child("delayMSG").setValue(day + "/" + month + "/" + now2);
                                    ordersDatabase.child("checkNumberForResposeTree").setValue(string);
                                    ordersDatabase.child("checkStatus").setValue("تم تأجيل الرد");
                                    ordersDatabase.child("delayDate").setValue(now);


                                    Toast.makeText(getActivity(), "تم تاجيل الرد", Toast.LENGTH_SHORT).show();
                                    // OrdersAdapter adapter=new OrdersAdapter(getActivity(),R.layout.order_item_layout,orders);

                                    DatabaseReference orderBD = FirebaseDatabase.getInstance().getReference().child("orders");
                                    childEventListener2 = orderBD.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                                            try {
                                                if (dataSnapshot.child("checkNumber").getValue().toString().equals(string)) {

                                                    ordersDatabase.child("name").setValue(dataSnapshot.child("name").getValue());
                                                    ordersDatabase.child("vendor").setValue(dataSnapshot.child("vendor").getValue());
                                                    ordersDatabase.child("Amount").setValue(dataSnapshot.child("Amount").getValue());
                                                    dataSnapshot.child("checkStatus").getRef().removeValue();
                                                    dataSnapshot.child("checkNumber").getRef().removeValue();
                                                    dataSnapshot.child("Amount").getRef().removeValue();
                                                    dataSnapshot.child("name").getRef().removeValue();
                                                    dataSnapshot.child("vendor").getRef().removeValue();
                                                    dataSnapshot.child("orderDate").getRef().removeValue();
                                                    delayBtnPressed = false;

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            try {
                                                getFragmentManager().popBackStackImmediate();
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


                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }else {
                    Toast.makeText(getActivity(),"يرجي كتابة التاريخ علي رقمين مثال 01  وليس   1 ",Toast.LENGTH_LONG).show();
                }
            }
        });
        checkAmountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayId.getText().toString().length()==2&& monthId.getText().toString().length()==2) {

                    try {
                    searchOrders.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String day = dayId.getText().toString();
                amountCheckerTxtView.setVisibility(View.VISIBLE);
                    currentAmountCheckerTxtView.setVisibility(View.VISIBLE);
                final String string1 = day + "/" + monthId.getText().toString() + "/" + now2;

                ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses");
                ordersDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            Order order = dataSnapshot1.getValue(Order.class);


                            try {
                                if (string1.equals(order.getResponse())) {

                                    try {
                                        editpass = Double.parseDouble(order.getAmount().toString());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }

                                    searchOrders.add(editpass);

    //
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            sum = 0;

                            for (Double i : searchOrders)
                                sum = sum + i;
                            double editpass = 0;
                            try {
                                editpass = 0;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                editpass = 0;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                editpass = Double.parseDouble(bundle.getString(OrdersAdapter.AMOUNT_PASSING));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            double d = sum + editpass;
                            total2 = new Double(d).toString();
                            String currentAmountStr = new Double(sum).toString();
                            String firstNumberAsString = String.format ("%.0f", sum);
                            String firstNumberAsString2 = String.format ("%.0f", d);


                            currentAmountCheckerResult.setText(firstNumberAsString);
                            amountCheckerResult.setText(firstNumberAsString2);


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                }else {
                    Toast.makeText(getActivity(),"يرجي كتابة التاريخ علي رقمين مثال 01  وليس   1 ",Toast.LENGTH_LONG).show();
                }
            }

        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            query1.removeEventListener(childEventListener1);
            query2.removeEventListener(childEventListener2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            query1.removeEventListener(childEventListener1);
            query2.removeEventListener(childEventListener2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
