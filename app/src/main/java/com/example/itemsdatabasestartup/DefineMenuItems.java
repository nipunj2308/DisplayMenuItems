package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DefineMenuItems extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner Categoryspinner;
    private ArrayList<String> CategoryNames;
    private String CategorySelected;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private EditText itemId, itemName, itemPrice, itemCounter;
    private int ItemId , CounterNo;
    private String ItemName;
    private float Price;
    private Button AddItemDetails ;
    private ArrayList<String> ItemNames , ItemCategory;
    private ArrayList<Integer> ItemID , ItemCounter;
    private ArrayList<Float> ItemPrice;
    private final ArrayList<ModelClassForItemDetails> itemDetails = new ArrayList<>();
    private MenuItemsRecyclerAdapter menuItemsRecyclerAdapter;
    private RecyclerView recyclerView;
    private ModelClassForItemDetails modelClassForItemDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_menu_items);
        sqLiteHelper = new SQLiteHelper(this);
        Categoryspinner = findViewById(R.id.CategoriesSpinner);
        CategoryNames = new ArrayList<>();
        // Spinner populated
        populateCategorySpinner();
        Categoryspinner.setSelection(0);
        populateItemData();

        itemId = findViewById(R.id.itemIdEditText);
        itemName = findViewById(R.id.itemNameEditText);
        itemPrice = findViewById(R.id.itemPriceEditText);
        itemCounter = findViewById(R.id.itemCounterEdiText);
        AddItemDetails = findViewById(R.id.AddItemDetailsButton);
        recyclerView = findViewById(R.id.MenuItemsDisplayRecylerView);
        sqLiteHelper = new SQLiteHelper(this);


       /* ItemNames = new ArrayList<>();
        ItemCategory = new ArrayList<>();
        ItemID = new ArrayList<>();
        ItemCounter = new ArrayList<>();
        ItemPrice = new ArrayList<>();
        itemDetails = populateItemData();
        */

        //menuItemsRecyclerAdapter = new MenuItemsRecyclerAdapter(ItemID , ItemNames , ItemPrice , ItemCounter , ItemCategory);
        menuItemsRecyclerAdapter = new MenuItemsRecyclerAdapter(itemDetails);
        recyclerView.setAdapter(menuItemsRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DefineMenuItems.this));
        recyclerView.setHasFixedSize(false);


        // populate the string arraylist by calling populateCategorySpinner before attaching it to adapter

        ArrayAdapter<String> CategoriesSpinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,CategoryNames);
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
                    boolean DataInserted = sqLiteHelper.AddItemDetails(ItemId, ItemName, Price, CounterNo, CategorySelected);
                    if (DataInserted == true) {
                        Toast.makeText(DefineMenuItems.this, "data added successfully", Toast.LENGTH_SHORT).show();
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

                    }
                    else {
                        Toast.makeText(DefineMenuItems.this, "unsuccessful...try again", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(DefineMenuItems.this, "please fill all the details", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //DisplayMenuItems();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CategorySelected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void populateCategorySpinner() {
       //similar to DisplayCategory() method in DefineCategories class
        Cursor cursor = sqLiteHelper.DisplayCategoryNames();
        // ArrayList<String> categoryNames = new ArrayList<>();
        if (cursor.getCount() == 0) {
            CategoryNames.add("Unavailable");
        } else {
            CategoryNames.add(0,"Null");
            while (cursor.moveToNext()) {

                CategoryNames.add(cursor.getString(0));
            }
        }


    }

    public ArrayList<ModelClassForItemDetails> populateItemData()
    {
         //ModelClassForItemDetails modelClassForItemDetails = new ModelClassForItemDetails();
         //final ArrayList<ModelClassForItemDetails> itemList = new ArrayList<>();
        Cursor cursor = sqLiteHelper.DisplayItemDetails();
        if(cursor.getCount() == 0)
        {
            //DefinedCategories.setText(" No Categories Defined");
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

                modelClassForItemDetails = new ModelClassForItemDetails(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2),cursor.getInt(3),cursor.getString(4));
                itemDetails.add(modelClassForItemDetails);
            }
        }
     return  itemDetails;


    }


    public void getFormattedValues()
    {
        try {


            ItemId = Integer.parseInt(itemId.getText().toString());
            CounterNo = Integer.parseInt(itemCounter.getText().toString());
            ItemName = itemName.getText().toString();
            String ItemPrice = String.format("%.2f", Double.parseDouble(itemPrice.getText().toString()));
            Price = Float.parseFloat(ItemPrice);
            ArrayAdapter CategoryNameSpinner = (ArrayAdapter) Categoryspinner.getAdapter();
            int CategoryNamePosition = CategoryNameSpinner.getPosition(CategorySelected);
            //spinner.setSelection(CategoryNamePosition);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

    }


    public void DisplayMenuItems()
    {
        Cursor cursor = sqLiteHelper.DisplayItemDetails();
        if(cursor.getCount() == 0)
        {
            //DefinedCategories.setText(" No Categories Defined");
        }
        else {
            while (cursor.moveToNext()) {
                //categoryNames.add(cursor.getString(0));
                ItemID.add(cursor.getInt(0));
                ItemNames.add(cursor.getString(1));
                ItemPrice.add(cursor.getFloat(2));
                ItemCounter.add(cursor.getInt(3));
                ItemCategory.add(cursor.getString(4));

            }
        }
    }


/*
    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage(" Are you sure you want to delete this item?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                          menuItemsRecyclerAdapter.DeleteMenuItem(item.getGroupId());
                    }
                }).setNegativeButton("Cancel",null).setCancelable(false).create().show();

            case 2:

                default:
                    return super.onContextItemSelected(item);

        }


    }*/
}
