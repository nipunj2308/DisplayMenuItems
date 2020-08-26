package com.example.itemsdatabasestartup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Expandable RecyclerView:     https://www.youtube.com/watch?v=hbMqd0XRN34

public class MenuItemsRecyclerAdapter extends RecyclerView.Adapter<MenuItemsRecyclerAdapter.ViewHolder>
{
    //private ArrayList categories , Names, Ids , counters, Prices;
    private ArrayList<ModelClassForItemDetails> itemDetails;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    Context context;



public MenuItemsRecyclerAdapter(ArrayList<ModelClassForItemDetails> itemDetails)
{
   /* this.Ids = Ids;
    this.Names = Names;
    this.Prices = Prices;
    this.counters = counters;
    this.categories = categories;*/
    //this.Expanded = false;

    this.itemDetails = itemDetails;
}




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //Expanded = false;
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items_list_layout,parent,false);
        //new ViewHolder(viewType).CategoryName.setAdapter(CategoriesSpinnerAdapter);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    /* holder.SerialNo.setText(Integer.toString(position+1));
     holder.ItemId.setText(String.valueOf(Ids.get(position)));
     holder.ItemName.setText(String.valueOf(Names.get(position)));
     holder.price.setText(String.valueOf(Prices.get(position)));
     int spinnerAdapterPosition = holder.CategoriesSpinnerAdapter.getPosition(String.valueOf(categories.get(position)));
     holder.Counter.setText(String.valueOf(counters.get(position)));*/
    //holder.SerialNo.setText(Integer.toString(position+1));
    holder.ItemId.setText(String.valueOf(itemDetails.get(position).getID()));
    holder.ItemName.setText(itemDetails.get(position).getItemName());
    holder.price.setText(String.valueOf(itemDetails.get(position).getPrice()));
   // holder.ItemCategory.setText(String.valueOf(itemDetails.get(position).getCategory()));
    holder.Counter.setText(String.valueOf(itemDetails.get(position).getCounter()));
    int spinnerAdapterPosition = holder.CategoriesSpinnerAdapter.getPosition(itemDetails.get(position).getCategory());
        holder.CategoryName.setSelection(spinnerAdapterPosition);

        boolean isExpanded = itemDetails.get(position).isExpanded();
        holder.ExpandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        //holder.ItemId.setEnabled( isExpanded? true  : false);
        //holder.ItemName.setEnabled( isExpanded? true : false);
        holder.price.setEnabled( isExpanded? true  : false);
        holder.CategoryName.setEnabled( isExpanded? true  : false);
        holder.Counter.setEnabled( isExpanded? true  : false);
       // holder.ItemCategory.setEnabled( isExpanded? true : false);

     //


    }

    @Override
    public int getItemCount()
    {
        return itemDetails.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
            implements AdapterView.OnItemSelectedListener {

        private TextView SerialNo, ItemName , ItemId , ItemCategory;
        private EditText price, Counter;
        public Spinner CategoryName;
        private Button UpdateData , DeleteData;
        private LinearLayout linearLayout;
        private ConstraintLayout ExpandableLayout;
        ArrayAdapter<String> CategoriesSpinnerAdapter;
        private ArrayList<String> Categories;
        private String CategorySelected;
        int CurrentRecyclerViewItemId;
        float CurrentSavedPriceValue = 0;
        int CurrentSavedCounterValue = 0;
        String CurrentSavedCategoryName = "";

       // private int CounterNo, PriceValue;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            //SerialNo = itemView.findViewById(R.id.SerialNoOfMenuItemTextView);
            ItemName = itemView.findViewById(R.id.ItemNameDisplayTextview);
            ItemId = itemView.findViewById(R.id.itemIdTextview);
            price = itemView.findViewById(R.id.ItemPriceEditText);
            Counter = itemView.findViewById(R.id.ItemCounterEdiText);
            //ItemCategory = itemView.findViewById(R.id.CategoryNameTextView);
            CategoryName = itemView.findViewById(R.id.CategorySpinnerRecyclerView);
            Categories = new ArrayList<>();
            populateCategorySpinner(); // populate the spinner using this
            CategoriesSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Categories);
            CategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CategoryName.setAdapter(CategoriesSpinnerAdapter);
            CategoryName.setOnItemSelectedListener(this);

            CategoryName.setEnabled(false); // disable the spinner by default
            UpdateData = itemView.findViewById(R.id.UpdateButtonMenuList);
            //UpdateData.setEnabled(false);
            DeleteData = itemView.findViewById(R.id.DeleteButtonMenuList);
            linearLayout = itemView.findViewById(R.id.MenuItemsDisplayLinearLayout);
            ExpandableLayout = itemView.findViewById(R.id.ExpandableLayout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // setExpanded(!isExpanded());
                    ModelClassForItemDetails modelClassForItemDetails = itemDetails.get(getAdapterPosition());
                    modelClassForItemDetails.setExpanded(!modelClassForItemDetails.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                       /*ItemName.setEnabled(true);
                       ItemId.setEnabled(true);
                       price.setEnabled(true);
                       Counter.setEnabled(true);
                       CategoryName.setEnabled(true);*/

                }
            });
            //linearLayout.setOnCreateContextMenuListener(this);


            DeleteData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure ??").setMessage(" All the corresponding data related to " + itemDetails.get(getAdapterPosition()).getItemName() + " will be deleted").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //sqLiteHelper.DeleteItemDetails(String.valueOf(Ids.get(getAdapterPosition())));
                            /*Ids.remove(getAdapterPosition());
                            Names.remove(getAdapterPosition());
                            Prices.remove(getAdapterPosition());
                            counters.remove(getAdapterPosition());
                            categories.remove(getAdapterPosition());*/
                            sqLiteHelper.DeleteItemDetails(String.valueOf(itemDetails.get(getAdapterPosition()).getID()));
                            itemDetails.remove(getAdapterPosition());
                            if (itemDetails.isEmpty()) {
                                DisplayMenuItemsFragment displayMenuItemsFragment = new DisplayMenuItemsFragment();
                                //do something to change the text of DefineMenuItem textview
                            }
                            notifyDataSetChanged();



                        }
                    }).setNegativeButton("Cancel", null).setCancelable(false).create().show();
                }
            });

            UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure you want to update Data ?").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            boolean DataUpdated = sqLiteHelper.UpdateItemDetails(String.valueOf(itemDetails.get(getAdapterPosition()).getID()),
                                    itemDetails.get(getAdapterPosition()).getItemName(), Float.parseFloat(price.getText().toString()), Integer.parseInt(Counter.getText().toString()),
                                    CategorySelected);


                            //https://stackoverflow.com/questions/33789345/whats-better-notifydatasetchanged-or-notifyitemchanged-in-loop
                            if (DataUpdated == true) {
                                Toast.makeText(context, "Details of " + itemDetails.get(getAdapterPosition()).getItemName() + " updated successfully", Toast.LENGTH_SHORT).show();



                                itemDetails.get(getAdapterPosition()).setExpanded(false);
                                //ExpandableLayout.setVisibility(View.GONE);
                                //price.setEnabled(false);
                               // CategoryName.setEnabled(false);
                                //Counter.setEnabled(false);
                               // Counter.setText(Counter.getText().toString());
                               // price.setText(price.getText().toString());
                                //CategoryName.setSelection(CategoriesSpinnerAdapter.getPosition(CategorySelected));
                                itemDetails.get(getAdapterPosition()).setPrice(Float.parseFloat(price.getText().toString()));
                                itemDetails.get(getAdapterPosition()).setCounter(Integer.parseInt(Counter.getText().toString()));
                                itemDetails.get(getAdapterPosition()).setCategory(CategorySelected);
                                notifyItemChanged(getAdapterPosition());


                            }
                            else {
                                Toast.makeText(context, "Unable to update ...try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("No", null).setCancelable(false).create().show();


                }
            });




            price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //int id = itemDetails.get(getAdapterPosition()).getID();
                    //float Price = itemDetails.get(getAdapterPosition()).getPrice();
                    // int Counter = itemDetails.get(getAdapterPosition()).getCounter();
                    //String Category =itemDetails.get(getAdapterPosition()).getCategory();

                    //String sqlQuery = "select price , counter , categoryName from itemTally where id='" + id + "';";
                    // String sqlQuery1 = "select categoryName from itemTally where id ='"+id+"';";
                    //String sqlQuery2 = "select price from itemTally where id ='"+id+"';";
                    getCurrentSavedDatabaseValues();
                    try {
                        int currentCounterValue = Integer.parseInt(Counter.getText().toString().trim());
                        float currentPriceValue = Float.parseFloat(editable.toString().trim());
                       /* float PriceValue = 0;
                        int CounterValue = 0;
                        String CategoryName = "";

                        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
                        while (cursor.moveToNext()) {
                            PriceValue = cursor.getFloat(cursor.getColumnIndex(sqLiteHelper.KEY_Price));
                            CounterValue = cursor.getInt(cursor.getColumnIndex(sqLiteHelper.KEY_Counter));
                            CategoryName = cursor.getString(cursor.getColumnIndex(sqLiteHelper.Category_Name));
                        }*/
                        /*try
                        {*/
                        if (currentPriceValue != CurrentSavedPriceValue || currentCounterValue != CurrentSavedCounterValue
                                || !CategorySelected.equals(CurrentSavedCategoryName)) {

                            UpdateData.setEnabled(true);

                        } else {
                            UpdateData.setEnabled(false);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
            });


            Counter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    //int id = itemDetails.get(getAdapterPosition()).getID();
                    //float Price = itemDetails.get(getAdapterPosition()).getPrice();
                    // int Counter = itemDetails.get(getAdapterPosition()).getCounter();
                    //String Category =itemDetails.get(getAdapterPosition()).getCategory();

                    //String sqlQuery = "select price , counter , categoryName from itemTally where id='" + id + "';";
                    // String sqlQuery1 = "select categoryName from itemTally where id ='"+id+"';";
                    //String sqlQuery2 = "select price from itemTally where id ='"+id+"';";
                    getCurrentSavedDatabaseValues();
                    try {
                        int currentCounterValue = Integer.parseInt(editable.toString().trim());
                        float currentPriceValue = Float.parseFloat(price.getText().toString().trim());
                        //float PriceValue = 0;
                        /*int CounterValue = 0;
                        String CategoryName = "";

                        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
                        while (cursor.moveToNext()) {
                            PriceValue = cursor.getFloat(cursor.getColumnIndex(sqLiteHelper.KEY_Price));
                            CounterValue = cursor.getInt(cursor.getColumnIndex(sqLiteHelper.KEY_Counter));
                            CategoryName = cursor.getString(cursor.getColumnIndex(sqLiteHelper.Category_Name));
                        }*/

                        if (currentPriceValue != CurrentSavedPriceValue || currentCounterValue != CurrentSavedCounterValue
                                || !CategorySelected.equals(CurrentSavedCategoryName)) {

                            UpdateData.setEnabled(true);

                        } else {
                            UpdateData.setEnabled(false);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }

                }
            });

        }
            @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
            CategorySelected = adapterView.getItemAtPosition(i).toString();

               // int id = itemDetails.get(getAdapterPosition()).getID();
                //float Price = itemDetails.get(getAdapterPosition()).getPrice();
                // int Counter = itemDetails.get(getAdapterPosition()).getCounter();
                //String Category =itemDetails.get(getAdapterPosition()).getCategory();

                //String sqlQuery = "select price , counter , categoryName from itemTally where id='" + id + "';";
                // String sqlQuery1 = "select categoryName from itemTally where id ='"+id+"';";
                //String sqlQuery2 = "select price from itemTally where id ='"+id+"';";
                getCurrentSavedDatabaseValues();
                try {
                    int currentCounterValue = Integer.parseInt(Counter.getText().toString().trim());
                    float currentPriceValue = Float.parseFloat(price.getText().toString().trim());
                    /*float PriceValue = 0;
                    int CounterValue = 0;
                    String CategoryName = "";

                    Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
                    while (cursor.moveToNext()) {
                        PriceValue = cursor.getFloat(cursor.getColumnIndex(sqLiteHelper.KEY_Price));
                        CounterValue = cursor.getInt(cursor.getColumnIndex(sqLiteHelper.KEY_Counter));
                        CategoryName = cursor.getString(cursor.getColumnIndex(sqLiteHelper.Category_Name));
                    }*/

                    if (currentPriceValue != CurrentSavedPriceValue || currentCounterValue != CurrentSavedCounterValue
                            || !CategorySelected.equals(CurrentSavedCategoryName))
                    {
                        UpdateData.setEnabled(true);

                    }
                    else {
                        UpdateData.setEnabled(false);
                    }
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }


        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

      /*  @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.add(this.getAdapterPosition(),1,0,"Delete").setIcon(R.drawable.ic_delete_black_24dp);

            //contextMenu.add(this.getAdapterPosition(),2,1,"Update Details");

        }*/

        public void populateCategorySpinner() {
            //similar to DisplayCategory() method in DefineCategories class
            Cursor cursor = sqLiteHelper.DisplayCategoryNames();
            // ArrayList<String> categoryNames = new ArrayList<>();
            if (cursor.getCount() == 0) {
                Categories.add("Unavailable");
            } else {
                Categories.add(0,"UNASSIGNED");
                Categories.add(1,"MRP");
                while (cursor.moveToNext()) {
                    Categories.add(cursor.getString(0));
                }
            }
             cursor.close();

        }

        public void getCurrentSavedDatabaseValues()
        {
            //try {
                CurrentRecyclerViewItemId = itemDetails.get(getAdapterPosition()).getID();
                String sqlQuery = "select price , counter , categoryName from itemTally where id='" + CurrentRecyclerViewItemId + "';";
                CurrentSavedPriceValue = 0;
                CurrentSavedCounterValue = 0;
                CurrentSavedCategoryName = "";

                Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
                while (cursor.moveToNext()) {
                    CurrentSavedPriceValue = cursor.getFloat(cursor.getColumnIndex(sqLiteHelper.KEY_Price));
                    CurrentSavedCounterValue = cursor.getInt(cursor.getColumnIndex(sqLiteHelper.KEY_Counter));
                    CurrentSavedCategoryName = cursor.getString(cursor.getColumnIndex(sqLiteHelper.Category_Name));
                }
                cursor.close();
           /* }
            catch (ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }*/
        }

    }

   /* public  void DeleteMenuItem(int position)
    {
        Ids.remove(position);
        Names.remove(position);
        Prices.remove(position);
        counters.remove(position);
        categories.remove(position);
        notifyDataSetChanged();
    }*/




}
