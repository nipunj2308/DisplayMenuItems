package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class FullSalesDetails extends AppCompatActivity {

    private RecyclerView itemSoldRecyclerView;
    private ArrayList<ModelClassForItemSold> itemSoldDetailsList = new ArrayList<>();
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqliteDatabase;
    private String SelectedDate;
    private TextView  TotalAmountCollected, TotalIncome, TotalGSTCollected, TopPopular, LeastPopular, TopRevenueItems, LeastRevenueItems;
    private TextView TotalTransactionAmountCollected, TotalOrdersPlaced;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_sales_details);
        sqLiteHelper = new SQLiteHelper(this);
        sqliteDatabase = sqLiteHelper.getReadableDatabase();
        itemSoldRecyclerView = findViewById(R.id.ItemSoldRecyclerView);
        TotalAmountCollected= findViewById(R.id.TotalAmountCollectedValueTextView);
        TotalGSTCollected = findViewById(R.id.TotalGSTCollectedValueTextView);
        TotalTransactionAmountCollected = findViewById(R.id.TotalTransactionAmountCollectedValueTextView);
        TopPopular = findViewById(R.id.Top3MostPopular);
        LeastPopular = findViewById(R.id.Least3Popular);
        TopRevenueItems = findViewById(R.id.Top3RevenueGeneratingItems);
        LeastRevenueItems = findViewById(R.id.Least3RevenueGeneratingItems);
        TotalOrdersPlaced=findViewById(R.id.TotalOrdersPlacedValueTextView);



        Bundle bundle = getIntent().getExtras();
        SelectedDate = bundle.getString("Selected Date");

        String SelectedDateInWords = SelectedDateInWords(SelectedDate);
        populateItemSoldDetailsList(SelectedDateInWords);

        fillTopSalesTable();

        ItemSoldRecyclerView itemSoldRecyclerViewAdapter = new ItemSoldRecyclerView(itemSoldDetailsList);
        itemSoldRecyclerView.setAdapter(itemSoldRecyclerViewAdapter);
        itemSoldRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemSoldRecyclerView.setHasFixedSize(false);



    }


    public void populateItemSoldDetailsList(String SelectedDate)
    {

        Cursor cursor = sqLiteHelper.DisplayItemSoldDetails(SelectedDate);

        if (cursor!=null)
        {
            while (cursor.moveToNext())
            {
                String itemName = cursor.getString(0);
                double itemPrice= cursor.getDouble(1);
                int itemQtySold = cursor.getInt(2);
                double itemRevenue = Double.valueOf(itemQtySold)*itemPrice;
                int itemCounter = cursor.getInt(3);
                ModelClassForItemSold modelClass = new ModelClassForItemSold(itemName,itemPrice,itemRevenue,itemCounter);
                itemSoldDetailsList.add(modelClass);

            }

        }

        cursor.close();

    }


    public void fillTopSalesTable()
    {
        BillDisplayAndGeneration billDisplayAndGeneration = new BillDisplayAndGeneration();

        //Total amount collected
        if(SelectedDate.equals("Monthly Summary"))
        {

            String query1 = "Select SUM("+sqLiteHelper.TotalSale+") from "+sqLiteHelper.TABLE_NAME3+";";
            Cursor cursor1 = sqliteDatabase.rawQuery(query1,null);
            if (cursor1!=null)
            {
                while(cursor1.moveToNext())
                {
                    double TotalSale = cursor1.getDouble(0);
                    BigDecimal bd = new BigDecimal(TotalSale).setScale(2, RoundingMode.UP);
                    Double FormattedTotalSale = bd.doubleValue();
                    TotalAmountCollected.setText(String.valueOf(FormattedTotalSale));
                }

            }
            cursor1.close();

            //Total GST Amount Collected

            String query2 =  "Select SUM("+sqLiteHelper.GSTCollected+") from "+sqLiteHelper.TABLE_NAME3+";";
            Cursor cursor2 = sqliteDatabase.rawQuery(query2,null);
            if (cursor2!=null)
            {
                while (cursor2.moveToNext())
                {
                    double totalGSTCollected = cursor2.getDouble(0);
                    BigDecimal bd = new BigDecimal(totalGSTCollected).setScale(2, RoundingMode.UP);
                    Double FormattedGSTCollected = bd.doubleValue();
                    TotalGSTCollected.setText(String.valueOf(FormattedGSTCollected));
                }

            }
            cursor2.close();

            // Total Transaction amount collected
            String query3 =  "Select SUM("+sqLiteHelper.TransactionAmountCollected+") from "+sqLiteHelper.TABLE_NAME3+";";
            Cursor cursor3 = sqliteDatabase.rawQuery(query3,null);
            if (cursor3!=null)
            {
                while (cursor3.moveToNext())
                {
                    double totalTransactionAmountCollected = cursor3.getDouble(0);
                    BigDecimal bd = new BigDecimal(totalTransactionAmountCollected).setScale(2, RoundingMode.UP);
                    Double FormattedTransactionAmountCollected = bd.doubleValue();
                    TotalTransactionAmountCollected.setText(String.valueOf(FormattedTransactionAmountCollected));
                }

            }
            cursor3.close();


            //Total Orders placed
            String query4 =  "Select SUM("+sqLiteHelper.TotalOrders+") from "+sqLiteHelper.TABLE_NAME3+";";
            Cursor cursor4 = sqliteDatabase.rawQuery(query4,null);
            if (cursor4!=null)
            {
                while (cursor4.moveToNext())
                {
                    String totalGSTCollected = String.valueOf(cursor4.getDouble(0));
                    TotalOrdersPlaced.setText(totalGSTCollected);
                }

            }
            cursor4.close();

            // Top 3 revenue generating items
            String SelectedMonthInWords = SelectedDateInWords(SelectedDate);
            StringBuilder sb = new StringBuilder("");
            String query5 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedMonthInWords + "*" + sqLiteHelper.KEY_Price + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedMonthInWords + "*" + sqLiteHelper.KEY_Price + " DESC LIMIT 3;";
            Cursor cursor5 = sqliteDatabase.rawQuery(query5, null);
            if (cursor5 != null) {
                while (cursor5.moveToNext()) {
                    sb.append(cursor5.getString(0) + " (" + cursor5.getDouble(1) + ")\n");
                }

            }
            TopRevenueItems.setText(sb.toString());
            cursor5.close();


            // Least 3 revenue generating items
            StringBuilder sb2 = new StringBuilder("");
            String query6 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedMonthInWords + "*" + sqLiteHelper.KEY_Price + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedMonthInWords + "*" + sqLiteHelper.KEY_Price + " ASC LIMIT 3;";
            Cursor cursor6 = sqliteDatabase.rawQuery(query6, null);
            if (cursor6 != null) {
                while (cursor6.moveToNext()) {
                    sb2.append(cursor6.getString(0) + " (" + cursor6.getDouble(1) + ")\n");
                }

            }
            LeastRevenueItems.setText(sb2.toString());
            cursor6.close();


            // Top 3 most popular items
            StringBuilder sb3 = new StringBuilder("");
            String query7 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedMonthInWords + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedMonthInWords + " DESC LIMIT 3;";
            Cursor cursor7 = sqliteDatabase.rawQuery(query7, null);
            if (cursor7 != null) {
                while (cursor7.moveToNext()) {
                    sb3.append(cursor7.getString(0) + " (" + cursor7.getInt(1) + ")\n");
                }

            }
            TopPopular.setText(sb3.toString());
            cursor7.close();


            // Top 3 least popular items
            StringBuilder sb4 = new StringBuilder("");
            String query8 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedMonthInWords + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedMonthInWords + " ASC LIMIT 3;";
            Cursor cursor8 = sqliteDatabase.rawQuery(query8, null);
            if (cursor8 != null) {
                while (cursor8.moveToNext()) {
                    sb4.append(cursor8.getString(0) + " (" + cursor8.getInt(1) + ")\n");
                }

            }

            LeastPopular.setText(sb4.toString());
            cursor8.close();



        }
        else
        {
            String SelectedDateInWords = SelectedDateInWords(SelectedDate);

            //Total Amount Collected
            String query1 = "Select " + sqLiteHelper.TotalSale + " from " + sqLiteHelper.TABLE_NAME3 + " where " + sqLiteHelper.Date + " = '" + SelectedDateInWords + "';";

            Cursor cursor = sqliteDatabase.rawQuery(query1, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String TotalSale = String.valueOf(cursor.getDouble(0));
                    TotalAmountCollected.setText(TotalSale);
                }

            }
            cursor.close();


            // Top 3 revenue generating items
            StringBuilder sb = new StringBuilder("");
            String query2 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedDateInWords + "*" + sqLiteHelper.KEY_Price + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedDateInWords + "*" + sqLiteHelper.KEY_Price + " DESC LIMIT 3;";
            Cursor cursor1 = sqliteDatabase.rawQuery(query2, null);
            if (cursor1 != null) {
                while (cursor1.moveToNext()) {
                    sb.append(cursor1.getString(0) + " (" + cursor1.getDouble(1) + ")\n");
                }

            }
            TopRevenueItems.setText(sb.toString());
            cursor1.close();


            // Least 3 revenue generating items
            StringBuilder sb2 = new StringBuilder("");
            String query3 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedDateInWords + "*" + sqLiteHelper.KEY_Price + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedDateInWords + "*" + sqLiteHelper.KEY_Price + " ASC LIMIT 3;";
            Cursor cursor2 = sqliteDatabase.rawQuery(query3, null);
            if (cursor2 != null) {
                while (cursor2.moveToNext()) {
                    sb2.append(cursor2.getString(0) + " (" + cursor2.getDouble(1) + ")\n");
                }

            }
            LeastRevenueItems.setText(sb2.toString());
            cursor2.close();


            // Top 3 most popular items
            StringBuilder sb3 = new StringBuilder("");
            String query4 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedDateInWords + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedDateInWords + " DESC LIMIT 3;";
            Cursor cursor3 = sqliteDatabase.rawQuery(query4, null);
            if (cursor3 != null) {
                while (cursor3.moveToNext()) {
                    sb3.append(cursor3.getString(0) + " (" + cursor3.getInt(1) + ")\n");
                }

            }
            TopPopular.setText(sb3.toString());
            cursor3.close();


            // Top 3 least popular items
            StringBuilder sb4 = new StringBuilder("");
            String query5 = "Select " + sqLiteHelper.KEY_Name + ", " + SelectedDateInWords + " from " + sqLiteHelper.TABLE_NAME + " ORDER BY "
                    + SelectedDateInWords + " ASC LIMIT 3;";
            Cursor cursor4 = sqliteDatabase.rawQuery(query5, null);
            if (cursor4 != null) {
                while (cursor4.moveToNext()) {
                    sb4.append(cursor4.getString(0) + " (" + cursor4.getInt(1) + ")\n");
                }

            }

            LeastPopular.setText(sb4.toString());
            cursor4.close();


            //Total GST Collected

            String query6 = "Select " + sqLiteHelper.GSTCollected + " from " + sqLiteHelper.TABLE_NAME3 + " where " + sqLiteHelper.Date + " = '" + SelectedDateInWords + "';";

            Cursor cursor5 = sqliteDatabase.rawQuery(query6, null);
            if (cursor5 != null) {
                while (cursor5.moveToNext()) {
                    String totalGSTCollected = String.valueOf(cursor5.getDouble(0));
                    TotalGSTCollected.setText(totalGSTCollected);
                }

            }
            cursor5.close();

            //Total Orders

            String query7 = " Select " + sqLiteHelper.TotalOrders + " from " + sqLiteHelper.TABLE_NAME3 + " where " + sqLiteHelper.Date + " =  '" + SelectedDateInWords + "';";
            Cursor cursor6 = sqliteDatabase.rawQuery(query7, null);
            if (cursor6 != null) {
                while (cursor6.moveToNext()) {
                    String TotalOrders = String.valueOf(cursor6.getInt(0));
                    TotalOrdersPlaced.setText(TotalOrders);
                }

            }
            cursor6.close();

            //Total Transaction amount collected
            String query8 = " Select " + sqLiteHelper.TransactionAmountCollected + " from " + sqLiteHelper.TABLE_NAME3 + " where " + sqLiteHelper.Date + " =  '" + SelectedDateInWords + "';";
            Cursor cursor7 = sqliteDatabase.rawQuery(query8, null);
            if (cursor7 != null) {
                while (cursor7.moveToNext()) {
                    String totalTransactionAmountCollected = String.valueOf(cursor7.getDouble(0));
                    TotalTransactionAmountCollected.setText(totalTransactionAmountCollected);
                }

            }
            cursor7.close();

        }

    }


    public String SelectedDateInWords(String SelectedDate)
    {

        if (SelectedDate.equals("Monthly Summary"))
        {
            BillDisplayAndGeneration billDisplayAndGeneration = new BillDisplayAndGeneration();
            String SelectedDateInWords = BillDisplayAndGeneration.toWords(billDisplayAndGeneration.getCurrentMonth().replaceAll("/",""));
            //populateItemSoldDetailsList(SelectedDateInWords);
            Toast.makeText(FullSalesDetails.this, ""+SelectedDateInWords, Toast.LENGTH_SHORT).show();
            return SelectedDateInWords;
        }
        else
        {
            int firstPosition = SelectedDate.indexOf("-");
            String dateStringForWordConversion = SelectedDate.substring(0, firstPosition);
            String SelectedDateInWords = BillDisplayAndGeneration.toWords(dateStringForWordConversion);
           // populateItemSoldDetailsList(SelectedDateInWords);
            Toast.makeText(FullSalesDetails.this, ""+SelectedDateInWords, Toast.LENGTH_SHORT).show();
            return SelectedDateInWords;
        }

    }
}
