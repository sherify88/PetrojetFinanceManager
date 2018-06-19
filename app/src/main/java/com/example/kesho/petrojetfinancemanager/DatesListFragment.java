package com.example.kesho.petrojetfinancemanager;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatesListFragment extends android.app.Fragment {


    private DatabaseReference mDatabase;
    ValueEventListener valueEventListener;
    ListView listView;
    List<Order> namesList;
    List<Order> searchNames;
    List<String> datelist;
    DatesListAdapter arrayAdapter;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    public static String NAME_BUNDLE;
    static AlertDialog dialog;
    int totalCounter;
    EditText searchNameEditTxt;
    Button btnTotalSearch;
    String totalCounterString;
    Button btnShowTotalSent;
    TextView txtViewShowTotalOpenOrders;
    int openOrdersCounter;
    public static String DATE_PASSING="10000";
    public static String CHECK_NUM_PASS="10001";


    public DatesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dates_list, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("responses");
        namesList = new ArrayList<>();
        datelist = new ArrayList<>();
        searchNames = new ArrayList<>();
        listView = view.findViewById(R.id.datesList);
        openOrdersCounter = 0;
        dialog = new SpotsDialog(getActivity());
        dialog.show();
        final Bundle bundle=getArguments();




        Query query=mDatabase.orderByChild("response");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                openOrdersCounter = 0;
                searchNames.clear();
                namesList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    final Order name = dataSnapshot1.getValue(Order.class);
                    try {
                        if (bundle.getString(MonthListFragment.MONTH_PASSING).equals(name.getResponseMonth())) {


                            if (name.getResponse().contains("")){

                                namesList.add(name);


                            Collections.sort(namesList, new Comparator<Order>() {
                                public int compare(Order o1, Order o2) {
                                    if (o1.getDateTime() == null || o2.getDateTime() == null)
                                        return 0;
                                    return o1.getDateTime().compareTo(o2.getDateTime());
                                }
                            });
                            //   Collections.sort(namesList);

                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = name.getResponse();

                            try {

                                Date date = formatter.parse(dateInString);
                                System.out.println(date);
                                System.out.println(formatter.format(date));


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                int counter;
                                counter = 0;
                                for (int i = 0; i < (namesList.size() + 1); i++) {

                                    if (namesList.get(i).getResponse().equals(dataSnapshot1.child("response").getValue().toString())) {

                                        counter++;
                                        if (counter == 2) {

                                            namesList.remove(i);

                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        arrayAdapter = new DatesListAdapter(getActivity(), R.layout.dates_layout, namesList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listView.setAdapter(arrayAdapter);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CreditDailyComputingFragment fragment = new CreditDailyComputingFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(DATE_PASSING, namesList.get(position).getResponse());
                bundle1.putString(CHECK_NUM_PASS, namesList.get(position).getCheckNumberForResposeTree());
                fm = getActivity().getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                fragment.setArguments(bundle1);
                ft.addToBackStack(null);
                ft.commit();
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
