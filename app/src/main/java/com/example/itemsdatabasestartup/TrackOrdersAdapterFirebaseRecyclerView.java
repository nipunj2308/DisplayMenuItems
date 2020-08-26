package com.example.itemsdatabasestartup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import java.util.Collections;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;

public class TrackOrdersAdapterFirebaseRecyclerView extends FirebaseRecyclerAdapter<ModelClassFirebaseForActiveOrdersAdapter, TrackOrdersAdapterFirebaseRecyclerView.ViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TrackOrdersAdapterFirebaseRecyclerView(@NonNull FirebaseRecyclerOptions<ModelClassFirebaseForActiveOrdersAdapter> options) {
        super(options);
    }
    //FirebaseRecyclerOptions<ModelClassFirebaseForActiveOrdersAdapter> options;
   // SharedPreferences sharedPreferences;



    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelClassFirebaseForActiveOrdersAdapter model) {

      /*  holder.OrderNo.setText(model.getOrderNo());
        holder.itemName.setText(model.getItemName());
        holder.qty.setText(model.getQuantity());
        holder.counter.setText(model.getCounter());
        holder.time.setText(model.getTime());
        holder.status.setText(model.getStatus());
*/
        holder.OrderNo.setText(String.valueOf(model.getOrderNo()));
        holder.itemName.setText(model.getItemName());
        holder.qty.setText("X "+model.getQuantity());
        holder.counter.setText(String.valueOf(model.getCounter()));
        holder.time.setText(model.getPreparationTime());
        holder.status.setText(model.getStatus());
       // holder.Description.setText(model.getDescription());

       /* if (model.getStatus().equals("prepared"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Active orders",holder.ActiveOrders +1);
        }*/

       //https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically
       if (model.getStatus().equals("preparing"))
       {
           holder.status.setTextColor(Color.parseColor("#FF7F50"));
       }
       else if (model.getStatus().equals("prepared"))
       {
           holder.status.setTextColor(Color.parseColor("#138D75"));
       }




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //hi
        //sharedPreferences = context.getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        View view = LayoutInflater.from(context).inflate(R.layout.track_orders_recyclerview_layout_2,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView OrderNo, itemName, qty, counter, time, status;
        private int ActiveOrders;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNo = itemView.findViewById(R.id.TrackOrdersOrderNo);
            itemName = itemView.findViewById(R.id.TrackOrdersItemName);
            qty = itemView.findViewById(R.id.TrackOrdersQty);
            counter = itemView.findViewById(R.id.TrackOrdersCounter);
            time = itemView.findViewById(R.id.TrackOrdersTime);
            status = itemView.findViewById(R.id.TrackOrdersStatus);


            //SharedPreferences.Editor editor = sharedPreferences.edit();
           // ActiveOrders = sharedPreferences.getInt("Active Orders",0);


        }

    }
}
