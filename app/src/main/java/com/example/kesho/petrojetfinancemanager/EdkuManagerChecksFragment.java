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
import android.widget.Toast;

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
public class EdkuManagerChecksFragment extends android.app.Fragment {

    private DatabaseReference mDatabase;
    private DatabaseReference ordersDatabase;
    TextView vendorTitleChecks;
    TextView textResult;
    ValueEventListener valueEventListener;
    TextView noDocForReview;
    ImageView imageView;
    ListView listView;
    List<DocUnderReview> documents;
    List<DocUnderReview> searchDocuments;
    EditText searchEditReview;
    Button searchBtn;
    ManagersOrdersAdapter arrayAdapter;
    DocUnderReview document;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    static AlertDialog dialog;

    public EdkuManagerChecksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_orders, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("managerOrders");
        textResult=view.findViewById(R.id.noResults);
        listView=view.findViewById(R.id.usnderReviewListViewId);
        documents=new ArrayList<>();
        searchDocuments=new ArrayList<>();
        searchBtn=view.findViewById(R.id.btnSearchByPo);
        searchEditReview=view.findViewById(R.id.searchEditTextReview);
        dialog = new SpotsDialog(getActivity());
        dialog.show();






        valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                dialog.dismiss();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    dialog.dismiss();



                       // Toast.makeText(getActivity(),"exists",Toast.LENGTH_SHORT).show();
           //             Toast.makeText(getActivity(),dataSnapshot1.child("ManagersUrgentChecks").getValue().toString(),Toast.LENGTH_SHORT).show();

                                       final DocUnderReview document =dataSnapshot1.getValue(DocUnderReview.class);

                    if (dataSnapshot1.child("ManagersUrgentChecks").getValue().toString().contains("Edku Manager")
                            &&dataSnapshot1.child("ManagersOrderStatus").getValue().toString().equals("تم عمل استعجال")) {
                        searchDocuments.add(document);
                        Toast.makeText(getActivity(), "equals", Toast.LENGTH_SHORT).show();


                        searchBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchDocuments.clear();
                                arrayAdapter.clear();
                                textResult.setVisibility(View.INVISIBLE);

                                // Toast.makeText(getActivity(),"لا يوجد نتائج",Toast.LENGTH_SHORT).show();

                                final SpotsDialog dialog1 = new SpotsDialog(getActivity());
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                    final DocUnderReview document = dataSnapshot1.getValue(DocUnderReview.class);
                                    String checknoStr = dataSnapshot1.child("po").getValue().toString();

                                    if (checknoStr.contains(searchEditReview.getText().toString())) {

                                        if (dataSnapshot1.child("ManagersUrgentChecks").getValue().toString().equals("Edku Manager")
                                                &&dataSnapshot1.child("ManagersOrderStatus").getValue().toString().equals("تم عمل استعجال")) {

                                            searchDocuments.add(document);


                                            arrayAdapter = new ManagersOrdersAdapter(getActivity(), R.layout.manager_orders_item, searchDocuments);
                                            listView.setAdapter(arrayAdapter);
                                        }


                                    }                                }

                                dialog1.dismiss();
                            }
                        });

                        arrayAdapter = new ManagersOrdersAdapter(getActivity(), R.layout.manager_orders_item, searchDocuments);
                        listView.setAdapter(arrayAdapter);


                    }                }



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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.removeEventListener(valueEventListener);
    }
}
