package com.example.itemsdatabasestartup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterToDisplayDefinedCategories extends RecyclerView.Adapter<RecyclerAdapterToDisplayDefinedCategories.ViewHolder> {

    private ArrayList categories;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    Context context;

    public RecyclerAdapterToDisplayDefinedCategories(ArrayList categories)
    {
        this.categories  = categories;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


       context = parent.getContext();
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.defined_categories_listlayout,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.SerialNo.setText(Integer.toString(position+1));
        holder.CategoryName.setText(String.valueOf(categories.get(position)));


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CategoryName;
        TextView SerialNo ;
        ImageButton DeleteCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryName = itemView.findViewById(R.id.CategoryName);
            SerialNo = itemView.findViewById(R.id.CategoriesSerialNo);


            DeleteCategory = itemView.findViewById(R.id.DeleteDefinedCategory);
            DeleteCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(" Are you sure you want to delete category??")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //sqLiteHelper.DeleteCategory()
                            sqLiteHelper.DeleteCategory(String.valueOf(categories.get(getAdapterPosition())));
                            categories.remove(getAdapterPosition());
                            notifyDataSetChanged();




                        }
                    }).setNegativeButton("cancel", null)
                            .setCancelable(false).create().show();

                }
            });


        }
    }


}
