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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowTotalSentListByNameFragment extends android.app.Fragment {


    private DatabaseReference mDatabase;
    ValueEventListener valueEventListener;
    ListView listView;
    List<Order> namesList;
    List<Order> searchNames;
    TotalSentByNameAdapter arrayAdapter;
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
    public static String NAME_PASSING;


    public ShowTotalSentListByNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_total_sent_list_by_name, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("responses");
        namesList = new ArrayList<>();
        searchNames = new ArrayList<>();
        listView = view.findViewById(R.id.totalListViewSentResponseByNames);
        openOrdersCounter = 0;
        dialog = new SpotsDialog(getActivity());
        dialog.show();


        valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                dialog.dismiss();
                openOrdersCounter = 0;
                searchNames.clear();
                namesList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    Order name = dataSnapshot1.getValue(Order.class);

                    if (!dataSnapshot1.child("checkStatus").getValue().toString().equals("استعجال الادارة")) {


                        namesList.add(name);
                    }

                    try {
                        int counter;
                        counter = 0;
                        for (int i = 0; i < (namesList.size() + 1); i++) {

                            if (namesList.get(i).getName().equals(dataSnapshot1.child("name").getValue().toString())) {

                                counter++;
                                if (counter == 2) {

                                    namesList.remove(i);

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                arrayAdapter = new TotalSentByNameAdapter(getActivity(), R.layout.names_list, namesList);
                listView.setAdapter(arrayAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        sentResponsesListFragment fragment = new sentResponsesListFragment();
               Bundle bundle1 = new Bundle();
               bundle1.putString(NAME_PASSING, namesList.get(position).getName());
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
