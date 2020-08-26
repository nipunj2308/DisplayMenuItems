package com.example.itemsdatabasestartup;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;

public class RecyclerViewForSubletActiveOrders extends RecyclerView.Adapter<RecyclerViewForSubletActiveOrders.ViewHolder> {

    private ArrayList<ModelClassForOrderDetailsFireBase> ActiveOrdersList;
    private Context context;
    private SharedPreferences sharedPreferences;


    public RecyclerViewForSubletActiveOrders(ArrayList<ModelClassForOrderDetailsFireBase> ActiveOrdersList)
    {
        this.ActiveOrdersList = ActiveOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.sublet_layout_active_orders_recycler_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.OrderNo.setText(String.valueOf(ActiveOrdersList.get(position).getOrderNumber()));
        holder.itemName.setText(ActiveOrdersList.get(position).getItemName());
        holder.qty.setText("X "+ActiveOrdersList.get(position).getQuantity());
        holder.Counter.setText(String.valueOf(ActiveOrdersList.get(position).getCounter()));
        holder.Time.setText(ActiveOrdersList.get(position).getOrderPreparationTime());
        holder.Status.setText(ActiveOrdersList.get(position).getStatus());
        holder.Description.setText(ActiveOrdersList.get(position).getDescriptions());

    }

    @Override
    public int getItemCount() {
        return ActiveOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView OrderNo, Description, itemName, qty, Counter,Status,Time;
        private Button Prepared, Delivered;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            OrderNo = itemView.findViewById(R.id.SubletActiveOrdersOrderNo);
            itemName= itemView.findViewById(R.id.SubletActiveOrdersItemName);
            qty = itemView.findViewById(R.id.SubletActiveOrdersQty);
            Counter = itemView.findViewById(R.id.SubletActiveOrdersCounter);
            Status = itemView.findViewById(R.id.SubletActiveOrdersStatus);
            Time = itemView.findViewById(R.id.SubletActiveOrdersTime);
            Description = itemView.findViewById(R.id.SubletActiveOrdersDescription);
            Prepared = itemView.findViewById(R.id.SubletActiveOrdersPreparedButton);
            Delivered = itemView.findViewById(R.id.SubletActiveOrdersDeliveredButton);

            Prepared.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String messageKey = ActiveOrdersList.get(getAdapterPosition()).getMessageKey();
                    int position = ActiveOrdersList.get(getAdapterPosition()).getItemPositionInArrray();
                    sharedPreferences = context.getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
                    String CurrentAdminId = sharedPreferences.getString("current Admin ID", "");
                    HashMap<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("AdminID/"+CurrentAdminId+"/Active Orders/"+messageKey+"/Item "+position+" status", "prepared");

                    FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
                    //notifyItemChanged(getAdapterPosition());
                }
            });


            Delivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                }
            });
        }
    }
}
