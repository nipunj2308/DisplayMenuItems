package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayCategoryArray extends AppCompatActivity {

    private TextView displayitemid , displayitemname, displayprice, displaycounter, displaycategory;
    MainActivity mainActivity;
    DefineCategoriesRecyclerAdapter defineCategoriesRecyclerAdapter;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category_array);
        //displayitemid = findViewById(R.id.DisplayId);
        displayitemname = findViewById(R.id.DisplayItemNameArray);
        //displayprice = findViewById(R.id.DisplayPriceArray);
        //displaycounter = findViewById(R.id.DisplayCounterArray);
        displaycategory = findViewById(R.id.DisplayCategoryArray);
        mainActivity = new MainActivity();
        sqLiteHelper = new SQLiteHelper(this);
            sqLiteDatabase = sqLiteHelper.getReadableDatabase();
            String rawquery = " SELECT * FROM itemTally";
            Cursor cursor = sqLiteDatabase.rawQuery(rawquery, new String[]{});

            if (cursor != null) {
                 cursor.moveToFirst();
                //StringBuilder stringBuilder1 = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();
                //StringBuilder stringBuilder3 = new StringBuilder();
                //StringBuilder stringBuilder4 = new StringBuilder();
                StringBuilder stringBuilder5 = new StringBuilder();
                do {
                    String id = cursor.getString(0);
                   // stringBuilder1.append(id + "\n");

                    String name = cursor.getString(1);
                    stringBuilder2.append(name + "\n");

                    String price = cursor.getString(2);
                   // stringBuilder3.append(price + "\n");

                    String Counter = cursor.getString(3);
                    //stringBuilder4.append(Counter + "\n");

                    String Category = cursor.getString(4);
                    stringBuilder5.append(Category + "\n");

                }
                while (cursor.moveToNext());
               // displayitemid.setText(stringBuilder1.toString());
                displayitemname.setText(stringBuilder2.toString());
               // displayprice.setText(stringBuilder3.toString());
               // displaycounter.setText(stringBuilder4.toString());
                displaycategory.setText(stringBuilder5.toString());
            }
           /* else
            {
                displayitemList.setText(" No categories defined");
            }*/
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
