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
public class UnderReviewFragment extends android.app.Fragment {
    private DatabaseReference mDatabase;
    private DatabaseReference ordersDatabase;
    TextView downPaymentCounter;
    TextView kemaCounter;
    TextView rentalCounter;
    TextView transportationCounter;
    TextView mosta5lasCounter;
    TextView inspectionCounter;
    TextView retentionCounter;
    TextView tasweyaWeSarfCounter;
    TextView textResult;
    TextView counterId;
    EditText searchByNameEditText;
    int counter;
    int retentionCount;
    int inspectionCount;
    int rentalCount;
    int tasweyaWeSarfCount;
    int kemaCount;
    int transportationCount;
    int mosta5lasCount;
    int downPaymentCount;
    ImageView imageView;
    ListView listView;
    List<DocUnderReview> documents;
    List<DocUnderReview> searchDocuments;
    EditText searchEditReview;
    Button searchBtn;
    Button btnSearchByName;
    UnderReviewAdapter arrayAdapter;
    String total2;
    String totaltransportationCount;
    String totalretentionCounter;
    String totalrentalCounter;
    String totaltasweyaWeSarfCounter;
    String totalkemaCounter;
    String totalmosta5lasCount;
    String totalinspectionCounter;
    String totalDownPayment;
    DocUnderReview document;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    static AlertDialog dialog;

    public UnderReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_under_review, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("review");
        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("orders");
        final Bundle bundle=getArguments();
               textResult=view.findViewById(R.id.noResults);
        counterId=view.findViewById(R.id.counterId);
        btnSearchByName=view.findViewById(R.id.btnSearchByName);
        searchByNameEditText=view.findViewById(R.id.searchByNameEditText);
        inspectionCounter=view.findViewById(R.id.inspectionCounter);
        tasweyaWeSarfCounter=view.findViewById(R.id.tasweyaWeSarfCounter);
        mosta5lasCounter=view.findViewById(R.id.mosta5lasCounter);
        kemaCounter=view.findViewById(R.id.kemaCounter);
        retentionCounter=view.findViewById(R.id.retentionCounter);
        rentalCounter=view.findViewById(R.id.rentalCounter);
        transportationCounter=view.findViewById(R.id.transportationCounter);
        downPaymentCounter=view.findViewById(R.id.downPaymentCounter);
        listView=view.findViewById(R.id.usnderReviewListViewId);
        documents=new ArrayList<>();
        searchDocuments=new ArrayList<>();
        searchBtn=view.findViewById(R.id.btnSearchByPo);
        searchEditReview=view.findViewById(R.id.searchEditTextReview);
        dialog = new SpotsDialog(getActivity());
        dialog.show();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mosta5lasCount=0;
                transportationCount=0;
                downPaymentCount=0;
                rentalCount=0;
                inspectionCount=0;
                tasweyaWeSarfCount=0;
                kemaCount=0;
                retentionCount=0;
                counter=0;
                dialog.dismiss();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    dialog.dismiss();


                    final DocUnderReview document =dataSnapshot1.getValue(DocUnderReview.class);

                    if (document.getDocNo().contains("N")){

                        if (document.getDocStatus().contains("N")){

                            if (!document.getNotes().contains("موقوف")){

                                if (!document.getDocType().contains("جزئي")) {

                                    if (!document.getDocType().contains("كلي")) {
                                        counter++;

                                        if (document.getDocType().contains("تحت") || document.getDocType().contains("ت/")) {

                                            downPaymentCount++;
                                        }
                                        if (document.getDocType().contains("نقل")) {

                                            transportationCount++;
                                        }
                                        if (document.getDocType().contains("مستخلص")) {

                                            mosta5lasCount++;
                                        }
                                        if (document.getDocType().contains("صرف")) {

                                            tasweyaWeSarfCount++;
                                        }
                                        if (document.getDocType().contains("تفتيش")) {

                                            inspectionCount++;
                                        }
                                        if (document.getDocType().contains("قيمة")) {

                                            kemaCount++;
                                        }
                                        if (document.getDocType().contains("رد")) {

                                            retentionCount++;
                                        }
                                        if (document.getDocType().contains("يجار")) {

                                            rentalCount++;
                                        }


                                        total2 = new Integer(counter).toString();
                                        totalDownPayment = new Integer(downPaymentCount).toString();
                                        totalrentalCounter = new Integer(rentalCount).toString();
                                        totalmosta5lasCount = new Integer(mosta5lasCount).toString();
                                        totaltasweyaWeSarfCounter = new Integer(tasweyaWeSarfCount).toString();
                                        totaltransportationCount = new Integer(transportationCount).toString();
                                        totalretentionCounter = new Integer(retentionCount).toString();
                                        totalinspectionCounter = new Integer(inspectionCount).toString();
                                        totalkemaCounter = new Integer(kemaCount).toString();
                                    }
                                }
                        }
                        }
                        counterId.setText(total2);
                        downPaymentCounter.setText(totalDownPayment);
                        transportationCounter.setText(totaltransportationCount);
                        retentionCounter.setText(totalretentionCounter);
                        kemaCounter.setText(totalkemaCounter);
                        mosta5lasCounter.setText(totalmosta5lasCount);
                        rentalCounter.setText(totalrentalCounter);
                        inspectionCounter.setText(totalinspectionCounter);
                        tasweyaWeSarfCounter.setText(totaltasweyaWeSarfCounter);
                    }

                    searchDocuments.add(document);



                    searchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchDocuments.clear();
                            arrayAdapter.clear();
                            textResult.setVisibility(View.INVISIBLE);

                            // Toast.makeText(getActivity(),"لا يوجد نتائج",Toast.LENGTH_SHORT).show();

                            final SpotsDialog dialog1=new SpotsDialog(getActivity());
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                                final DocUnderReview document = dataSnapshot1.getValue(DocUnderReview.class);
                                String checknoStr = dataSnapshot1.child("po").getValue().toString();

                                if (checknoStr.contains(searchEditReview.getText().toString())) {

                                        searchDocuments.add(document);


                                    arrayAdapter = new UnderReviewAdapter(getActivity(), R.layout.document_layout, searchDocuments);
                                    listView.setAdapter(arrayAdapter);
                                }


                            }

                            dialog1.dismiss();
                        }
                    });
                    btnSearchByName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchDocuments.clear();
                            arrayAdapter.clear();
                            textResult.setVisibility(View.INVISIBLE);

                            // Toast.makeText(getActivity(),"لا يوجد نتائج",Toast.LENGTH_SHORT).show();

                            final SpotsDialog dialog1=new SpotsDialog(getActivity());
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                                final DocUnderReview document = dataSnapshot1.getValue(DocUnderReview.class);
                                String checknoStr = dataSnapshot1.child("name").getValue().toString();

                                if (checknoStr.contains(searchByNameEditText.getText().toString())) {

                                        searchDocuments.add(document);


                                    arrayAdapter = new UnderReviewAdapter(getActivity(), R.layout.document_layout, searchDocuments);
                                    listView.setAdapter(arrayAdapter);
                                }


                            }

                            dialog1.dismiss();
                        }
                    });

                    try {
                        arrayAdapter=new UnderReviewAdapter(getActivity(),R.layout.document_layout, searchDocuments);
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




}
