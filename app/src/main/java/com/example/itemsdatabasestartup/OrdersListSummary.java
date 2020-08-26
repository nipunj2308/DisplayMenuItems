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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersListSummary extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewForOrdersSummary recyclerViewForOrdersSummary;
    private String CurrentAdminID;
    private DatabaseReference rootReference;
    private ArrayList<ModelClassForSalesAndOrderSummary> OrderDatesList = new ArrayList<>();


    public OrdersListSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_orders_list_summary, container, false);
        recyclerView = view.findViewById(R.id.OrdersListSummaryRecyclerView);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        CurrentAdminID = sharedPreferences.getString("currentAdminID","");
        return  view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewForOrdersSummary = new RecyclerViewForOrdersSummary(OrderDatesList);
        recyclerView.setAdapter(recyclerViewForOrdersSummary);
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
                recyclerViewForOrdersSummary.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
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
