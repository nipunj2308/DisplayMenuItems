package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayCategoryArray extends AppCompatActivity {

    private TextView displayArrayList;
    MainActivity mainActivity;
    DefineCategoriesRecyclerAdapter defineCategoriesRecyclerAdapter;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category_array);
        displayArrayList = findViewById(R.id.DisplayCategoriesArrayTextView);
        mainActivity = new MainActivity();
        sqLiteHelper = new SQLiteHelper(this);
            sqLiteDatabase = sqLiteHelper.getReadableDatabase();
            String rawquery = " SELECT categoryName FROM CategoriesTable";
            Cursor cursor = sqLiteDatabase.rawQuery(rawquery, new String[]{});

            if (cursor != null) {
                 cursor.moveToFirst();
                StringBuilder stringBuilder = new StringBuilder();
                do {
                    String categoryName = cursor.getString(0);
                    stringBuilder.append(categoryName + "\n");

                }
                while (cursor.moveToNext());
                displayArrayList.setText(stringBuilder.toString());
            }
            else
            {
                displayArrayList.setText(" No categories defined");
            }
        }

        /* try {
            if (!defineCategoriesRecyclerAdapter.categories.isEmpty()) {
                StringBuilder stringBuilder =new StringBuilder();
                for (String i: defineCategoriesRecyclerAdapter.categories) {
                    stringBuilder.append(i+"\n");
                }
                displayArrayList.setText(stringBuilder.toString());

            }
            else {
                displayArrayList.setText("No categories defined");
            }
        }
        catch (NullPointerException e)
        {


            e.printStackTrace();
        }*/


}
