package com.example.kesho.petrojetfinancemanager;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckTypesByRequesterFragment extends android.app.Fragment {

Button vendorsResponsesByDate;
Button editableResponseDates;
public static String DATE_PASSING="1020";
    FragmentManager fm;
    android.app.FragmentTransaction ft;


    public CheckTypesByRequesterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_check_types_by_requester, container, false);
        editableResponseDates=view.findViewById(R.id.editableResponseDates);
        vendorsResponsesByDate=view.findViewById(R.id.vendorsResponsesByDate);
        final Bundle bundle=getArguments();
        final String datePassing=bundle.getString(DatesListFragment.DATE_PASSING);


    return view;}

}
