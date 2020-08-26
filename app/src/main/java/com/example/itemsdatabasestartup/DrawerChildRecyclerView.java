package com.example.itemsdatabasestartup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrawerChildRecyclerView extends RecyclerView.Adapter<DrawerChildRecyclerView.ViewHolder> {


    private ArrayList<ModelClassForItemDetails> itemsListByCategory;
    private Context context;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ModelClassForItemDetails modelClassForItemDetails ;

    public DrawerChildRecyclerView(ArrayList<ModelClassForItemDetails> itemsListByCategory)
    {
        this.itemsListByCategory = itemsListByCategory;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_recyclerview_child_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       if (!itemsListByCategory.isEmpty()) {
           holder.itemId.setText(String.valueOf(itemsListByCategory.get(position).getID()));

           holder.itemPrice.setText(String.valueOf(itemsListByCategory.get(position).getPrice()));
           holder.itemName.setText(String.valueOf(itemsListByCategory.get(position).getItemName()));
       }
    }

    @Override
    public int getItemCount() {
        return itemsListByCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView itemName , itemId , itemPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemId = itemView.findViewById(R.id.itemIdTextview);
            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemPrice = itemView.findViewById(R.id.itemPriceTextview);
        }
    }
}
