package com.example.itemsdatabasestartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrackOrdersRecyclerView extends RecyclerView.Adapter<TrackOrdersRecyclerView.ViewHolder> {

private ArrayList<ModelClassForOrderDetailsFireBase> ActiveOrdersList;
private Context context;



public TrackOrdersRecyclerView(ArrayList<ModelClassForOrderDetailsFireBase> ActiveOrdersList)
{
    this.ActiveOrdersList = ActiveOrdersList;


}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.track_orders_recyclerview_layout_2,parent,false);
    ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.OrderNo.setText(String.valueOf(ActiveOrdersList.get(position).getOrderNumber()));
    holder.itemName.setText(ActiveOrdersList.get(position).getItemName());
    holder.qty.setText("X "+ActiveOrdersList.get(position).getQuantity());
    holder.counter.setText(String.valueOf(ActiveOrdersList.get(position).getCounter()));
    holder.time.setText(ActiveOrdersList.get(position).getOrderPreparationTime());
    holder.status.setText(ActiveOrdersList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return ActiveOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView OrderNo, itemName, qty, counter, time, status;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        OrderNo = itemView.findViewById(R.id.TrackOrdersOrderNo);
        itemName = itemView.findViewById(R.id.TrackOrdersItemName);
        qty = itemView.findViewById(R.id.TrackOrdersQty);
        counter = itemView.findViewById(R.id.TrackOrdersCounter);
        time = itemView.findViewById(R.id.TrackOrdersTime);
        status = itemView.findViewById(R.id.TrackOrdersStatus);
    }
}
}