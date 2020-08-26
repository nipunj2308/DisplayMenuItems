package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DefineCategories extends AppCompatActivity {
    private EditText CategoryName;
    private Button AddCategoryToDatabase;
    private TextView DefineCategories, DefinedCategories;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<String> categoryNames;
    private RecyclerAdapterToDisplayDefinedCategories recyclerAdapterToDisplayDefinedCategories;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_categories);
        categoryNames = new ArrayList<>();
        sqLiteHelper = new SQLiteHelper(this);
        DefineCategories = findViewById(R.id.DefineCategoriesTextView);
        DefinedCategories = findViewById(R.id.DefinedCategoriesTextView);
        CategoryName = findViewById(R.id.defineCategoryName);
        recyclerView = findViewById(R.id.DisplayCategoriesRecyclerView);
        AddCategoryToDatabase = findViewById(R.id.AddCategory);
        DisplayCategory();
        recyclerAdapterToDisplayDefinedCategories = new RecyclerAdapterToDisplayDefinedCategories(categoryNames);
        recyclerView.setAdapter(recyclerAdapterToDisplayDefinedCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);







        AddCategoryToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean DataInserted =sqLiteHelper.AddCategory(CategoryName.getText().toString());
               if(DataInserted == true)
               {
                   Toast.makeText(DefineCategories.this, "data added successfully", Toast.LENGTH_SHORT).show();
                   categoryNames.add(CategoryName.getText().toString());
                   recyclerAdapterToDisplayDefinedCategories.notifyDataSetChanged();
                   CategoryName.setText("");

               }
               else
               {
                   Toast.makeText(DefineCategories.this,"unsuccessful...try again", Toast.LENGTH_SHORT).show();

               }

            }
        });



    }


    public void DisplayCategory()
    {
        Cursor cursor = sqLiteHelper.DisplayCategoryNames();
        if(cursor.getCount() == 0)
        {
             DefinedCategories.setText(" No Categories Defined");
        }
        else {
            while (cursor.moveToNext()) {
                categoryNames.add(cursor.getString(0));
            }
        }
    }
}
