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
public class EditResponseFragment extends android.app.Fragment {


    EditText dayId;
    TextView monthId;
    TextView slashId;
    TextView amountCheckerResult;
    TextView amountCheckerTxtView;
    TextView currentAmountCheckerTxtView;
    TextView currentAmountCheckerResult;
    TextView txtViewreceiptDate;
    Button btnSendResponse;
    Button checkAmountBtn;
    String total2;
    List<Double> searchOrders;
    double editpass;

    Query query1;
    Query query2;
    ChildEventListener childEventListener1;
    ChildEventListener childEventListener2;
    DatabaseReference ordersDatabase;
    DatabaseReference receiptDates;
    double sum = 0;
    FragmentTransaction ft;
    FragmentManager fm;
    TextView vendorNameTxtView;
    List<Order> orders=new ArrayList<>();

    public EditResponseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_response, container, false);

        currentAmountCheckerResult=view.findViewById(R.id.currentAmountCheckerResult);
        currentAmountCheckerTxtView=view.findViewById(R.id.currentAmountCheckerTxtView);
        dayId=view.findViewById(R.id.dayId);
        amountCheckerTxtView=view.findViewById(R.id.amountCheckerTxtView);
        slashId=view.findViewById(R.id.slashId);
        txtViewreceiptDate=view.findViewById(R.id.txtViewreceiptDate);
        checkAmountBtn=view.findViewById(R.id.checkAmountBtn);
        monthId=view.findViewById(R.id.monthId);
        amountCheckerResult=view.findViewById(R.id.amountCheckerResult);
        final Bundle bundle=getArguments();
        searchOrders=new ArrayList<>();

        amountCheckerTxtView.setVisibility(View.INVISIBLE);
        currentAmountCheckerTxtView.setVisibility(View.INVISIBLE);
        final String string=bundle.getString(SentResposesListAdapter.CHECK_NUMBER_EDIT_RESPONSE_PASSING);
        btnSendResponse =view.findViewById(R.id.btnSendResponse);
        vendorNameTxtView =view.findViewById(R.id.txtviewForResponseForCheckNum);
        final String day=dayId.getText().toString();
        vendorNameTxtView.setText(" رد علي طلب استلام شيك رقم : "+ string + "  بقيمة  " + bundle.getString(SentResposesListAdapter.AMOUNT_PASSING)  );

        DateFormat df2 = new SimpleDateFormat("yyyy");
        final String now2 = df2.format(new Date());
       // monthId.setText(now2);
//        DateFormat df3= new SimpleDateFormat("MMM");
//        final String now3 = df3.format(new Date());

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


                                        ordersDatabase.child("response").setValue(day + "/" + month + "/" + now2);
                                        ordersDatabase.child("responseMonth").setValue(month + " , " + now2);
                                        ordersDatabase.child("checkNumberForResposeTree").setValue(string);
                                        ordersDatabase.child("dayCredit").setValue("0");
                                        ordersDatabase.child("checkStatus").getRef().setValue("تم تعديل الرد");
                                        ordersDatabase.child("responseDate").setValue(now);
                                        ordersDatabase.child("effectOfChange").getRef().setValue("vendor");

                                        getFragmentManager().popBackStackImmediate();
                                        // OrdersAdapter adapter=new OrdersAdapter(getActivity(),R.layout.order_item_layout,orders);


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    }
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
                    currentAmountCheckerTxtView.setVisibility(View.VISIBLE);
                    amountCheckerTxtView.setVisibility(View.VISIBLE);
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
                                double editpass = Double.parseDouble(bundle.getString(SentResposesListAdapter.AMOUNT_PASSING));
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

