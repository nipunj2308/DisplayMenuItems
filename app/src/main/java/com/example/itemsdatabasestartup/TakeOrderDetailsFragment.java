package com.example.itemsdatabasestartup;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TakeOrderDetailsFragment extends Fragment {


    private RecyclerView OrdersListRecyclerView , DrawerParentRecyclerView;
    private TextView TransactionsLeft, itemName, itemTotal ;
    private EditText itemId , itemQty, Description, ContactNo;
    private Button AddItemToOrdersList, NextButton;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ImageButton trackOrdersandSMS;
    private ArrayList<ModelClassForOrdersList> orderItems;
    private ArrayList<String> CategoryNames;
    private OrderItemListRecyclerView orderItemListRecyclerView;
    private DrawerRecyclerView drawerRecyclerView;
    private ModelClassForOrdersList modelClassForOrdersList;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private NavController navController;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton ButtonToTrackOrdersAndSMS;

    int id, qty;
    float Total;
    String name, ItemDescription, CustomerContact;


    public TakeOrderDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_take_order_details, container, false);
        sqLiteHelper = new SQLiteHelper(getContext());
        itemId = view.findViewById(R.id.OrdersListItemIDEditText);
        itemQty = view.findViewById(R.id.ItemquantityEditText);
        itemName = view.findViewById(R.id.itemNameTextView);
        itemTotal = view.findViewById(R.id.itemTotalTextView);
        Description = view.findViewById(R.id.DescriptionEditText);
        ContactNo = view.findViewById(R.id.CustomerContactEditText);
        NextButton = view.findViewById(R.id.TakeOrderDetailsToGenerateBillButton);
        OrdersListRecyclerView = view.findViewById(R.id.OrderItemsListRecyclerView);
        DrawerParentRecyclerView = view.findViewById(R.id.DrawerRecyclerView);
        AddItemToOrdersList = view.findViewById(R.id.AddItemtoOrdersButton);
        ButtonToTrackOrdersAndSMS = view.findViewById(R.id.ButtonTotrackOrdersandSMS);

        drawerLayout = view.findViewById(R.id.DrawerLayout1);
        orderItems = new ArrayList<>();
        CategoryNames = new ArrayList<>();
        toolbar = view.findViewById(R.id.toolbar3);
        trackOrdersandSMS = toolbar.findViewById(R.id.ButtonTotrackOrdersandSMS);
        TransactionsLeft = toolbar.findViewById(R.id.transactionsLeftTextView);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // ((SettingsActivity)getActivity()).toolbarHeading.setVisibility(View.GONE);
        //TextView transactionsLeft = new TextView(getContext());
       // transactionsLeft.setGravity(Gravity.END);
        //transactionsLeft.setText("500");
        //((SettingsActivity)getActivity()).toolbar.addView(transactionsLeft);
        //((SettingsActivity)getActivity()).toolbar.setVisibility(View.GONE);
        /*((TakeOrdersActivity)getActivity()).toolbar.setVisibility(View.GONE);
        ((TakeOrdersActivity)getActivity()).setSupportActionBar(toolbar);
        ((TakeOrdersActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);*/




       actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        //if you place this in onstart() the recyclerview will be loaded everytime the user navigates outside activiy and comes
        //back and again onStart() method is called
        DisplayCategory();
        drawerRecyclerView = new DrawerRecyclerView(CategoryNames);
        DrawerParentRecyclerView.setAdapter(drawerRecyclerView);
        DrawerParentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DrawerParentRecyclerView.setHasFixedSize(false);



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        //itemQty.setFilters(new InputFilter[]{ new InputFilterMinMax("1")});
    }

    @Override
    public void onStart() {
        super.onStart();

        AddTextChangeListeners();

        orderItemListRecyclerView = new OrderItemListRecyclerView(orderItems);
        OrdersListRecyclerView.setAdapter(orderItemListRecyclerView);
        OrdersListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OrdersListRecyclerView.setHasFixedSize(false);

        trackOrdersandSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   //navController.navigate(R.id.action_takeOrderDetailsFragment_to_trackOrdersSMSActivity);
                   Intent intent = new Intent(getActivity(),SubletBaseActivity.class);
                   startActivity(intent);
            }
        });


        AddItemToOrdersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(itemId.getText()) && !TextUtils.isEmpty(itemName.getText())&& !TextUtils.isEmpty(itemQty.getText())) {

                    id = Integer.parseInt(itemId.getText().toString());
                    name = itemName.getText().toString();
                    qty = Integer.parseInt(itemQty.getText().toString());
                    Total = Float.parseFloat(itemTotal.getText().toString());
                    ItemDescription = Description.getText().toString();

                    modelClassForOrdersList = new ModelClassForOrdersList(id,name,qty,Total,ItemDescription);
                    orderItems.add(modelClassForOrdersList);

                    itemId.setText("");
                    itemName.setText("");
                    itemQty.setText("");
                    itemTotal.setText("");
                    Description.setText("");
                    orderItemListRecyclerView.notifyDataSetChanged();



                }

                else
                {
                    Toast.makeText(getContext(), "Fill all details", Toast.LENGTH_SHORT).show();
                }

            }
        });


        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (orderItems.size()!=0) {
                    int orderListCount = 0;
                    for (int i=0; i< orderItems.size();i++)
                    {
                        if (orderItems.get(i).getQuantity()!=0 &&  orderItems.get(i).getTotal()!=0)
                        {
                            String query = "select categoryName from itemTally where itemName = '" + orderItems.get(i).getName() + "';";
                            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                            String ItemCategory = "";
                            if (cursor != null)
                            {
                                while(cursor.moveToNext())
                                {
                                    ItemCategory = cursor.getString(0);

                                    if (!ItemCategory.equals("MRP"))
                                    {
                                        ++orderListCount;
                                    }

                                }
                            }
                        }
                    }

                    CustomerContact = ContactNo.getText().toString();
                    Intent intent = new Intent(getActivity(), BillDisplayAndGeneration.class);
                    intent.putExtra("orderListCount", orderListCount);
                    intent.putExtra("customerContact", CustomerContact);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(),"No item added in the order", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();
        //onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /*public ArrayList<ModelClassForOrdersList> populateOrdersList()
    {



    }*/


    public void  AddTextChangeListeners()
    {
        itemId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                     if (TextUtils.isEmpty(editable.toString()))
                     {
                         itemName.setText("");
                         itemTotal.setText("");
                     }
                     else {
                         int ID = Integer.parseInt(editable.toString());

                         sqLiteDatabase = sqLiteHelper.getReadableDatabase();
                         String Name="";
                         float Price= 0;
                         String query = "select itemName, price from itemTally where id= '" + ID + "';";
                         Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                         if (cursor != null)
                         {
                             while (cursor.moveToNext()) {
                                 Name = cursor.getString(0);
                                 Price = cursor.getFloat(1);

                             }
                             itemName.setText(Name);
                             int Quantity = Integer.parseInt(itemQty.getText().toString());
                             itemTotal.setText(Float.toString(Quantity * Price));
                         }
                         else if(cursor == null)
                         {
                             itemName.setText("");
                             itemTotal.setText("");
                         }
                     }
                }
                catch ( NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
        });


        itemQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (TextUtils.isEmpty(editable.toString()))
                    {
                        itemTotal.setText("");
                    }
                   else {
                        int ID = Integer.parseInt(itemId.getText().toString());
                        int Quantity = Integer.parseInt(editable.toString());
                        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
                        float Price;
                        //String query = "select itemName, price from itemTally where id= '" + ID + "';";
                        String query = "select price from itemTally where id= '" + ID + "';";
                        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                //itemName.setText(cursor.getString(0));
                                //Price = cursor.getFloat(1);
                                Price =cursor.getFloat(0);
                                itemTotal.setText(Float.toString(Quantity * Price));

                            }
                        }
                        else {
                            itemName.setText("");
                            itemTotal.setText("");
                        }
                    }
                }
                catch (NumberFormatException e)
                {
                      e.printStackTrace();
                }

            }
        });

    }

    public void DisplayCategory()
    {
        Cursor cursor = sqLiteHelper.DisplayCategoryNames();
        if(cursor !=null)
        {

            while (cursor.moveToNext()) {
                CategoryNames.add(cursor.getString(0));
            }
            CategoryNames.add("MRP");
            CategoryNames.add("UNASSIGNED");

        }

    }
}
