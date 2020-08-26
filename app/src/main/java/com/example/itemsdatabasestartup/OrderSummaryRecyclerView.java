package com.example.itemsdatabasestartup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderSummaryRecyclerView extends RecyclerView.Adapter<OrderSummaryRecyclerView.ViewHolder>

{
    public static ArrayList<Double> itemTotals = new ArrayList<>();
    public static ArrayList<ModelClassForOrdersList> itemDetails;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    Context context;
    double inflateAmount;


    public OrderSummaryRecyclerView(double inflateAmount, ArrayList<ModelClassForOrdersList> itemDetails)
    {
        this.inflateAmount = inflateAmount;
        this.itemDetails = itemDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_summary_recyclerview_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {


        String ItemName = itemDetails.get(position).getName();
        int Qty = itemDetails.get(position).getQuantity();
        double Total = itemDetails.get(position).getTotal();
          // consider only the entries which do not have any empty values;
        if ( ItemName !=null &&  Qty !=0 && Total != 0 )
        {

            holder.itemName.setText(ItemName);

            holder.itemQty.setText(String.valueOf(Qty));

            //check the category of the item , if the item is under MRP category then dont add any amount in that to make up...
            //...for the SMS cost
            String query = "select categoryName from itemTally where itemName = '" + ItemName + "';";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            String ItemCategory = "";
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    ItemCategory = cursor.getString(0);

                    if (ItemCategory.equals("MRP")) {
                        holder.itemTotal.setText(String.valueOf(Total));
                        itemTotals.add(Total);
                    } else {
                        double ActualTotal = Total + inflateAmount;
                        holder.itemTotal.setText(String.valueOf(ActualTotal));
                        itemTotals.add(Total);
                    }
                }
            }

        }
        else
        {
           holder.invoiceSummaryRecyclerViewLinearLayout.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount()
    {
        return itemDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView itemName , itemQty, itemTotal;
        private LinearLayout invoiceSummaryRecyclerViewLinearLayout;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemName = itemView.findViewById(R.id.OrderSummaryItemName);
            itemQty = itemView.findViewById(R.id.OrderSummaryItemQty);
            itemTotal = itemView.findViewById(R.id.OrderSummaryItemTotal);
            invoiceSummaryRecyclerViewLinearLayout = itemView.findViewById(R.id.invoiceSummaryRecyclerViewLinearLayout);

        }
    }
}
