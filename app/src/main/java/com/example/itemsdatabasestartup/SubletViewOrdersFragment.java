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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubletViewOrdersFragment extends Fragment {

    private DatabaseReference rootReference;
    private ArrayList<ModelClassForOrderDetailsFireBase> ActiveOrdersList;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private String AdminId, currentSubletID, CounterNo;
    private RecyclerViewForSubletActiveOrders recyclerViewForSubletActiveOrders;
    private SubletActiveOrdersRecyclerViewAdapter subletActiveOrdersRecyclerViewAdapter;

    public SubletViewOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_sublet_view_orders, container, false);
        sharedPreferences = getContext().getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        ActiveOrdersList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.SubletActiveOrdersRecyclerView);
        currentSubletID = sharedPreferences.getString("current Sublet ID", "");
        AdminId = sharedPreferences.getString("current Admin ID", "");
        CounterNo = sharedPreferences.getString("Counter No","");
        rootReference = FirebaseDatabase.getInstance().getReference().child("AdminID").child(AdminId).child("Active Orders");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*populateActiveOrdersList();
        recyclerViewForSubletActiveOrders = new RecyclerViewForSubletActiveOrders(ActiveOrdersList);
        recyclerView.setAdapter(recyclerViewForSubletActiveOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);*/
        FirebaseRecyclerOptions<ModelClassFirebaseForActiveOrdersAdapter> options =
                new FirebaseRecyclerOptions.Builder<ModelClassFirebaseForActiveOrdersAdapter>()
                        .setQuery(rootReference, ModelClassFirebaseForActiveOrdersAdapter.class)
                        .build();


        subletActiveOrdersRecyclerViewAdapter = new SubletActiveOrdersRecyclerViewAdapter(options);
        recyclerView.setAdapter(subletActiveOrdersRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        subletActiveOrdersRecyclerViewAdapter.startListening();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStop() {
        super.onStop();
        subletActiveOrdersRecyclerViewAdapter.stopListening();
    }

    public void populateActiveStatusList()
    {
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    DisplayOrderDetails(dataSnapshot);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void DisplayOrderDetails(DataSnapshot dataSnapshot)
    {
        ModelClassForOrderDetailsFireBase modelClass;
        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
        {
            final String messageKey = dataSnapshot1.getKey();

            rootReference.child(messageKey).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     ModelClassForOrderDetailsFireBase modelClass = new ModelClassForOrderDetailsFireBase();
                     HashMap<String, Object> ActiveOrderDetails = new HashMap<>();
                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                     {

                         ActiveOrderDetails.put(String.valueOf(dataSnapshot1.getKey()), String.valueOf(dataSnapshot1.getValue()));

                     }
                     String[] Counters = ActiveOrderDetails.get("Counters").toString().split(",");
                     String[] description = ActiveOrderDetails.get("Descriptions").toString().split(",");
                     String[] itemName = ActiveOrderDetails.get("Item Names").toString().split(",");
                     String[] Quantity = ActiveOrderDetails.get("Quantities").toString().split(",");

                     String CustomerContact = ActiveOrderDetails.get("Customer Contact").toString();
                     String CustomerName = ActiveOrderDetails.get("Customer Name").toString();
                     String Time= ActiveOrderDetails.get("Time").toString();
                     String OrderNo = ActiveOrderDetails.get("Order Number").toString();
                     String MessageKey = messageKey;

                     String[] Statuses = new String[Counters.length];
                     String[] preparationTime = new String[Counters.length];
                     for (int i = 0; i < Counters.length; i++) {
                         Statuses[i] = ActiveOrderDetails.get("Item "+i+" status").toString();
                         preparationTime[i] = ActiveOrderDetails.get("Item "+i+" Preparation Time").toString();
                     }

                     for (int i = 0; i < Counters.length; i++)
                     {
                         if (Counters[i].equals(CounterNo)) {
                             String Description = " ";


                             //if there is no description mentioned
                             if (description.length == 0) {
                                 Description = " ";
                             } else {
                                 Description = description[i];
                             }

                             modelClass = new ModelClassForOrderDetailsFireBase(Integer.parseInt(OrderNo), itemName[i], Integer.parseInt(Quantity[i]), Integer.parseInt(Counters[i]), Description, Statuses[i], preparationTime[i],MessageKey,i);

                             ActiveOrdersList.add(modelClass);
                             recyclerViewForSubletActiveOrders.notifyDataSetChanged();

                         }
                     }



                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });


        }

    }



    public void populateActiveOrdersList()
    {

        rootReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                if (dataSnapshot.exists())
                {
                  for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                   {
                      String messageKey = dataSnapshot1.getKey();
                      DisplayActiveOrders(dataSnapshot, messageKey);
                   }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        String messageKey = dataSnapshot1.getKey();
                        DisplayActiveOrders(dataSnapshot, messageKey);
                    }

                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void DisplayActiveOrders(DataSnapshot dataSnapshot, final String messageKey)
    {
        rootReference.child(messageKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelClassForOrderDetailsFireBase modelClass = new ModelClassForOrderDetailsFireBase();
                HashMap<String, Object> ActiveOrderDetails = new HashMap<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {

                    ActiveOrderDetails.put(String.valueOf(dataSnapshot1.getKey()), String.valueOf(dataSnapshot1.getValue()));

                }

                String[] Counters = ActiveOrderDetails.get("Counters").toString().split(",");
                String[] description = ActiveOrderDetails.get("Descriptions").toString().split(",");
                String[] itemName = ActiveOrderDetails.get("Item Names").toString().split(",");
                String[] Quantity = ActiveOrderDetails.get("Quantities").toString().split(",");

                String CustomerContact = ActiveOrderDetails.get("Customer Contact").toString();
                String CustomerName = ActiveOrderDetails.get("Customer Name").toString();
                String Time= ActiveOrderDetails.get("Time").toString();
                String OrderNo = ActiveOrderDetails.get("Order Number").toString();

                String[] Statuses = new String[Counters.length];
                String[] preparationTime = new String[Counters.length];
                for (int i = 0; i < Counters.length; i++) {
                    Statuses[i] = ActiveOrderDetails.get("Item "+i+" status").toString();
                    preparationTime[i] = ActiveOrderDetails.get("Item "+i+" Preparation Time").toString();
                }

                for (int i = 0; i < Counters.length; i++)
                {
                    if (Counters[i].equals(CounterNo)) {
                        String Description = " ";


                        //if there is no description mentioned
                        if (description.length == 0) {
                            Description = " ";
                        } else {
                            Description = description[i];
                        }

                        modelClass = new ModelClassForOrderDetailsFireBase(Integer.parseInt(OrderNo), itemName[i], Integer.parseInt(Quantity[i]), Integer.parseInt(Counters[i]), Description, Statuses[i], preparationTime[i],messageKey,i);

                        ActiveOrdersList.add(modelClass);
                        recyclerViewForSubletActiveOrders.notifyDataSetChanged();

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
