package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;

public class FullOrdersListSummary extends AppCompatActivity {

    private DatabaseReference rootReference;
    private String CurrentAdminID;
    private String SelectedDate;
    private RecyclerView recyclerView;
    private TextView TotalOrders, Top3Popular, Least3Popular, OrdersPreparedPerCounter;
    private TextView AvgTimePerCounter;
    private OrdersListRecyclerView ordersListRecyclerView;
    private ArrayList<ModelClassForOrderDetailsFireBase> orderDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_orders_list_summary);

        SharedPreferences sharedPreferences = this.getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras();
        SelectedDate = bundle.getString("Selected Date");

        CurrentAdminID = sharedPreferences.getString("currentAdminID","");
        rootReference = FirebaseDatabase.getInstance().getReference().child("AdminID").child(CurrentAdminID).child("Orders").child(SelectedDate);

         recyclerView = findViewById(R.id.OrdersListRecyclerView);
         orderDetails = new ArrayList<>();
         populateOrderDetailsList();
         ordersListRecyclerView = new OrdersListRecyclerView(orderDetails);
         recyclerView.setAdapter(ordersListRecyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setHasFixedSize(false);




    }



    public void populateOrderDetailsList()
    {

        rootReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists())
                {
                    DisplayOrderDetails(dataSnapshot) ;

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists())
                {
                    DisplayOrderDetails(dataSnapshot);
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


    public void DisplayOrderDetails(DataSnapshot dataSnapshot)
    {
        ModelClassForOrderDetailsFireBase modelClass;
        Iterator iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext())
        {
            String Counters = ((DataSnapshot) iterator.next()).getValue().toString();
            String CustomerContact = ((DataSnapshot) iterator.next()).getValue().toString();
            String CustomerName = ((DataSnapshot) iterator.next()).getValue().toString();
            String Descriptions = ((DataSnapshot) iterator.next()).getValue().toString();
            String ItemNames = ((DataSnapshot) iterator.next()).getValue().toString();
            String OrderNo = ((DataSnapshot) iterator.next()).getValue().toString();
            String OrderPreparationTime = ((DataSnapshot) iterator.next()).getValue().toString();
            String OrderTotal = ((DataSnapshot) iterator.next()).getValue().toString();
            String Quantities = ((DataSnapshot) iterator.next()).getValue().toString();
            String Statuses =((DataSnapshot) iterator.next()).getValue().toString();
            String Time = ((DataSnapshot) iterator.next()).getValue().toString();



            modelClass = new ModelClassForOrderDetailsFireBase(OrderNo, Time, CustomerName, CustomerContact, ItemNames
                    , Quantities, Counters, OrderPreparationTime, OrderTotal);

            orderDetails.add(modelClass);
            ordersListRecyclerView.notifyDataSetChanged();

        }


    }
}
