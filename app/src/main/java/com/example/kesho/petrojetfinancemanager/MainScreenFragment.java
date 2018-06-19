package com.example.kesho.petrojetfinancemanager;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends android.app.Fragment {

    private DatabaseReference ordersDatabase;


    Button viewOrdersButton;
    Button ManagerOrdersBtn;
    Button btnShowTotalSent;
        Button viewTreasuryChecks;
        Button statistics;
        Button viewUnderReview;
        Button viewDatesList;
    FragmentTransaction ft;
    FragmentManager fm;

    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_screen, container, false);

        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("orders");
        viewOrdersButton=view.findViewById(R.id.viewOrdersId);
        statistics=view.findViewById(R.id.statistics);
        viewUnderReview=view.findViewById(R.id.viewUnderReview);
        viewTreasuryChecks=view.findViewById(R.id.viewTreasuryChecks);
        viewDatesList=view.findViewById(R.id.viewDatesList);
        ManagerOrdersBtn=view.findViewById(R.id.ManagerOrdersBtn);
        btnShowTotalSent=view.findViewById(R.id.btnShowTotalSent);

        try {
            ordersDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.child("checkNumber").exists()){

                        try {
                            viewOrdersButton.setBackgroundColor(getResources().getColor(R.color.openOrderMarkBtn));
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.child("checkNumber").exists()){

                        try {
                            viewOrdersButton.setBackgroundColor(getResources().getColor(R.color.openOrderMarkBtn));
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("checkNumber").exists()){

                        viewOrdersButton.setBackgroundColor(getResources().getColor(R.color.openOrderMarkBtn));

                    }

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NamesListBegFragment fragment = new NamesListBegFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        viewDatesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                MonthListFragment fragment = new MonthListFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        btnShowTotalSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTotalSentListByNameFragment fragment = new ShowTotalSentListByNameFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        viewUnderReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                UnderReviewFragment fragment = new UnderReviewFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        viewTreasuryChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TreasuryChecksFragment fragment = new TreasuryChecksFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AllChecksSheetFragment fragment = new AllChecksSheetFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        ManagerOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                UrgentChecksFragment fragment = new UrgentChecksFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });




    return view;
    }

}
