package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DefineCategoriesRecyclerAdapter recyclerAdapter;
    public ArrayList<ModelClassForCategories> categories;
    private Button addCategory, displayCategories ;
    private EditText categoryName;
    private SQLiteDatabase sqLiteDatabase;
    String CategoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCategory = findViewById(R.id.addCategoryFieldbutton);
        displayCategories = findViewById(R.id.displayCategoriesButton);
        categoryName = findViewById(R.id.defineCategoryName);
        recyclerView = findViewById(R.id.DefineCategoriesRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        categories = categoriesName();
        recyclerAdapter = new DefineCategoriesRecyclerAdapter(categories);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            CategoryName = categoryName.getText().toString();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }


        displayCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , DisplayCategoryArray.class);
                startActivity(intent);
            }
        });

    }


public ArrayList<ModelClassForCategories> categoriesName()
{
final ArrayList<ModelClassForCategories> categoryNames = new ArrayList<>(10);
    final ModelClassForCategories modelClass = new ModelClassForCategories();

      modelClass.setCategoryName("");
      categoryNames.add(modelClass);
addCategory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


            categoryNames.add(new ModelClassForCategories());
            recyclerAdapter.notifyDataSetChanged();


    }
});



return categoryNames;
}


}

