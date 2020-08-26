package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewDatabaseEntries extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<ItemTally> databaseEntries;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private TextView databaseContent;
    private DatabaseEntriesRecyclerAdapter databaseEntriesRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sqLiteHelper = new SQLiteHelper(this);
        recyclerView = findViewById(R.id.DatabaseEntriesRecyclerView);
        databaseContent = findViewById(R.id.DatabaseContentTextView);
        databaseEntries = new ArrayList<>();
        populatedatabaseEntriesArrayList();
        databaseEntriesRecyclerAdapter = new DatabaseEntriesRecyclerAdapter(databaseEntries);
        recyclerView.setAdapter(databaseEntriesRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

    }



    public ArrayList<ItemTally> populatedatabaseEntriesArrayList()
    {
        String currentDate = getCurrentDate();
        String currentMonth = getCurrentMonth();
        String formattedCurrentMonth = currentMonth.replaceAll("/","");
        String formattedCurrentDate = currentDate.replaceAll("/","");
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        //String query = "Select itemName, '"+currentMonth+"', '"+currentDate+"' from itemTally ;";
        String query = "Select itemName, TwentyFour, EightHundredTwenty from itemTally ;";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor!=null)
        {
            while(cursor.moveToNext())
            {
                String itemName = cursor.getString(0);
                int MonthQty= cursor.getInt(1);
                int DateQty = cursor.getInt(2);
                ItemTally itemTally = new ItemTally(itemName,MonthQty,DateQty);
                databaseEntries.add(itemTally);

            }
        }
       return databaseEntries;

    }




    public String getCurrentMonth()
    {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM/yy");
        String CurrentMonth = date.format(calendar.getTime());
        return CurrentMonth;
    }

    public String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd");
        String CurrentDate = date.format(calendar.getTime());
        return CurrentDate;
    }
}
