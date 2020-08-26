package com.example.itemsdatabasestartup;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SQLiteHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    static String DATABASE_NAME="ItemDetailsDataBase";
    public static int database_version = getCurrentMonth();

    // for table = itemsTally
    public static final String TABLE_NAME="itemTally";
    public static final String KEY_ID="id";
    public static final String KEY_Name="itemName";
    public static final String KEY_Price="price";
    public static final String KEY_Counter="counter";

    // for table =categoryDetails
    public static final String TABLE_NAME2="CategoriesTable";
    public static final String Category_ID = "categoryID";
    public static final String Category_Name = "categoryName";

    public static final String currentMonth = String.valueOf(getCurrentMonth()).replaceAll("/", "");
    public static final String Month_tally_column = BillDisplayAndGeneration.toWords(currentMonth);

    public static final String TABLE_NAME3 = "daily_summary";
    public static final String Date= "Date";
    public static final String TotalSale = "TotalSale";
    public static final String TotalOrders= "TotalOrders";
    public static final String TransactionAmountCollected= "TransactionAMountCollected";
    public static final String GSTCollected= "GSTCollected";





    public static final String ForeignKeyConstraint = "categoryIDKey";



    public SQLiteHelper(Context context)
    {

        super(context, DATABASE_NAME, null,database_version);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Table1Creation = "Create Table "+TABLE_NAME2+" ("
                +Category_ID+" INTEGER PRIMARY KEY, "
                +Category_Name+" Varchar(20) NOT NULL UNIQUE)";


        String Table3Creation = "Create Table "+TABLE_NAME3+" ("
                +Date+" String Primary Key not Null, "
                +TotalSale+" REAL not null, "
                +TotalOrders+" integer not null, "
                +TransactionAmountCollected+" REAL not null, "
                +GSTCollected+" REAL not null );";


        // consider inserting a category "MRP"  with insert statement from the first time the table is built

        String Table2Creation = "Create Table "+TABLE_NAME+" ("
                +KEY_ID+" INTEGER PRIMARY KEY NOT NULL, "
                +KEY_Name+" VARCHAR(20) NOT NULL UNIQUE, "
                +KEY_Price+" REAL NOT NULL, "
                +KEY_Counter+" INTEGER NOT NULL, "
                +Category_Name+" VARCHAR(20) DEFAULT 'UNASSIGNED', "
                +"One INTEGER, "
                +"Two INTEGER, "
                +"Three INTEGER, "
                +"Four INTEGER, "
                +"Five INTEGER, "
                +"Six INTEGER, "
                +"Seven INTEGER, "
                +"Eight INTEGER, "
                +"Nine INTEGER, "
                +"Ten INTEGER, "
                +"Eleven INTEGER, "
                +"Twelve INTEGER, "
                +"Thirteen INTEGER, "
                +"Fourteen INTEGER, "
                +"Fifteen INTEGER, "
                +"Sixteen INTEGER, "
                +"Seventeen INTEGER, "
                +"Eighteen INTEGER, "
                +"Nineteen INTEGER, "
                +"Twenty INTEGER, "
                +"TwentyOne INTEGER, "
                +"TwentyTwo INTEGER, "
                +"TwentyThree INTEGER, "
                +"TwentyFour INTEGER, "
                +"TwentyFive INTEGER, "
                +"TwentySix INTEGER, "
                +"TwentySeven INTEGER, "
                +"TwentyEight INTEGER, "
                +"TwentyNine INTEGER, "
                +"Thirty  INTEGER, "
                +"ThirtyOne INTEGER, "
                +Month_tally_column +" INTEGER, "+                  //monthlyTally field
                "FOREIGN KEY ("+Category_Name+") " +
                "REFERENCES "+TABLE_NAME2+"("+Category_Name+") " +
                "On Update CASCADE " +
                "On Delete set DEFAULT)";

        sqLiteDatabase.execSQL(Table2Creation);
        sqLiteDatabase.execSQL(Table1Creation);
        sqLiteDatabase.execSQL(Table3Creation);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //https://stackoverflow.com/questions/2545558/foreign-key-constraints-in-android-using-sqlite-on-delete-cascade
       // db.execSQL("PRAGMA foreign_keys=ON;");

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {

        //https://stackoverflow.com/questions/7224183/how-we-can-delete-foreign-key-in-sqlite
        //db.setForeignKeyConstraintsEnabled(true);
        //super.onConfigure(db);
    }

    /*@Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        *//*sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);*//*
    }
*/

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion )
    {
           newVersion = database_version;

           if (oldVersion != newVersion)
           {
               //Do not directly create a new table , first prompt the client to a window/activity where he is asked to take an
               //action regarding what he wants to do with the data of previous month.
               // if a response is received the go ahead with creating this new table

               String newTableName = "updatedTable";

               String DisableForeignKey = "PRAGMA foreign_keys=off;";
               String beginTransaction = "begin Transaction;";


               String CreateNewTable = "Create Table "+newTableName+" ("
                       +KEY_ID+" INTEGER PRIMARY KEY NOT NULL, "
                       +KEY_Name+" VARCHAR(20) NOT NULL UNIQUE, "
                       +KEY_Price+" REAL NOT NULL, "
                       +KEY_Counter+" INTEGER NOT NULL, "
                       +Category_Name+" VARCHAR(20) DEFAULT 'UNASSIGNED', "
                       +"One INTEGER, "
                       +"Two INTEGER, "
                       +"Three INTEGER, "
                       +"Four INTEGER, "
                       +"Five INTEGER, "
                       +"Six INTEGER, "
                       +"Seven INTEGER, "
                       +"Eight INTEGER, "
                       +"Nine INTEGER, "
                       +"Ten INTEGER, "
                       +"Eleven INTEGER, "
                       +"Twelve INTEGER, "
                       +"Thirteen INTEGER, "
                       +"Fourteen INTEGER, "
                       +"Fifteen INTEGER, "
                       +"Sixteen INTEGER, "
                       +"Seventeen INTEGER, "
                       +"Eighteen INTEGER, "
                       +"Nineteen INTEGER, "
                       +"Twenty INTEGER, "
                       +"TwentyOne INTEGER, "
                       +"TwentyTwo INTEGER, "
                       +"TwentyThree INTEGER, "
                       +"TwentyFour INTEGER, "
                       +"TwentyFive INTEGER, "
                       +"TwentySix INTEGER, "
                       +"TwentySeven INTEGER, "
                       +"TwentyEight INTEGER, "
                       +"TwentyNine INTEGER, "
                       +"Thirty  INTEGER, "
                       +"ThirtyOne INTEGER, "
                       +Month_tally_column +" INTEGER, "+                  //monthlyTally field
                       "FOREIGN KEY ("+Category_Name+") " +
                       "REFERENCES "+TABLE_NAME2+"("+Category_Name+") " +
                       "On Update CASCADE " +
                       "On Delete set DEFAULT)";

               String insertIntoNewTable = "INSERT INTO "+newTableName+" ("+KEY_ID+", "+KEY_Name+", "+KEY_Price+", "+KEY_Counter+", "+Category_Name+
                       ") SELECT "+KEY_ID+", "+KEY_Name+", "+KEY_Price+", "+KEY_Counter+", "+Category_Name+" FROM itemTally;";



               String DropCurrentTable = " Drop TABLE itemTally;";

               String RenameNewTableToOldName = "ALTER TABLE "+newTableName+" Rename to "+TABLE_NAME;

               String CommitTransaction= "COMMIT ;";

               String EnableForeignKey= "PRAGMA foreign_keys=on;";

              // String AddCurrentMonthColumn ="ALTER TABLE " + TABLE_NAME + " ADD COLUMN '" + database_version + "' INTEGER;";


               //daily_summary table creation
               String Table3Creation = "Create Table "+TABLE_NAME3+" ("
                       +Date+" String Primary Key not Null, "
                       +TotalSale+" REAL not null, "
                       +TotalOrders+" integer not null, "
                       +TransactionAmountCollected+" REAL not null, "
                       +GSTCollected+"REAL not null );";


               sqLiteDatabase.execSQL(DisableForeignKey);
               sqLiteDatabase.execSQL(beginTransaction);
               sqLiteDatabase.execSQL(CreateNewTable);
               sqLiteDatabase.execSQL(insertIntoNewTable);
               sqLiteDatabase.execSQL(DropCurrentTable);
               sqLiteDatabase.execSQL(RenameNewTableToOldName);
               sqLiteDatabase.execSQL(CommitTransaction);
               sqLiteDatabase.execSQL(EnableForeignKey);
               sqLiteDatabase.execSQL(Table3Creation);


           }

    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(sqLiteDatabase, oldVersion, newVersion );
    }

    public boolean AddCategory(String CategoryName)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryName",CategoryName);
        long result= sqLiteDatabase.insert("CategoriesTable",null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
            return true;
        //String rawquery = " Insert into "+TABLE_NAME2+" ("+Category_Name+") values("+CategoryName+");";

    }

    public int DeleteCategory(String CategoryName)
    {
        sqLiteDatabase = this.getWritableDatabase();

        //String query = "Update "+TABLE_NAME+" set "+Category_Name+" = 'UNASSIGNED' where "+Category_Name+" = '"+CategoryName+"';";
        //sqLiteDatabase.execSQL(query);
        // temporary solution, have to make foreign key constraints work
        // first delete the category and if successful update every item detail category name to another name so that spinner doesnt malfunction.
        int result = sqLiteDatabase.delete("CategoriesTable","categoryName=?",new String[]{CategoryName});
     // if there are some rows affected then update data
        int updatedRows;
      if (result >0)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Category_Name, "UNASSIGNED");
            updatedRows = sqLiteDatabase.update(TABLE_NAME, contentValues, Category_Name+" =?",new String[]{CategoryName});
            if (updatedRows==0)
            {
                return -1;
            }
            else {
                return updatedRows;
            }
        }
      else
      {
          return result;
      }

    }


    public Cursor DisplayCategoryNames()
    {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "Select "+Category_Name+" from "+TABLE_NAME2;
        ;
        Cursor cursor = null;

        if (sqLiteDatabase!=null)
        {
            cursor = sqLiteDatabase.rawQuery(query,null);
        }
        return cursor;

    }

    public boolean AddItemDetails(int id, String itemName, float price, int Counter , String CategoryName)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,id);
        contentValues.put(KEY_Name, itemName);
        contentValues.put(KEY_Price,price);
        contentValues.put(KEY_Counter, Counter);
        contentValues.put(Category_Name , CategoryName);
        long result= sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
            return true;
    }

    public Integer DeleteItemDetails(String id)
    {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{id});
    }


    public Cursor DisplayItemDetails()
    {
        sqLiteDatabase = this.getReadableDatabase();

        String query = "Select "+KEY_ID+", "+KEY_Name+", "+KEY_Price+", "+KEY_Counter+", "+Category_Name+" from "+TABLE_NAME;

        Cursor cursor = null;

        if (sqLiteDatabase!=null)
        {
            cursor = sqLiteDatabase.rawQuery(query,null);
        }
        return cursor;


    }


    public Cursor DisplayItemSoldDetails(String SelectedDate)
    {
        sqLiteDatabase = this.getReadableDatabase();

        String query = "Select "+KEY_Name+", "+KEY_Price+", "+SelectedDate+", "+KEY_Counter+" from "+TABLE_NAME+";";

        Cursor cursor = null;

        if (sqLiteDatabase!=null)
        {
            cursor = sqLiteDatabase.rawQuery(query,null);
        }
        return cursor;


    }



    public boolean UpdateItemDetails(String id, String ItemName , float price , int Counter , String Category)
    {

        //Update function will return number of rows affected if success, 0 otherwise.
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,id);
        contentValues.put(KEY_Name,ItemName);
        contentValues.put(KEY_Price,price);
        contentValues.put(KEY_Counter,Counter);
        contentValues.put(Category_Name,Category);
        int result =sqLiteDatabase.update(TABLE_NAME, contentValues,"id= ?", new String[]{id});
        if(result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public boolean UpdateItemSoldTally(String itemName , String DateColumn, String MonthColumn , int quantityDay, int quantityMonth)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateColumn, quantityDay);
        contentValues.put(MonthColumn,quantityMonth);
        int result = sqLiteDatabase.update(TABLE_NAME, contentValues,"itemName= ?", new String[]{itemName});
        if(result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }


    public Cursor displayItemByCategory(String Category)
    {
        sqLiteDatabase = this.getReadableDatabase();

        String query = "Select "+KEY_ID+", "+KEY_Name+", "+KEY_Price+" from "+TABLE_NAME+" where "+Category_Name+" = '"+Category+"' ;";

        Cursor cursor = null;

        if (sqLiteDatabase!=null)
        {
            cursor = sqLiteDatabase.rawQuery(query,null);
        }
        return cursor;



    }

    public static int getCurrentMonth()
    {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM/yy");
        String CurrentMonth = date.format(calendar.getTime());
        int formattedCurrentMonth = Integer.parseInt(CurrentMonth.replaceAll("/", ""));

        return formattedCurrentMonth;
    }



    public boolean insertIntoDailySummary(String date, int totalOrders, Double totalSale, Double TransactionAmount, Double GSTcollected)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Date,date);
        contentValues.put(TotalSale,totalSale);
        contentValues.put(TotalOrders,totalOrders);
        contentValues.put(TransactionAmountCollected,TransactionAmount);
        contentValues.put(GSTCollected,GSTcollected);
        long result = sqLiteDatabase.insert(TABLE_NAME3,null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
            return true;

    }


    public boolean updateDailySummary(String date,Double totalSale, int totalOrders, double transactionCostCollected, double GSTcollected)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TotalSale,totalSale);
        contentValues.put(TotalOrders, totalOrders);
        contentValues.put(TransactionAmountCollected,transactionCostCollected);
        contentValues.put(GSTCollected,GSTcollected);
        int result = sqLiteDatabase.update(TABLE_NAME3, contentValues,"Date= ?", new String[]{date});
        if(result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }


    }



}
