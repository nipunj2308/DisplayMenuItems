package com.example.itemsdatabasestartup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;

public class SubletActiveOrdersRecyclerViewAdapter extends FirebaseRecyclerAdapter<ModelClassFirebaseForActiveOrdersAdapter, SubletActiveOrdersRecyclerViewAdapter.ViewHolder> {



    private Context context;
    public SubletActiveOrdersRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<ModelClassFirebaseForActiveOrdersAdapter> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelClassFirebaseForActiveOrdersAdapter model) {
        holder.OrderNo.setText(String.valueOf(model.getOrderNo()));
        holder.itemName.setText(model.getItemName());
        holder.qty.setText("X "+model.getQuantity());
        holder.Counter.setText(String.valueOf(model.getCounter()));
        holder.Time.setText(model.getPreparationTime());
        holder.Status.setText(model.getStatus());
        holder.Description.setText(model.getDescription());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.sublet_layout_active_orders_recycler_view,parent,false);

        return new ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView OrderNo, Description, itemName, qty, Counter,Status,Time;
        private Button Prepared, Delivered;
        public ViewHolder(@NonNull View itemView) {

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
                public void onClick(View view)
                {
                   /* String messageKey = ActiveOrdersList.get(getAdapterPosition()).getMessageKey();
                    int position = ActiveOrdersList.get(getAdapterPosition()).getItemPositionInArrray();
                    sharedPreferences = context.getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
                    String CurrentAdminId = sharedPreferences.getString("current Admin ID", "");
                    HashMap<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("AdminID/"+CurrentAdminId+"/Active Orders/"+messageKey+"/Item "+position+" status", "prepared");

                    FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
                    //notifyItemChanged(getAdapterPosition());*/
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
