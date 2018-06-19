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
public class TreasuryChecksFragment extends android.app.Fragment {
    private DatabaseReference mDatabase;
    TextView vendorTitleChecks;
    TextView textResult;
    TextView noDocForReview;
    Button btnSearchByName;
    Button btnSearchByCheckNo;
    EditText searchByNameEditText;
    EditText searchByCheckNoEditText;

    ImageView imageView;
    ListView listView;
    List<DocUnderReview> documents;
    List<DocUnderReview> searchDocuments;
    EditText searchEditReview;
    Button searchBtn;
    TreasuryChecksAdapter arrayAdapter;
    DocUnderReview document;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    static AlertDialog dialog;

    public TreasuryChecksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_treasury_checks, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("vendors");
        final Bundle bundle=getArguments();
        textResult=view.findViewById(R.id.noResults);
        btnSearchByCheckNo=view.findViewById(R.id.btnSearchByCheckNo);
        searchByCheckNoEditText=view.findViewById(R.id.searchByCheckNoEditText);
        listView=view.findViewById(R.id.treasuryChecksListView);
        btnSearchByName=view.findViewById(R.id.btnSearchByName);
        searchByNameEditText=view.findViewById(R.id.searchByNameEditText);
        documents=new ArrayList<>();
        searchDocuments=new ArrayList<>();
        searchBtn=view.findViewById(R.id.btnSearchByPo);
        searchEditReview=view.findViewById(R.id.searchEditTextReview);
        dialog = new SpotsDialog(getActivity());
        dialog.show();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                dialog.dismiss();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    dialog.dismiss();


                    final DocUnderReview document =dataSnapshot1.getValue(DocUnderReview.class);

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


                                    arrayAdapter = new TreasuryChecksAdapter(getActivity(), R.layout.document_layout, searchDocuments);
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


                                    arrayAdapter = new TreasuryChecksAdapter(getActivity(), R.layout.document_layout, searchDocuments);
                                    listView.setAdapter(arrayAdapter);
                                }


                            }

                            dialog1.dismiss();
                        }
                    });
                    btnSearchByCheckNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchDocuments.clear();
                            arrayAdapter.clear();
                            textResult.setVisibility(View.INVISIBLE);

                            // Toast.makeText(getActivity(),"لا يوجد نتائج",Toast.LENGTH_SHORT).show();

                            final SpotsDialog dialog1=new SpotsDialog(getActivity());
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                                final DocUnderReview document = dataSnapshot1.getValue(DocUnderReview.class);
                                String checknoStr = dataSnapshot1.child("check").getValue().toString();

                                if (checknoStr.contains(searchByCheckNoEditText.getText().toString())) {

                                    searchDocuments.add(document);


                                    arrayAdapter = new TreasuryChecksAdapter(getActivity(), R.layout.document_layout, searchDocuments);
                                    listView.setAdapter(arrayAdapter);
                                }


                            }

                            dialog1.dismiss();
                        }
                    });

                    try {
                        arrayAdapter=new TreasuryChecksAdapter(getActivity(),R.layout.document_layout, searchDocuments);
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
