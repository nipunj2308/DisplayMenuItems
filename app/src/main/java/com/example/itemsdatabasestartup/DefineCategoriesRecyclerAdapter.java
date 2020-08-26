package com.example.itemsdatabasestartup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DefineCategoriesRecyclerAdapter extends RecyclerView.Adapter<DefineCategoriesRecyclerAdapter.ViewHolder> {

public final ArrayList<ModelClassForCategories> categories;
private SQLiteHelper sqLiteHelper;
 private SQLiteDatabase sqLiteDatabase;
 Context context;

public DefineCategoriesRecyclerAdapter(ArrayList<ModelClassForCategories> categories)
{
    this.categories = categories;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    context = parent.getContext();
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.define_categories_list_layout,parent,false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;




    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.CategoryName.setText(categories.get(position).getCategoryName());
     holder.CategoriesSerialNo.setText(Integer.toString(position+1));
    }



    @Override
    public int getItemCount() {
        return categories.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder {

    EditText CategoryName;
    Button DeleteCategory ,  Addcategory;
    TextView CategoriesSerialNo;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           CategoryName = itemView.findViewById(R.id.defineCategoryName);
           DeleteCategory = itemView.findViewById(R.id.DeleteCategory);
           CategoriesSerialNo = itemView.findViewById(R.id.CategoriesSerialNo);
           Addcategory = itemView.findViewById(R.id.AddCategory);
           DeleteCategory.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   // check if the code is working or not before using
                   int deletedRows = sqLiteHelper.DeleteCategory(categories.get(getAdapterPosition()).getCategoryName());
                   if (deletedRows == 0)
                   {
                       Toast.makeText(context, "Unsuccesful..", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Toast.makeText(context, categories.get(getAdapterPosition()).getCategoryName()+"deleted, "+deletedRows+" rows affected", Toast.LENGTH_SHORT).show();
                   }
                   categories.remove(getAdapterPosition());
                   notifyDataSetChanged();
               }
           });

           Addcategory.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                   ContentValues contentValues = new ContentValues();
                   //contentValues.put("categoryName",CategoryName.getText().toString());
                   contentValues.put("categoryName",CategoryName.getText().toString());
                   sqLiteDatabase.insert("CategoriesTable",null,contentValues);

               }
           });

           CategoryName.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   categories.get(getAdapterPosition()).setCategoryName(CategoryName.getText().toString());

               }

               @Override
               public void afterTextChanged(final Editable editable) {


                  /*sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("ItemDetailsDataBase",null);
                   String sqlQuery="CREATE TABLE IF NOT EXISTS CategoriesTable (categoryID INTEGER primary key, categoryName Varchar(30) NOT NULL)";
                   sqLiteDatabase.execSQL(sqlQuery);*/
                   sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                   ContentValues contentValues = new ContentValues();
                   //contentValues.put("categoryName",CategoryName.getText().toString());
                   contentValues.put("categoryName",editable.toString());
                   sqLiteDatabase.insert("CategoriesTable",null,contentValues);

                   }




           });
       }
   }



}
