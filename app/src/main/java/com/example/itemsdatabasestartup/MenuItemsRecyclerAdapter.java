package com.example.itemsdatabasestartup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



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
    holder.SerialNo.setText(Integer.toString(position+1));
    holder.ItemId.setText(String.valueOf(itemDetails.get(position).getID()));
    holder.ItemName.setText(itemDetails.get(position).getItemName());
    holder.price.setText(String.valueOf(itemDetails.get(position).getPrice()));
   // holder.ItemCategory.setText(String.valueOf(itemDetails.get(position).getCategory()));
    holder.Counter.setText(String.valueOf(itemDetails.get(position).getCounter()));
    int spinnerAdapterPosition = holder.CategoriesSpinnerAdapter.getPosition(itemDetails.get(position).getCategory());
        holder.CategoryName.setSelection(spinnerAdapterPosition);

        boolean isExpanded = itemDetails.get(position).isExpanded();
        holder.ExpandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.ItemId.setEnabled( isExpanded? true  : false);
        holder.ItemName.setEnabled( isExpanded? true : false);
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SerialNo = itemView.findViewById(R.id.SerialNoOfMenuItemTextView);
            ItemName = itemView.findViewById(R.id.ItemNameDisplayTextview);
            ItemId = itemView.findViewById(R.id.itemIdTextview);
            price = itemView.findViewById(R.id.ItemPriceEditText);
            Counter = itemView.findViewById(R.id.ItemCounterEdiText);
            //ItemCategory = itemView.findViewById(R.id.CategoryNameTextView);
            CategoryName =itemView.findViewById(R.id.CategorySpinnerRecyclerView);
            Categories = new ArrayList<>();
            populateCategorySpinner(); // populate the spinner using this
            CategoriesSpinnerAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, Categories);
            CategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CategoryName.setAdapter(CategoriesSpinnerAdapter);
            CategoryName.setOnItemSelectedListener(this);

           CategoryName.setEnabled(false); // disable the spinner by default
            UpdateData = itemView.findViewById(R.id.UpdateButtonMenuList);
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
                    builder.setTitle("Are you sure ??").setMessage(" All the corresponding data related to this item will be deleted").setPositiveButton("yes", new DialogInterface.OnClickListener() {
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
                            notifyDataSetChanged();


                        }
                    }).setNegativeButton("Cancel",null).setCancelable(false).create().show();
                }
            });

            UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //sqLiteHelper.UpdateItemDetails(Ids.get())

                }
            });
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
                while (cursor.moveToNext()) {
                    Categories.add(0,"Null");
                    Categories.add(cursor.getString(0));
                }
            }


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
