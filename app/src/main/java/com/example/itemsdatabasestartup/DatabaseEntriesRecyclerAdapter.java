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

public class DatabaseEntriesRecyclerAdapter extends RecyclerView.Adapter<DatabaseEntriesRecyclerAdapter.ViewHolder> {

    private ArrayList<ItemTally> databaseEntries;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    Context context;

    public DatabaseEntriesRecyclerAdapter(ArrayList<ItemTally> databaseEntries)
    {
        this.databaseEntries = databaseEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.database_entires_item_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        /*if (databaseEntries.get(position).getCurrentDayTally()!=0 && databaseEntries.get(position).getCurrentMonthTally()!=0)
        {*/
        holder.name.setText(databaseEntries.get(position).getItemName());
           holder.month.setText(String.valueOf(databaseEntries.get(position).getCurrentMonthTally()));
            holder.day.setText(String.valueOf(databaseEntries.get(position).getCurrentDayTally()));
        //}

    }

    @Override
    public int getItemCount() {
        return databaseEntries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name,month, day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemNameTextView);

            month = itemView.findViewById(R.id.itemCountInMonth);
            day = itemView.findViewById(R.id.itemCountInCurrentDate);
        }
    }
}
