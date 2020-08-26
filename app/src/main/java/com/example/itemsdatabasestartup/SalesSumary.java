package com.example.itemsdatabasestartup;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalesSumary extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootReference;
    private String CurrentAdminID;
    private RecyclerView recyclerView;
    private RecyclerViewForSalesSummary recyclerViewForSalesSummary;
    private ArrayList<ModelClassForSalesAndOrderSummary> OrderDatesList = new ArrayList<>();
    private Button ViewMonthlyDetails;
    private LinearLayout ExpandableLinearLayout, MonthlySummaryLinearLayout;
    private TextView MonthlySummaryText, CurrentMonthTextView, TotalSaleTextView;
    public boolean Expanded = false;

    public boolean isExpanded() {
        return Expanded;
    }

    public void setExpanded(boolean expanded) {
        Expanded = expanded;
    }

    public SalesSumary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_sales_sumary, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        CurrentAdminID = sharedPreferences.getString("currentAdminID","");


        //MonthlySummaryText = view.findViewById(R.id.MonthlySummaryText);
       // CurrentMonthTextView = view.findViewById(R.id.CurrentMonthTextView);
        TotalSaleTextView = view.findViewById(R.id.TotalSaleTextView);
      //  MonthlySummaryLinearLayout = view.findViewById(R.id.MonthlySummaryLinearLayoutMain);
        ExpandableLinearLayout = view.findViewById(R.id.ExpandableLayout);
        recyclerView = view.findViewById(R.id.salesSummaryRecyclerView);
        ModelClassForSalesAndOrderSummary modelClass = new ModelClassForSalesAndOrderSummary("Monthly Summary");
        OrderDatesList.add(0, modelClass);


        // Inflate the layout for this fragment
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewForSalesSummary = new RecyclerViewForSalesSummary(OrderDatesList);
        recyclerView.setAdapter(recyclerViewForSalesSummary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);

        rootReference = FirebaseDatabase.getInstance().getReference().child("AdminID").child(CurrentAdminID).child("Orders");
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // OrderDatesList.clear();
                // Toast.makeText(getContext(), "abc", Toast.LENGTH_SHORT).show();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    try
                    {
                        OrderDatesList.add(new ModelClassForSalesAndOrderSummary(dataSnapshot1.getKey()));

                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }

                }
                recyclerViewForSalesSummary.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        //String CurrentMonth = new BillDisplayAndGeneration().getCurrentMonth();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
