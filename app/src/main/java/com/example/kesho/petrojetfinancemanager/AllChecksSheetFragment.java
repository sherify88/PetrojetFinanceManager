package com.example.kesho.petrojetfinancemanager;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllChecksSheetFragment extends android.app.Fragment {

   LineGraphSeries<DataPoint> series;
    TextView januaryDp;
    TextView februaryDp;
    TextView marchDp;
    TextView aprilDp;
    TextView mayDp;
    TextView juneDp;
    TextView julyDp;
    TextView augustDp;
    TextView septemberDp;
    TextView octoberDp;
    TextView novemberDp;
    TextView decemberDp;
 TextView januaryTotal;
    TextView februaryTotal;
    TextView marchTotal;
    TextView aprilTotal;
    TextView mayTotal;
    TextView juneTotal;
    TextView julyTotal;
    TextView augustTotal;
    TextView septemberTotal;
    TextView octoberTotal;
    TextView novemberTotal;
    TextView decemberTotal;

    String total2;
    List<Double> janList;
    List<Double> febList;
    List<Double> marchList;
    List<Double> aprilList;
    List<Double> mayList;
    List<Double> juneList;
    List<Double> julyList;
    List<Double> augustList;
    List<Double> septemberList;
    List<Double> octoberList;
    List<Double> novemberList;
    List<Double> decemberList;
  List<Double> totaljanList;
    List<Double> totalfebList;
    List<Double> totalmarchList;
    List<Double> totalaprilList;
    List<Double> totalmayList;
    List<Double> totaljuneList;
    List<Double> totaljulyList;
    List<Double> totalaugustList;
    List<Double> totalseptemberList;
    List<Double> totaloctoberList;
    List<Double> totalnovemberList;
    List<Double> totaldecemberList;

    double janDouble;
    double febDouble;
    double marchDouble;
    double aprilDouble;
    double mayDouble;
    double juneDouble;
    double julyDouble;
    double augustDouble;
    double septemberDouble;
    double octoberDouble;
    double novemberDouble;
    double decemberDouble;
 double totaljanDouble;
    double  totalfebDouble;
    double  totalmarchDouble;
    double  totalaprilDouble;
    double  totalmayDouble;
    double totaljuneDouble;
    double totaljulyDouble;
    double totalaugustDouble;
    double totalseptemberDouble;
    double totaloctoberDouble;
    double totalnovemberDouble;
    double totaldecemberDouble;

    DatabaseReference ordersDatabase;
    double sum = 0;

    public AllChecksSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_checks_sheet, container, false);


        final GraphView graph=view.findViewById(R.id.graphId);
        series=new LineGraphSeries<>();
        januaryDp = view.findViewById(R.id.januaryDp);
        februaryDp = view.findViewById(R.id.februaryDp);
        marchDp = view.findViewById(R.id.marchDp);
        aprilDp = view.findViewById(R.id.aprilDp);
        mayDp = view.findViewById(R.id.mayDp);
        juneDp = view.findViewById(R.id.juneDp);
        julyDp = view.findViewById(R.id.julyDp);
        augustDp = view.findViewById(R.id.augustDp);
        septemberDp = view.findViewById(R.id.septemberDp);
        octoberDp = view.findViewById(R.id.octoberDp);
        novemberDp = view.findViewById(R.id.novemberDp);
        decemberDp = view.findViewById(R.id.decemberDp);
  januaryTotal = view.findViewById(R.id.januaryTotal);
        februaryTotal = view.findViewById(R.id.februaryTotal);
        marchTotal = view.findViewById(R.id.marchTotal);
        aprilTotal = view.findViewById(R.id.aprilTotal);
        mayTotal = view.findViewById(R.id.mayTotal);
        juneTotal = view.findViewById(R.id.juneTotal);
        julyTotal = view.findViewById(R.id.julyTotal);
        augustTotal = view.findViewById(R.id.augustTotal);
        septemberTotal = view.findViewById(R.id.septemberTotal);
        octoberTotal = view.findViewById(R.id.octoberTotal);
        novemberTotal = view.findViewById(R.id.novemberTotal);
        decemberTotal = view.findViewById(R.id.decemberTotal);
        janList=new ArrayList<>();
        febList=new ArrayList<>();
        marchList=new ArrayList<>();
        aprilList=new ArrayList<>();
      mayList=new ArrayList<>();
        juneList=new ArrayList<>();
        julyList=new ArrayList<>();
      augustList=new ArrayList<>();
        septemberList=new ArrayList<>();
        octoberList=new ArrayList<>();
       novemberList=new ArrayList<>();
       decemberList=new ArrayList<>();

        totaljanList=new ArrayList<>();
        totalfebList=new ArrayList<>();
        totalmarchList=new ArrayList<>();
        totalaprilList=new ArrayList<>();
        totalmayList=new ArrayList<>();
        totaljuneList=new ArrayList<>();
        totaljulyList=new ArrayList<>();
        totalaugustList=new ArrayList<>();
        totalseptemberList=new ArrayList<>();
        totaloctoberList=new ArrayList<>();
        totalnovemberList=new ArrayList<>();
        totaldecemberList=new ArrayList<>();


        final Dialog dialog;


        dialog = new SpotsDialog(getActivity());
        dialog.show();


        ordersDatabase = FirebaseDatabase.getInstance().getReference().child("allChecks");
        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                janList.clear();
                totaljanList.clear();

                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                   // janList.clear();


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                        if (order.getCheckDate().contains("Jan")) {

                            if (  !order.getPlant().contains("مص")){

                                try {
                                    totaljanDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                totaljanList.add(totaljanDouble);


                                if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {


                                try {
                                    janDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                janList.add(janDouble);
                            }
                            }



                        }

                }
                for (Double i : janList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    januaryDp.setText(firstNumberAsString);

                }
                    sum=0;

                    for (Double i : totaljanList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                        String firstNumberAsString = String.format ("%.0f", sum);

                        januaryTotal.setText(firstNumberAsString);
                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                febList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                        if (order.getCheckDate().contains("Feb")) {
                            if (  !order.getPlant().contains("مص")){

                                try {
                                    totalfebDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                totalfebList.add(totalfebDouble);

                                if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    febDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                febList.add(febDouble);
                            }}



                        }


                }
                for (Double i : febList) {

                    sum = sum + i;
                   // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    februaryDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totalfebList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    februaryTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                marchList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                        if (order.getCheckDate().contains("Mar")) {
                            if (!order.getPlant().contains("مص")) {
                                try {
                                    totalmarchDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                totalmarchList.add(totalmarchDouble);


                                if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                    try {
                                        marchDouble = Double.parseDouble(order.getAmount().toString());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }

                                    marchList.add(marchDouble);
                                }
                            }


                        }


                }
                for (Double i : marchList) {

                    sum = sum + i;
                   // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    marchDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totalmarchList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    marchTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                aprilList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                        if (order.getCheckDate().contains("Apr")) {
                            if (!order.getPlant().contains("مص")) {

                                try {
                                    totalaprilDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                totalaprilList.add(totalaprilDouble);

                                if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                    try {
                                        aprilDouble = Double.parseDouble(order.getAmount().toString());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }

                                    aprilList.add(aprilDouble);
                                }

                            }

                        }


                }
                for (Double i : aprilList) {

                    sum = sum + i;
                   // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    aprilDp.setText(firstNumberAsString);
                }

                sum=0;
                for (Double i : totalaprilList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();
//                   double y=1000.00;
//
//                    series.appendData(new DataPoint(sum,y),true,totalaprilList.size());

                    String firstNumberAsString = String.format ("%.0f", sum);

                    aprilTotal.setText(firstNumberAsString);
                   // Toast.makeText(getActivity(),januaryTotal.getText().toString(),Toast.LENGTH_LONG).show();


                }
               // graph.addSeries(series);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mayList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("may")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totalmayDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totalmayList.add(totalmayDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    mayDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                mayList.add(mayDouble);
                            }}



                    }


                }
                for (Double i : mayList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    mayDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totalmayList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    mayTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                juneList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("jun")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totaljuneDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totaljuneList.add(totaljuneDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    juneDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                juneList.add(juneDouble);
                            }}



                    }


                }
                for (Double i : juneList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    juneDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totaljuneList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    juneTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                julyList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("jul")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totaljulyDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totaljulyList.add(totaljulyDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    julyDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                julyList.add(julyDouble);
                            }}



                    }


                }
                for (Double i : julyList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    julyDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totaljulyList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    julyTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                augustList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("aug")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totalaugustDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totalaugustList.add(totalaugustDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    augustDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                augustList.add(augustDouble);
                            }}



                    }


                }
                for (Double i : augustList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    augustDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totalaugustList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    augustTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                septemberList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("sep")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totalseptemberDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totalseptemberList.add(totalseptemberDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    septemberDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                septemberList.add(septemberDouble);
                            }}



                    }


                }
                for (Double i : septemberList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    septemberDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totalseptemberList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    septemberTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                octoberList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("oct")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totaloctoberDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totaloctoberList.add(totaloctoberDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    octoberDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                octoberList.add(octoberDouble);
                            }}



                    }


                }
                for (Double i : octoberList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    octoberDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totaloctoberList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    octoberTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                novemberList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("nov")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totalnovemberDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totalnovemberList.add(totalnovemberDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    novemberDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                novemberList.add(novemberDouble);
                            }}



                    }


                }
                for (Double i : novemberList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    novemberDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totalnovemberList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    novemberTotal.setText(firstNumberAsString);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ordersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                decemberList.clear();
                sum=0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    DocUnderReview order = dataSnapshot1.getValue(DocUnderReview.class);


                    if (order.getCheckDate().contains("dec")) {
                        if (  !order.getPlant().contains("مص")){

                            try {
                                totaldecemberDouble = Double.parseDouble(order.getAmount().toString());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            totaldecemberList.add(totaldecemberDouble);

                            if (order.getDocType().contains("تحت")||order.getDocType().contains("ت/")) {

                                try {
                                    decemberDouble = Double.parseDouble(order.getAmount().toString());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }

                                decemberList.add(decemberDouble);
                            }}



                    }


                }
                for (Double i : decemberList) {

                    sum = sum + i;
                    // total2 = new Double(sum).toString();
                    String firstNumberAsString = String.format ("%.0f", sum);

                    decemberDp.setText(firstNumberAsString);
                }
                sum=0;

                for (Double i : totaldecemberList){


                    sum = sum + i;
                    total2 = new Double(sum).toString();

                    String firstNumberAsString = String.format ("%.0f", sum);

                    decemberTotal.setText(firstNumberAsString);
                }

                double editpass1 = 0;
                double editpass2 = 0;
                double editpass3 = 0;
                double editpass4 = 0;
                double editpass5 = 0;
                double editpass6 = 0;
                double editpass7 = 0;
                double editpass8 = 0;
                double editpass9 = 0;
                double editpass10 = 0;
                double editpass11= 0;
                double editpass12= 0;
                try {
                    editpass1 = Double.parseDouble(januaryTotal.getText().toString() );
                    editpass2 = Double.parseDouble(februaryTotal.getText().toString() );
                    editpass3 = Double.parseDouble(marchTotal.getText().toString() );
                    editpass4 = Double.parseDouble(aprilTotal.getText().toString() );
                    editpass5 = Double.parseDouble(mayTotal.getText().toString() );
                    editpass6 = Double.parseDouble(juneTotal.getText().toString() );
                    editpass7 = Double.parseDouble(julyTotal.getText().toString() );
                    editpass8 = Double.parseDouble(augustTotal.getText().toString() );
                    editpass9 = Double.parseDouble(septemberTotal.getText().toString() );
                    editpass10 = Double.parseDouble(octoberTotal.getText().toString() );
                    editpass11= Double.parseDouble(novemberTotal.getText().toString() );
                    editpass12= Double.parseDouble(decemberTotal.getText().toString() );
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                double downpaymentGraph1 = 0;
                double downpaymentGraph2 = 0;
                double downpaymentGraph3 = 0;
                double downpaymentGraph4 = 0;
                double downpaymentGraph5 = 0;
                double downpaymentGraph6 = 0;
                double downpaymentGraph7 = 0;
                double downpaymentGraph8 = 0;
                double downpaymentGraph9 = 0;
                double downpaymentGraph10 = 0;
                double downpaymentGraph11= 0;
                double downpaymentGraph12= 0;
                try {
                    downpaymentGraph1 = Double.parseDouble(januaryDp.getText().toString() );
                    downpaymentGraph2 = Double.parseDouble(februaryDp.getText().toString() );
                    downpaymentGraph3 = Double.parseDouble(marchDp.getText().toString() );
                    downpaymentGraph4 = Double.parseDouble(aprilDp.getText().toString() );
                    downpaymentGraph5 = Double.parseDouble(mayDp.getText().toString() );
                    downpaymentGraph6 = Double.parseDouble(juneDp.getText().toString() );
                    downpaymentGraph7 = Double.parseDouble(julyDp.getText().toString() );
                    downpaymentGraph8 = Double.parseDouble(augustDp.getText().toString() );
                    downpaymentGraph9 = Double.parseDouble(septemberDp.getText().toString() );
                    downpaymentGraph10 = Double.parseDouble(octoberDp.getText().toString() );
                    downpaymentGraph11 = Double.parseDouble(novemberDp.getText().toString() );
                    downpaymentGraph12 = Double.parseDouble(decemberDp.getText().toString() );
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double new1=editpass1/1000000;
                double new2=editpass2/1000000;
                double new3=editpass3/1000000;
                double new4=editpass4/1000000;
                double new5=editpass5/1000000;
                double new6=editpass6/1000000;
                double new7=editpass7/1000000;
                double new8=editpass8/1000000;
                double new9=editpass9/1000000;
                double new10=editpass10/1000000;
                double new11=editpass11/1000000;
                double new12=editpass12/1000000;
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                        new DataPoint(0, 0),
                        new DataPoint(1, editpass1/1000000),
                        new DataPoint(2, editpass2/1000000),
                        new DataPoint(3, editpass3/1000000),
                        new DataPoint(4, editpass4/1000000),
                        new DataPoint(5, editpass5/1000000),
                        new DataPoint(6, editpass6/1000000),
                        new DataPoint(7, editpass7/1000000),
                        new DataPoint(8, editpass8/1000000),
                        new DataPoint(9, editpass9/1000000),
                        new DataPoint(10, editpass10/1000000),
                        new DataPoint(11, editpass11/1000000),
                        new DataPoint(12, editpass12/1000000)

                });   LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {

                        new DataPoint(0, 0),
                        new DataPoint(1, downpaymentGraph1/1000000),
                        new DataPoint(2, downpaymentGraph2/1000000),
                        new DataPoint(3, downpaymentGraph3/1000000),
                        new DataPoint(4, downpaymentGraph4/1000000),
                        new DataPoint(5, downpaymentGraph5/1000000),
                        new DataPoint(6, downpaymentGraph6/1000000),
                        new DataPoint(7, downpaymentGraph7/1000000),
                        new DataPoint(8, downpaymentGraph8/1000000),
                        new DataPoint(9, downpaymentGraph9/1000000),
                        new DataPoint(10, downpaymentGraph10/1000000),
                        new DataPoint(11, downpaymentGraph11/1000000),
                        new DataPoint(12, downpaymentGraph12/1000000),

                });
                graph.getViewport().setScrollable(true);
                graph.isHorizontalScrollBarEnabled();


                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(12);


               //                graph.getViewport().setMinY(2.0);
//                graph.getViewport().setMaxY(15.0);
//
              //  graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setXAxisBoundsManual(true);


                graph.addSeries(series);
                series2.setColor(Color.GREEN);
                graph.addSeries(series2);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return view;
    }

}
