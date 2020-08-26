package com.example.itemsdatabasestartup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class RecyclerViewForOrdersSummary  extends RecyclerView.Adapter<RecyclerViewForOrdersSummary.ViewHolder> {

    private ArrayList<ModelClassForSalesAndOrderSummary> OrderDatesList;
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqliteHelper;
    private Context context;

    public RecyclerViewForOrdersSummary(ArrayList<ModelClassForSalesAndOrderSummary> OrderDatesList)
    {
        this.OrderDatesList = OrderDatesList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        sqliteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqliteHelper.getWritableDatabase();

        View view = LayoutInflater.from(context).inflate(R.layout.orders_list_summary_recyclerview_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        boolean isExpanded = OrderDatesList.get(position).isExpanded();
        holder.ExpandableLayout.setVisibility(isExpanded? View.VISIBLE : View.GONE);
        BillDisplayAndGeneration billDisplayAndGeneration = new BillDisplayAndGeneration();

        try {
            if (OrderDatesList.get(position).getDate().equals("Monthly Summary"))
            {
                holder.dateTextView.setText("Monthly Summary");
                holder.tillDateTime.setVisibility(View.VISIBLE);
                holder.tillDateTime.setText("for " +billDisplayAndGeneration.getCurrentMonth()+" till "+billDisplayAndGeneration.getCurrentDateAndMonth());
                String query = "Select SUM("+sqliteHelper.TotalOrders +") from " + sqliteHelper.TABLE_NAME3 + ";";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null)
                {
                    while (cursor.moveToNext()) {
                        int TotalOrders = cursor.getInt(0);
                       // BigDecimal bd = new BigDecimal(TotalOrders).setScale(2, RoundingMode.UP);
                       // Double FormattedTotalOrders = bd.doubleValue();
                        holder.TotalOrdersTextView.setText("Total Orders :"+String.valueOf(TotalOrders));

                    }
                }
            }

            else {

                if (OrderDatesList.get(position).getDate().equals(billDisplayAndGeneration.getCurrentDateAndMonth()))
                {
                    holder.tillDateTime.setVisibility(View.VISIBLE);
                    holder.tillDateTime.setText("till " + billDisplayAndGeneration.getCurrentTime());
                }
                else
                {
                    holder.tillDateTime.setVisibility(View.GONE);
                }
                String Date = OrderDatesList.get(position).getDate();
                holder.dateTextView.setText(Date);
                int firstPosition = Date.indexOf("-");
                String dateStringForWordConversion = Date.substring(0, firstPosition);
                String dateInWords = BillDisplayAndGeneration.toWords(dateStringForWordConversion);
                //String query = "Select "+sqliteHelper.TotalSale +" from " + sqliteHelper.TABLE_NAME3 + " where "+sqliteHelper.Date+" = '"+dateInWords+"';";
                String query = "Select "+sqliteHelper.TotalOrders +" from " + sqliteHelper.TABLE_NAME3 + ";";


                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null)
                {
                    while (cursor.moveToNext())
                    {
                        int TotalOrders = cursor.getInt(0);
                        holder.TotalOrdersTextView.setText("Total Orders :" +String.valueOf(TotalOrders));

                    }
                }


            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount()
    {
        return OrderDatesList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView, tillDateTime;
        public LinearLayout dateLinearLayout;
        public Button viewDetailsOfCurrentDate;
        public TextView TotalOrdersTextView;
        public LinearLayout ExpandableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.DateDisplayTextView);
            viewDetailsOfCurrentDate = itemView.findViewById(R.id.ViewOrderDetailsForCurrentDateButton);
            TotalOrdersTextView = itemView.findViewById(R.id.TotalOrdersTextView);
            ExpandableLayout = itemView.findViewById(R.id.ExpandableLayout);
            dateLinearLayout = itemView.findViewById(R.id.DateDisplayLinearLayout);
            tillDateTime =itemView.findViewById(R.id.tillTimeTextView);

            dateLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelClassForSalesAndOrderSummary modelClass = OrderDatesList.get(getAdapterPosition());
                    modelClass.setExpanded(!modelClass.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            viewDetailsOfCurrentDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, FullOrdersListSummary.class);
                    intent.putExtra("Selected Date", OrderDatesList.get(getAdapterPosition()).getDate());
                    context.startActivity(intent);

                }

            });


        }
    }
}
