package com.example.itemsdatabasestartup;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayMenuItemsFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private Spinner Categoryspinner;
    private ArrayList<String> CategoryNames = new ArrayList<>();;
    private String CategorySelected;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private EditText itemId, itemName, itemPrice, itemCounter;
    public TextView DefineMenuItems;
    private int ItemId , CounterNo;
    private String ItemName;
    private float Price;
    private Button AddItemDetails ;
    /*private ArrayList<String> ItemNames , ItemCategory;
    private ArrayList<Integer> ItemID , ItemCounter;
    private ArrayList<Float> ItemPrice;*/
    private final ArrayList<ModelClassForItemDetails> itemDetails = new ArrayList<>();
    private MenuItemsRecyclerAdapter menuItemsRecyclerAdapter;
    private RecyclerView recyclerView;
    private ModelClassForItemDetails modelClassForItemDetails;

    public DisplayMenuItemsFragment() {
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
        View view = inflater.inflate(R.layout.activity_define_menu_items, container, false);
        sqLiteHelper = new SQLiteHelper(getContext());

        // Spinner populated
        CategoryNames.add(0,"UNASSIGNED");
        CategoryNames.add(1, "MRP");
        Categoryspinner = view.findViewById(R.id.CategoriesSpinner);




        itemId = view.findViewById(R.id.itemIdEditText);
        itemName = view.findViewById(R.id.itemNameEditText);
        itemPrice = view.findViewById(R.id.itemPriceEditText);
        itemCounter = view.findViewById(R.id.itemCounterEdiText);
        AddItemDetails = view.findViewById(R.id.AddItemDetailsButton);
        recyclerView = view.findViewById(R.id.MenuItemsDisplayRecylerView);
        DefineMenuItems = view.findViewById(R.id.MenuItemsTextView);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //((SettingsActivity)getActivity()).setToolbarTitle("Menu Items Settings");


    }


    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigationView).setVisibility(View.GONE);

        populateCategorySpinner();
        Categoryspinner.setSelection(0);
        populateItemData();

        menuItemsRecyclerAdapter = new MenuItemsRecyclerAdapter(itemDetails);
        recyclerView.setAdapter(menuItemsRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);


        ArrayAdapter<String> CategoriesSpinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,CategoryNames);
        CategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Categoryspinner.setAdapter(CategoriesSpinnerAdapter);
        Categoryspinner.setOnItemSelectedListener(this);


        AddItemDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemId.getText()) && !TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemPrice.getText()) && !TextUtils.isEmpty(itemCounter.getText()))

                {

                    // format the data before doing any operations in database
                    getFormattedValues();
                    boolean DataInserted = sqLiteHelper.AddItemDetails(ItemId, ItemName , Price, CounterNo, CategorySelected);
                    if (DataInserted == true) {
                        Toast.makeText(getContext(), "data added successfully", Toast.LENGTH_SHORT).show();
                        // Add formatted data to arraylists that are fed to the recycler view Adapter

                       /* ItemID.add(ItemId);
                        ItemNames.add(ItemName);
                        ItemPrice.add(Price);
                        ItemCounter.add(CounterNo);
                        ItemCategory.add(CategorySelected);*/
                        //ModelClassForItemDetails modelClass = new ModelClassForItemDetails();
                       /*modelClassForItemDetails.setID(ItemId);
                       modelClassForItemDetails.setItemName(ItemName);
                       modelClassForItemDetails.setPrice(Price);
                       modelClassForItemDetails.setCounter(CounterNo);
                       modelClassForItemDetails.setCategory(CategorySelected);
                       itemDetails.add(modelClassForItemDetails);*/
                        modelClassForItemDetails = new ModelClassForItemDetails(ItemId,ItemName,Price,CounterNo,CategorySelected);
                        itemDetails.add(modelClassForItemDetails);

                        menuItemsRecyclerAdapter.notifyDataSetChanged();

                        //Reset the EdiTexts and spinner

                        itemId.setText("");
                        itemName.setText("");
                        itemPrice.setText("");
                        itemCounter.setText("");
                        Categoryspinner.setSelection(0);
                        DefineMenuItems.setText("Menu Items");

                    }
                    else {
                        Toast.makeText(getContext(), "unsuccessful...try again", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "please fill all the details", Toast.LENGTH_SHORT).show();
                }


            }
        });





    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public ArrayList<ModelClassForItemDetails> populateItemData()
    {
        //ModelClassForItemDetails modelClassForItemDetails = new ModelClassForItemDetails();
        //final ArrayList<ModelClassForItemDetails> itemList = new ArrayList<>();
        Cursor cursor = sqLiteHelper.DisplayItemDetails();
        if(cursor.getCount() == 0)
        {

            DefineMenuItems.setText("No Items in Menu");
        }
        else {
            while (cursor.moveToNext()) {
                //categoryNames.add(cursor.getString(0));
               /* ItemID.add(cursor.getInt(0));
                ItemNames.add(cursor.getString(1));
                ItemPrice.add(cursor.getFloat(2));
                ItemCounter.add(cursor.getInt(3));
                ItemCategory.add(cursor.getString(4));*/

               /* modelClassForItemDetails.setID(cursor.getInt(0));
                modelClassForItemDetails.setItemName(cursor.getString(1));
                modelClassForItemDetails.setPrice(cursor.getFloat(2));
                modelClassForItemDetails.setCounter(cursor.getInt(3));
                modelClassForItemDetails.setCategory(cursor.getString(4));*/
                //if (cursor.getString(4) != null) {
                    modelClassForItemDetails = new ModelClassForItemDetails(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2), cursor.getInt(3), cursor.getString(4));
                    itemDetails.add(modelClassForItemDetails);
                //}
                /*else if (cursor.getString(4) == null)
                {
                    modelClassForItemDetails = new ModelClassForItemDetails(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2), cursor.getInt(3), "UNASSIGNED");
                    itemDetails.add(modelClassForItemDetails);
                }*/
            }
        }
        return  itemDetails;

    }

    public void populateCategorySpinner() {
        //similar to DisplayCategory() method in DefineCategories class
        Cursor cursor = sqLiteHelper.DisplayCategoryNames();
        // ArrayList<String> categoryNames = new ArrayList<>();
        if (cursor.getCount() == 0)
        {
            //CategoryNames.add("Unavailable");

        }
        else {

                while (cursor.moveToNext()) {

                    CategoryNames.add(cursor.getString(0));
                }

        }
    }


    public void getFormattedValues()
    {
       // try {

            ItemId = Integer.parseInt(itemId.getText().toString().trim());
            CounterNo = Integer.parseInt(itemCounter.getText().toString().trim());
            ItemName = itemName.getText().toString();
            String ItemPrice = String.format("%.2f", Double.parseDouble(itemPrice.getText().toString().trim()));
            Price = Float.parseFloat(ItemPrice);
            ArrayAdapter CategoryNameSpinner = (ArrayAdapter) Categoryspinner.getAdapter();
            int CategoryNamePosition = CategoryNameSpinner.getPosition(CategorySelected);
            //Categoryspinner.setSelection(CategoryNamePosition);
        /*}
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CategorySelected = adapterView.getItemAtPosition(i).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
