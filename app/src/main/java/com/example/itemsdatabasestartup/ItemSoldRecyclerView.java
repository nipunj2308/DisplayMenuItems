package com.example.itemsdatabasestartup;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemSoldRecyclerView extends RecyclerView.Adapter<ItemSoldRecyclerView.ViewHolder> {
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqliteHelper;
    private Context context;
    ArrayList<ModelClassForItemSold> itemSoldDetailsList;

    public ItemSoldRecyclerView(ArrayList<ModelClassForItemSold> itemSoldDetailsList)
    {
        this.itemSoldDetailsList = itemSoldDetailsList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        sqliteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqliteHelper.getReadableDatabase();
        View view = LayoutInflater.from(context).inflate(R.layout.item_sold_sales_summary_recycler_view_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemName.setText(itemSoldDetailsList.get(position).getItemName());
        holder.itemPrice.setText(String.valueOf(itemSoldDetailsList.get(position).getItemPrice()));
        holder.itemRevenue.setText(String.valueOf(itemSoldDetailsList.get(position).getItemRevenue()));
        holder.itemCounter.setText(String.valueOf(itemSoldDetailsList.get(position).getCounterNo()));


    }

    @Override
    public int getItemCount() {
        return itemSoldDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName, itemPrice, itemRevenue, itemCounter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.ItemSoldRecyclerViewItemname);
            itemPrice = itemView.findViewById(R.id.ItemSoldRecyclerViewItemPrice);
            itemRevenue = itemView.findViewById(R.id.ItemSoldRecyclerViewItemRevenue);
            itemCounter = itemView.findViewById(R.id.ItemSoldRecyclerViewItemCounter);
        }
    }
}
