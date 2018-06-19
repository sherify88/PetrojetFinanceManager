package com.example.kesho.petrojetfinancemanager;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class UrgentChecksFragment extends android.app.Fragment {
    Button edkuChecksBtnId;
    FragmentTransaction ft;
    FragmentManager fm;


    public UrgentChecksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_urgent_checks, container, false);

        edkuChecksBtnId=view.findViewById(R.id.edkuChecksBtnId);

        edkuChecksBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                EdkuManagerChecksFragment fragment = new EdkuManagerChecksFragment();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        return view;}

}
