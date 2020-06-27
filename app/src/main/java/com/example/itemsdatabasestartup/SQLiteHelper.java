package com.example.itemsdatabasestartup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    static String DATABASE_NAME="ItemDetailsDataBase";

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

    public static final String ForeignKeyConstraint = "categoryIDKey";


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Table1Creation = "Create Table "+TABLE_NAME2+" ("
                +Category_ID+" INTEGER PRIMARY KEY, "
                +Category_Name+" Varchar(20) NOT NULL, " + "UNIQUE("+Category_ID+","+Category_Name+"))";

// what is unique in here
        // heere you are saying combination of catagoryid and categoryname is unique
        //okay so basically ishould only assign uniquee constraint to category name if i want it to be unique ??
        //okay chal ab recycler view ka dekh le

        String Table2Creation = "Create Table "+TABLE_NAME+" ("
                +KEY_ID+" INTEGER PRIMARY KEY, "
                +KEY_Name+" VARCHAR(20) NOT NULL, "
                +KEY_Price+" REAL, "
                +KEY_Counter+" INTEGER, "
                +Category_Name+" VARCHAR(20) NOT NULL," +
                " CONSTRAINT "+ForeignKeyConstraint+
                " FOREIGN KEY ("+Category_Name+") " +
                "REFERENCES "+TABLE_NAME2+"("+Category_Name+") " +
                "On Update CASCADE " +
                "On Delete set NULL)";

        sqLiteDatabase.execSQL(Table2Creation);
        sqLiteDatabase.execSQL(Table1Creation);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
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

    public Integer DeleteCategory(String CategoryName)
    {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("CategoriesTable","categoryName=?",new String[]{CategoryName});
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
        ;
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



}
