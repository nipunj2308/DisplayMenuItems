package com.example.itemsdatabasestartup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrawerRecyclerView extends RecyclerView.Adapter<DrawerRecyclerView.Viewholder>

{
    private ArrayList<String> categoryList;
    private Context context;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ModelClassForItemDetails modelClassForItemDetails ;


    public DrawerRecyclerView(ArrayList<String> categoryList)
    {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_recyclerview_parent_layout,parent,false);
        Viewholder viewholder = new Viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String CategoryName = categoryList.get(position);
        holder.CategoryName.setText(CategoryName);
        ArrayList<ModelClassForItemDetails> itemDetails = new ArrayList<>();
        Cursor cursor = sqLiteHelper.displayItemByCategory(CategoryName);
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
            int id= cursor.getInt(0);
            String itemname= cursor.getString(1);
            float price =cursor.getFloat(2);
            modelClassForItemDetails = new ModelClassForItemDetails(id,itemname,price);
            itemDetails.add(modelClassForItemDetails );
            }
        }

        DrawerChildRecyclerView drawerChildRecyclerView = new DrawerChildRecyclerView(itemDetails);
        holder.ChildRecyclerView.setAdapter(drawerChildRecyclerView);
        holder.ChildRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.ChildRecyclerView.setHasFixedSize(true);


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView CategoryName;
        private RecyclerView ChildRecyclerView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            CategoryName = itemView.findViewById(R.id.categoryNameDrawerRecyclerView);
            ChildRecyclerView = itemView.findViewById(R.id.drawerChildrecyclerView);
        }
    }
}
