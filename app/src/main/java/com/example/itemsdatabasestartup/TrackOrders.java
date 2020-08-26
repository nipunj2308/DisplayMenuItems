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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
public class TrackOrders extends Fragment

{
    private DatabaseReference rootReference;
    private String currentUserId;
    private SharedPreferences sharedPreferences;
    private ArrayList<ModelClassForOrderDetailsFireBase> ActiveOrdersList;
    private RecyclerView recyclerView;
    private TrackOrdersRecyclerView trackOrdersRecyclerView;
    private TrackOrdersAdapterFirebaseRecyclerView trackOrdersFirebaseRecyclerAdapter;
    //FirebaseRecyclerOptions<ModelClassFirebaseForActiveOrdersAdapter> options;

    public TrackOrders()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_orders, container, false);
        ActiveOrdersList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.TrackOrdersRecyclerView);
        sharedPreferences = getContext().getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("currentAdminID","");
        rootReference = FirebaseDatabase.getInstance().getReference().child("AdminID").child(currentUserId).child("Active Orders");


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* populateActiveOrdersList();
        trackOrdersRecyclerView = new TrackOrdersRecyclerView(ActiveOrdersList);
        recyclerView.setAdapter(trackOrdersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);*/
        FirebaseRecyclerOptions<ModelClassFirebaseForActiveOrdersAdapter> options = new FirebaseRecyclerOptions.Builder<ModelClassFirebaseForActiveOrdersAdapter>()
                .setQuery(rootReference, ModelClassFirebaseForActiveOrdersAdapter.class)
                .build();
        trackOrdersFirebaseRecyclerAdapter = new TrackOrdersAdapterFirebaseRecyclerView(options);
        recyclerView.setAdapter(trackOrdersFirebaseRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        trackOrdersFirebaseRecyclerAdapter.startListening();

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
    public void onStop()
    {
        super.onStop();
        trackOrdersFirebaseRecyclerAdapter.stopListening();
    }





    public void populateActiveOrdersList()
    {

       rootReference.addValueEventListener(new ValueEventListener() {
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

       //rootReference.addChildEventListener(new Va)



    }


    public void DisplayOrderDetails(DataSnapshot dataSnapshot)
    {

        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
        {
            final String messageKey = dataSnapshot1.getKey();

            rootReference.child(messageKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
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

                            String Description = " ";


                            //if there is no description mentioned
                            if (description.length == 0) {
                                Description = " ";
                            } else {
                                Description = description[i];
                            }

                            modelClass = new ModelClassForOrderDetailsFireBase(Integer.parseInt(OrderNo), itemName[i], Integer.parseInt(Quantity[i]), Integer.parseInt(Counters[i]), Description, Statuses[i], preparationTime[i],MessageKey,i);
                            ActiveOrdersList.add(modelClass);
                        trackOrdersRecyclerView.notifyDataSetChanged();


                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            /*rootReference.addChildEventListener(new ChildEventListener()
            {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext())
                    {

                        String Counters = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] Counter = Counters.split(",");


                        String CustomerContact = ((DataSnapshot) iterator.next()).getValue().toString();

                        String CustomerName = ((DataSnapshot) iterator.next()).getValue().toString();

                        String Descriptions = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] description = Descriptions.split(",");

                        String[] Statuses = new String[Counter.length];
                        String[] preparationTime = new String[Counter.length];
                        for (int i = 0; i < Counter.length; i++) {
                            preparationTime[i] = ((DataSnapshot) iterator.next()).getValue().toString();
                            Statuses[i] = ((DataSnapshot) iterator.next()).getValue().toString();

                        }


                        String ItemNames = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] itemName = ItemNames.split(",");


                        String OrderNo = ((DataSnapshot) iterator.next()).getValue().toString();


                        String Quantities = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] Quantity = Quantities.split(",");


                        String Time = ((DataSnapshot) iterator.next()).getValue().toString();
                        //  Toast.makeText(getContext(),""+itemName,Toast.LENGTH_SHORT).show();


                        for (int i = 0; i < itemName.length; i++) {
                            String Description = " ";
                            //if there is no description mentioned
                            if (description.length == 0) {
                                Description = " ";
                            } else {
                                Description = description[i];
                            }

                            ModelClassForOrderDetailsFireBase modelClass = new ModelClassForOrderDetailsFireBase(Integer.parseInt(OrderNo), itemName[i], Integer.parseInt(Quantity[i]), Integer.parseInt(Counter[i]), Description, Statuses[i], preparationTime[i], i);
                            ActiveOrdersList.add(modelClass);
                            trackOrdersRecyclerView.notifyDataSetChanged();

                        }


                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    while (iterator.hasNext())
                    {

                        String Counters = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] Counter = Counters.split(",");


                        String CustomerContact = ((DataSnapshot) iterator.next()).getValue().toString();

                        String CustomerName = ((DataSnapshot) iterator.next()).getValue().toString();

                        String Descriptions = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] description = Descriptions.split(",");

                        String[] Statuses = new String[Counter.length];
                        String[] preparationTime = new String[Counter.length];
                        for (int i = 0; i < Counter.length; i++) {
                            preparationTime[i] = ((DataSnapshot) iterator.next()).getValue().toString();
                            Statuses[i] = ((DataSnapshot) iterator.next()).getValue().toString();

                        }


                        String ItemNames = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] itemName = ItemNames.split(",");


                        String OrderNo = ((DataSnapshot) iterator.next()).getValue().toString();


                        String Quantities = ((DataSnapshot) iterator.next()).getValue().toString();
                        String[] Quantity = Quantities.split(",");


                        String Time = ((DataSnapshot) iterator.next()).getValue().toString();

                        //  Toast.makeText(getContext(),""+itemName,Toast.LENGTH_SHORT).show();


                        for (int i = 0; i < itemName.length; i++) {
                            String Description = " ";
                            //if there is no description mentioned
                            if (description.length == 0) {
                                Description = " ";
                            } else {
                                Description = description[i];
                            }

                            ModelClassForOrderDetailsFireBase modelClass = new ModelClassForOrderDetailsFireBase(Integer.parseInt(OrderNo), itemName[i], Integer.parseInt(Quantity[i]), Integer.parseInt(Counter[i]), Description, Statuses[i], preparationTime[i], i);
                            ActiveOrdersList.add(modelClass);
                            trackOrdersRecyclerView.notifyDataSetChanged();

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
*/
       }
    }

}
