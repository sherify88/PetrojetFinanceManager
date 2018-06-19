package com.example.kesho.petrojetfinancemanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by kesho on 4/17/2018.
 */

public class TreasuryChecksAdapter  extends ArrayAdapter<DocUnderReview> {


    public static String CHECK_NUMBER_AS_A_KEY="1";
    public static String AMOUNT_PASSING="2";
    public static String NAME_PASSING="3";
    public static String VENDOR_PASSING="4";
    Activity context;
    List<DocUnderReview> documentList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;


    public TreasuryChecksAdapter(@NonNull Activity context, int resource, @NonNull List<DocUnderReview> objects) {
        super(context, resource, objects);
        this.context=context;
        this.documentList =objects;
        this.recource=resource;
        documentList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(recource, parent, false);


        TextView textView5 = view.findViewById(R.id.checkNoTextTitle);

        TextView textView = view.findViewById(R.id.checkamount);
        textView.setText(documentList.get(position).getAmount());
        TextView nameCode = view.findViewById(R.id.nameCode);
        nameCode.setText(documentList.get(position).getName());
        TextView checkDateCode = view.findViewById(R.id.checkDateCode);
        checkDateCode.setText(documentList.get(position).getCheckDate());
        TextView textView2=view.findViewById(R.id.checkNo);
        textView2.setText(documentList.get(position).getCheck());

        TextView textView3 = view.findViewById(R.id.docpo);
        textView3.setText(documentList.get(position).getPo());
        TextView docStatus=view.findViewById(R.id.docStatus);
        TextView docStatusCode=view.findViewById(R.id.docStatusCode);
        docStatus.setVisibility(View.INVISIBLE);
        docStatus.setVisibility(View.GONE);
        docStatusCode.setVisibility(View.INVISIBLE);
        docStatusCode.setVisibility(View.GONE);
        TextView backDate=view.findViewById(R.id.backDate);
        backDate.setVisibility(View.INVISIBLE);
        backDate.setVisibility(View.GONE);
        TextView backDateCode=view.findViewById(R.id.backDateCode);
        backDateCode.setVisibility(View.INVISIBLE);
        backDateCode.setVisibility(View.GONE);
   TextView arrivalDateCode=view.findViewById(R.id.arrivalDateCode);
        arrivalDateCode.setVisibility(View.INVISIBLE);
        arrivalDateCode.setVisibility(View.GONE);
   TextView arrivalDateViewId=view.findViewById(R.id.arrivalDateViewId);
        arrivalDateViewId.setVisibility(View.INVISIBLE);
        arrivalDateViewId.setVisibility(View.GONE);
   TextView keyCode=view.findViewById(R.id.keyCode);
        keyCode.setVisibility(View.INVISIBLE);
        keyCode.setVisibility(View.GONE);
   TextView keyViewId=view.findViewById(R.id.keyViewId);
        keyViewId.setVisibility(View.INVISIBLE);
        keyViewId.setVisibility(View.GONE);
        TextView docTypeCode=view.findViewById(R.id.docTypeCode);
        docTypeCode.setText(documentList.get(position).getDocType());

       final Button addToChecksDates = view.findViewById(R.id.addToChecksDates);
        addToChecksDates.setText("تحديد موعد استلام");


        addToChecksDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addToChecksDates.getText().toString().contains("تم")){



                }else {
                try {


                    DefineOnlyDatesForChecksFragment fragment = new DefineOnlyDatesForChecksFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(CHECK_NUMBER_AS_A_KEY, documentList.get(position).getCheck());
                    bundle.putString(AMOUNT_PASSING, documentList.get(position).getAmount());
                    bundle.putString(NAME_PASSING, documentList.get(position).getName());
                    bundle.putString(VENDOR_PASSING, documentList.get(position).getVendor());
                    fm = context.getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    fragment.setArguments(bundle);
                    ft.addToBackStack(null);
                    ft.commit();

                } catch(Exception e){
                    e.printStackTrace();
                }


            }
            }
        });

        try {
            DatabaseReference responsesDatabase = FirebaseDatabase.getInstance().getReference().child("responses");
            responsesDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    try {
                        if (dataSnapshot.child("checkNumberForResposeTree").exists()) {
                            if (dataSnapshot.child("checkNumberForResposeTree").getValue().toString().equals(documentList.get(position).getCheck())) {

                                addToChecksDates.setText("تم تحديد تاريخ استلام يوم " + dataSnapshot.child("response").getValue().toString());
                                addToChecksDates.setBackgroundColor(context.getResources().getColor(R.color.green));
                            }else {}
                        }  else {}         } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

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


        return view; }
}