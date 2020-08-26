package com.example.itemsdatabasestartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersListRecyclerView extends RecyclerView.Adapter<OrdersListRecyclerView.ViewHolder>
{

    private ArrayList<ModelClassForOrderDetailsFireBase> orderDetailsList;
    private Context context;


    public OrdersListRecyclerView(ArrayList<ModelClassForOrderDetailsFireBase> orderDetailsList)
    {
        this.orderDetailsList = orderDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.full_orders_list_summary_recycler_view_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.OrderNoTextView.setText(orderDetailsList.get(position).getOrderNo());
        holder.CustomerNameTextView.setText(orderDetailsList.get(position).getCustomerName());
        holder.CustomerContactTextView.setText(orderDetailsList.get(position).getCustomerContact());
        holder.TimeTextView.setText(orderDetailsList.get(position).getTime());

        String itemNamesString = orderDetailsList.get(position).getItemNames();
        String itemNames[] = itemNamesString.split(",");

        String quantitiesString = orderDetailsList.get(position).getQuantities();
        String quantities[]= quantitiesString.split(",");

        String itemCountersString = orderDetailsList.get(position).getCounters();
        String counters[] = itemCountersString.split(",");

        String preparationTimesString = orderDetailsList.get(position).getOrderPreparationTime();
        String preparationTimes[] = preparationTimesString.split(",");


        StringBuilder item_Names = new StringBuilder();
        StringBuilder item_quantities = new StringBuilder();
        StringBuilder item_Counters = new StringBuilder();
        StringBuilder item_PreparationTime = new StringBuilder();
        StringBuilder XSign = new StringBuilder();


        for (int i=0; i<itemNames.length;i++)
        {
            item_Names.append(itemNames[i]+"\n");
            XSign.append("X\n");
            item_quantities.append(quantities[i]+"\n");
            item_Counters.append(counters[i]+"\n");
            item_PreparationTime.append(preparationTimes[i]+"\n");

        }


        holder.ItemNameTextView.setText(item_Names.toString());
        holder.QuantityTextView.setText(item_quantities.toString());
        holder.CounterTextview.setText(item_Counters.toString());
        holder.TimeTakenTextView.setText(item_PreparationTime.toString());
        holder.XSignTextView.setText(XSign.toString());
        holder.OrderTotal.setText(orderDetailsList.get(position).getOrderTotal());

        boolean isExpanded = orderDetailsList.get(position).isExpanded();
        holder.Row2.setVisibility(isExpanded? View.VISIBLE : View.GONE);
        holder.Row3.setVisibility(isExpanded? View.VISIBLE : View.GONE);
        holder.Row4.setVisibility(isExpanded? View.VISIBLE : View.GONE);






    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ItemNameTextView,XSignTextView,QuantityTextView,CounterTextview,TimeTakenTextView;
        TextView OrderNoTextView,CustomerNameTextView,CustomerContactTextView,TimeTextView,OrderTotal;
        TableRow Row1,Row2,Row3,Row4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemNameTextView = itemView.findViewById(R.id.ItemNameTextView);
            XSignTextView = itemView.findViewById(R.id.XSignTextView);
            QuantityTextView = itemView.findViewById(R.id.QuantityTextView);
            CounterTextview = itemView.findViewById(R.id.CounterTextview);
            TimeTakenTextView = itemView.findViewById(R.id.TimeTakenTextView);
            OrderNoTextView = itemView.findViewById(R.id.OrderNoTextView);
            CustomerNameTextView =itemView.findViewById(R.id.CustomerNameTextView);
            CustomerContactTextView = itemView.findViewById(R.id.CustomerContactTextView);
            TimeTextView = itemView.findViewById(R.id.TimeTextView);
            OrderTotal = itemView.findViewById(R.id.OrderTotalAmountTextView);
            Row1 = itemView.findViewById(R.id.Row1);
            Row2 = itemView.findViewById(R.id.Row2);
            Row3 = itemView.findViewById(R.id.Row3);
            Row4 = itemView.findViewById(R.id.Row4);

            OrderNoTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelClassForOrderDetailsFireBase modelClass = orderDetailsList.get(getAdapterPosition());
                    modelClass.setExpanded(!modelClass.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });



        }
    }
}
