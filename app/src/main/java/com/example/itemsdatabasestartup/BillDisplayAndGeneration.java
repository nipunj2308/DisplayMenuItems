package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.itemsdatabasestartup.BillSettings.GST_switch;
import static com.example.itemsdatabasestartup.BillSettings.GST_value;
import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;
import static com.example.itemsdatabasestartup.OrderSummaryRecyclerView.itemDetails;
import static com.example.itemsdatabasestartup.OrderSummaryRecyclerView.itemTotals;
import static com.example.itemsdatabasestartup.SQLiteHelper.GSTCollected;
import static com.example.itemsdatabasestartup.SQLiteHelper.KEY_Price;

public class BillDisplayAndGeneration extends AppCompatActivity {


    private TextView OrderNo, DateAndTime;
    private TextView TotalAndGST , TotalAmount;
    private TextView CustomerContact;
    private EditText CustomerName;
    private RecyclerView orderDetails;
    private Button GenerateBill;
    private String ContactNumber;
    private ArrayList<ModelClassForOrdersList> orderDetail;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    String CurrentDate;
    String CurrentTime;
    String CurrentMonth;
    String CurrentSavedMonth;
    String CurrentSavedDate;
    int CurrentSavedOrderNo;

    private double TransactionCost= 0.65;
    private int OrderNumber;
    private int orderItemCountForInflation;
    private boolean GSTOnOff;
    private double GstValue;

    SharedPreferences sharedPreferences;
    private DatabaseReference rootReference;
    private String currentUserId;
    private FirebaseAuth firebaseAuth;



    // date, time, description, item name, qty , counter number
    // Clients/BusinessName/ AdminId/ CounterIDs/Orders/date
    // let client enter clientID and password and make him login by checking if the login credentials belong to any email.
    //if the attribute counter no is same as the current loggedin counter ID the display data.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_display_and_generation);
        sqLiteHelper = new SQLiteHelper(this);

        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = BillDisplayAndGeneration.this.getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("currentAdminID","");
        rootReference = FirebaseDatabase.getInstance().getReference("AdminID").child(currentUserId);


        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        OrderNo = findViewById(R.id.OrderNoTextView);
        DateAndTime = findViewById(R.id.DateandTimeTextView);
        TotalAndGST = findViewById(R.id.OrderTotalandGSTTextView);
        TotalAmount = findViewById(R.id.OrderTotal);
        CustomerContact = findViewById(R.id.CustomerContactTextView);
        CustomerName = findViewById(R.id.CustomerNameEditText);
        orderDetails = findViewById(R.id.OrderSummaryRecyclerView);
        GenerateBill = findViewById(R.id.GenerateBillButton);

        Bundle bundle = getIntent().getExtras();
        ContactNumber = bundle.getString("customerContact");
        orderItemCountForInflation = bundle.getInt("orderListCount");
        CustomerContact.setText("+91 "+ContactNumber);




        orderDetail = OrderItemListRecyclerView.orderList ;
        double inflateAmount = TransactionCost/orderItemCountForInflation;
        OrderSummaryRecyclerView  orderSummaryRecyclerView= new OrderSummaryRecyclerView(inflateAmount,orderDetail);
        orderDetails.setAdapter(orderSummaryRecyclerView);
        orderDetails.setLayoutManager(new LinearLayoutManager(this));
        orderDetails.setHasFixedSize(false);


        callSavedValues();

        setActualTotal(GstValue, TransactionCost);


        String TimeDate = "Ordered at "+getCurrentTime()+ " on "+ getCurrentDateAndMonth();
        DateAndTime.setText(TimeDate);

        sharedPreferences = this.getSharedPreferences(Shared_Prefs,Context.MODE_PRIVATE);
        CurrentSavedDate = sharedPreferences.getString("Current Saved Date", "");
        CurrentSavedOrderNo = sharedPreferences.getInt("Current saved Order No",5);
        CurrentSavedMonth = sharedPreferences.getString("Current Saved Month","");

        CurrentDate = getCurrentDate();

        OrderNumber = SetGetOrderNo(CurrentDate, CurrentSavedDate);
        OrderNo.setText("OrderNo : "+OrderNumber);

        Toast.makeText(this,""+currentUserId, Toast.LENGTH_SHORT).show();

        CurrentMonth = getCurrentMonth();




        GenerateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                sendOrderDetailsToFirebase(getCurrentDateAndMonth(),getCurrentTime(),OrderNumber, ContactNumber);
                updateDatabaseDetails(getCurrentDate(),getCurrentMonth(), TransactionCost,OrderNumber, GstValue);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("Current Saved Date",CurrentDate);
                editor.putString("Current Saved Month", CurrentMonth);
                editor.putInt("Current saved Order No",OrderNumber);
                editor.commit();


                TakeOrderDetailsFragment takeOrderDetailsFragment = new TakeOrderDetailsFragment();
                FragmentManager manager = BillDisplayAndGeneration.this.getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(takeOrderDetailsFragment);
                trans.commit();
                manager.popBackStack();

               /* Intent intent = new Intent(BillDisplayAndGeneration.this,BaseActivity.class);
                startActivity(intent);*/

                finish();
            }
        });
    }




    public String getCurrentTime()
    {

        //SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
        //String strDate = "Order Time : " + mdformat.format(calendar.getTime());

        //format to get time in 12 hour format where a represents am/pm
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("h:mm a");

        CurrentTime = time.format(calendar.getTime());


        return CurrentTime;
    }


    public String getCurrentDateAndMonth()
    {
           Calendar calendar = Calendar.getInstance();
           SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
           CurrentDate = date.format(calendar.getTime());
           return CurrentDate;
    }

    public String getCurrentMonth()
    {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM/yy");
        CurrentMonth = date.format(calendar.getTime());
        return CurrentMonth;
    }

    public String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd");
        CurrentDate = date.format(calendar.getTime());
        return CurrentDate;
    }


    public void callSavedValues()
    {

        SharedPreferences sharedPreferences = this.getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);
        GSTOnOff = sharedPreferences.getBoolean(GST_switch, false);
        if (GSTOnOff)
        {

            String GstValueString = sharedPreferences.getString(GST_value, "");
            if (GstValueString.equals("Select GST %"))
            {
                GstValue = 0.0;
                TotalAndGST.setText("Total :");

            }
            else
                {
                    GstValue = Double.parseDouble(GstValueString.substring(0, GstValueString.indexOf("%")));
                TotalAndGST.setText("Total (+" + GstValue + "):");
            }
        }

        else
        {
            GstValue = 0.0;
            TotalAndGST.setText("Total :");
        }

    }


    public int SetGetOrderNo(String CurrentDate ,String CurrentSavedDate)
    {
        if (CurrentDate.equals(CurrentSavedDate))
        {
            int OrderNumber = ++CurrentSavedOrderNo;

            return OrderNumber;
        }
        else
        {
            int OrderNumber = 1;
            return OrderNumber;
        }

    }


    public void setDatabase(String CurrentSavedDate , String CurrentDate , String CurrentSavedMonth , String CurrentMonth)
    {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        String formattedCurrentDate = CurrentDate.replaceAll("/","");
        String formattedCurrentMonth = CurrentMonth.replaceAll("/","");
        if(CurrentSavedMonth.equals(CurrentMonth))
        {
            if (!CurrentDate.equals(CurrentSavedDate)) {

                String AddColumnQuery = "ALTER TABLE " + sqLiteHelper.TABLE_NAME + " ADD COLUMN '" + formattedCurrentDate + "' INTEGER;";
                sqLiteDatabase.execSQL(AddColumnQuery, null);
                Toast.makeText(this,"date Column added", Toast.LENGTH_SHORT).show();
            }
        }
        // if the current month is not saved then create a new column
        else
        {
            // delete current table
            // Add Column for current month
            //Add column for current date.
            String newTableName = "updatedTable";

            String DisableForeignKey = "PRAGMA foreign_keys=off;";
            String beginTransaction = "begin Transaction;";

            String CreateNewTable = "Create Table if not exists "+newTableName+" ("
                    +sqLiteHelper.KEY_ID+" INTEGER PRIMARY KEY NOT NULL, "
                    +sqLiteHelper.KEY_Name+" VARCHAR(20) NOT NULL UNIQUE, "
                    + KEY_Price+" REAL NOT NULL, "
                    +sqLiteHelper.KEY_Counter+" INTEGER NOT NULL, "
                    +sqLiteHelper.Category_Name+" VARCHAR(20) DEFAULT 'UNASSIGNED',"+
                    " FOREIGN KEY ("+sqLiteHelper.Category_Name+") " +
                    "REFERENCES "+sqLiteHelper.TABLE_NAME2+"("+sqLiteHelper.Category_Name+") " +
                    "On Update CASCADE " +
                    "On Delete set DEFAULT)";

            String insertIntoNewTable = "INSERT INTO "+newTableName+" ("+sqLiteHelper.KEY_ID+", "+sqLiteHelper.KEY_Name+", "+sqLiteHelper.KEY_Price+", "+sqLiteHelper.KEY_Counter+", "+sqLiteHelper.Category_Name+
                    ") SELECT "+sqLiteHelper.KEY_ID+", "+sqLiteHelper.KEY_Name+", "+sqLiteHelper.KEY_Price+", "+sqLiteHelper.KEY_Counter+", "+sqLiteHelper.Category_Name+" FROM itemTally;";


            String DropCurrentTable = " Drop TABLE itemTally;";

            String RenameNewTableToOldName = "ALTER TABLE "+newTableName+" Rename to "+sqLiteHelper.TABLE_NAME;

            String CommitTransaction= "COMMIT ;";

            String EnableForeignKey= "PRAGMA foreign_keys=on;";

            String AddCurrentMonthColumn ="ALTER TABLE " + sqLiteHelper.TABLE_NAME + " ADD COLUMN '" + formattedCurrentMonth + "' INTEGER;";

            String AddCurrentDateColumn ="ALTER TABLE " + sqLiteHelper.TABLE_NAME + " ADD COLUMN '" + formattedCurrentDate + "' INTEGER;";

            sqLiteDatabase.execSQL(DisableForeignKey);
            sqLiteDatabase.execSQL(beginTransaction);
            sqLiteDatabase.execSQL(CreateNewTable);
            sqLiteDatabase.execSQL(insertIntoNewTable);
            sqLiteDatabase.execSQL(DropCurrentTable);
            sqLiteDatabase.execSQL(RenameNewTableToOldName);
            sqLiteDatabase.execSQL(CommitTransaction);
            sqLiteDatabase.execSQL(EnableForeignKey);
            sqLiteDatabase.execSQL(AddCurrentMonthColumn);
            sqLiteDatabase.execSQL(AddCurrentDateColumn);
            Toast.makeText(this,"New Month added", Toast.LENGTH_SHORT).show();

        }

    }

    public Double setActualTotal(Double GSTValue, Double TransactionCost)
    {
        Double NetTotal= 0.0;
        Double ActualTotal= 0.0;
        try {

            for (int i = 0; i <OrderSummaryRecyclerView.itemDetails.size(); i++)
            {
                try
                {
                    double Total = itemDetails.get(i).getTotal();

                   ActualTotal = ActualTotal+Total;

                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }


                NetTotal = ActualTotal+TransactionCost+ (ActualTotal*GSTValue)/100;

                TotalAmount.setText(Double.toString(NetTotal));

            }

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return ActualTotal;
    }



    public void sendInvoiceSMSToCustomer()
    {

    }

    public void sendOrderDetailsToFirebase(final String CurrentDate, String CurrentTime, int OrderNo, String ContactNo)
    {
       // ArrayList<ModelClassForOrderDetailsFireBase> orderListForFireBase = new ArrayList<>();
        //ModelClassForOrderDetailsFireBase modelClassForOrderDetailsFireBase;
        StringBuilder itemNameString = new StringBuilder("");
        StringBuilder itemQuantityString = new StringBuilder("");
        StringBuilder itemCounterString = new StringBuilder("");
         StringBuilder itemDescriptionString = new StringBuilder("");
         StringBuilder itemStatusString = new StringBuilder("");
         StringBuilder itemTotalString = new StringBuilder("");
         StringBuilder orderPreparationTime = new StringBuilder("");
        ArrayList<String> ab = new ArrayList<>();

        // Toast.makeText(BillDisplayAndGeneration.this,""+OrderTotal,Toast.LENGTH_SHORT).show();

        for (int i = 0; i < orderDetail.size(); i++) {


            if (orderDetail.get(i).getName() != null && orderDetail.get(i).getQuantity() != 0 && orderDetail.get(i).getTotal() != 0)
            {
                  int id = orderDetail.get(i).getId();
                  String itemName= orderDetail.get(i).getName();
                  int itemQuantity=orderDetail.get(i).getQuantity();
                  String Description= orderDetail.get(i).getDescription();
                  String Status = "Preparing";
                  float total= orderDetail.get(i).getTotal();
                  int CounterNo = 0;

                  String query = "Select "+sqLiteHelper.KEY_Counter+" from "+sqLiteHelper.TABLE_NAME+" where "+sqLiteHelper.KEY_ID+" = "+id+";";
                  Cursor cursor = sqLiteDatabase.rawQuery(query,null);
                  if (cursor!=null)
                  {
                      while (cursor.moveToNext())
                      {
                          CounterNo = cursor.getInt(0);
                          itemNameString.append(itemName+",");
                          itemQuantityString.append(itemQuantity+",");
                          itemCounterString.append(CounterNo+",");
                          itemDescriptionString.append(Description+",");
                          itemStatusString.append(Status+",");
                          itemTotalString.append(String.valueOf(total)+",");
                          orderPreparationTime.append("0.0,");
                          /*modelClassForOrderDetailsFireBase =
                                  new ModelClassForOrderDetailsFireBase(itemName,itemQuantity,CounterNo,Description,Status,total);
                          orderListForFireBase.add(modelClassForOrderDetailsFireBase );*/
                      }
                  }


            }

        }

        String customerName = CustomerName.getText().toString();
        double OrderTotal = setActualTotal(GstValue,TransactionCost);
        double GSTCollected = OrderTotal*GstValue/100 ;
        double ActualTotal = OrderTotal+GSTCollected+TransactionCost;



        String messageKey = rootReference.child(CurrentDate).push().getKey();
        HashMap<String, Object> orderDetailsMap = new HashMap<>();

        orderDetailsMap.put("Counters",itemCounterString.toString());
        orderDetailsMap.put("Customer Contact",ContactNo);
        orderDetailsMap.put("Customer Name",customerName);
        orderDetailsMap.put("Descriptions",itemDescriptionString.toString());
        orderDetailsMap.put("Item Name",itemNameString.toString());
        orderDetailsMap.put("Order Number",String.valueOf(OrderNo));
        orderDetailsMap.put("Order Preparation Time",orderPreparationTime.toString());
        orderDetailsMap.put("Order Total",String.valueOf(ActualTotal));
        orderDetailsMap.put("Quantities",itemQuantityString.toString());
        orderDetailsMap.put("Statuses",itemStatusString.toString());
        orderDetailsMap.put("Time",CurrentTime);



        rootReference.child("Orders").child(CurrentDate).child(messageKey).updateChildren(orderDetailsMap);



       /* HashMap<String, Object> ActiveOrdersMap = new HashMap<>();
        ActiveOrdersMap.put("Counters",itemCounterString.toString());
        ActiveOrdersMap.put("Customer Contact",ContactNo);
        ActiveOrdersMap.put("Customer Name",customerName);
        ActiveOrdersMap.put("Descriptions",itemDescriptionString.toString());
        ActiveOrdersMap.put("Item Names",itemNameString.toString());
        ActiveOrdersMap.put("Order Number",String.valueOf(OrderNo));
        //ActiveOrdersMap.put("Order Preparation Time",orderPreparationTime.toString());
        ActiveOrdersMap.put("Quantities",itemQuantityString.toString());
        //ActiveOrdersMap.put("Statuses",itemStatusString.toString());
        ActiveOrdersMap.put("Time",CurrentTime);
        for (int i =0; i<orderDetail.size();i++)
        {
            ActiveOrdersMap.put("Item "+i+" status","preparing");
            ActiveOrdersMap.put("Item "+i+" Preparation Time","0.0");

        }



        rootReference.child("Active Orders").child(messageKey).updateChildren(ActiveOrdersMap);*/
       String[] Counters = itemCounterString.toString().split(",");
       String[] itemNames = itemNameString.toString().split(",");
       HashMap<String,Object> ActiveOrdersMap = new HashMap<>();
       for (int i=0;i<orderDetail.size();i++)
       {
           ActiveOrdersMap.put("counter",String.valueOf(Counters[i]));
           ActiveOrdersMap.put("customerContact",ContactNo);
           ActiveOrdersMap.put("customerName",customerName);
           ActiveOrdersMap.put("description",String.valueOf(orderDetail.get(i).getDescription()));
           ActiveOrdersMap.put("itemName",String.valueOf(orderDetail.get(i).getName()));
           ActiveOrdersMap.put("messageKey",messageKey);
           ActiveOrdersMap.put("orderNo",String.valueOf(OrderNo));
           ActiveOrdersMap.put("Position",String.valueOf(i));
           ActiveOrdersMap.put("preparationTime","0.0");
           ActiveOrdersMap.put("quantity",String.valueOf(orderDetail.get(i).getQuantity()));
           ActiveOrdersMap.put("status","preparing");
           ActiveOrdersMap.put("time",CurrentTime);
           rootReference.child("Active Orders").child(messageKey+" "+i).updateChildren(ActiveOrdersMap);
       }

    }

    public void updateDatabaseDetails(String CurrentDate , String CurrentMonth, Double TransactionCost, int OrderNo , Double GSTValue)
    {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
       // String formattedCurrentDate = convertNumberToWords(CurrentDate.replaceAll("/", "").toCharArray());
        //String formattedCurrentMonth = convertNumberToWords(CurrentMonth.replaceAll("/", "").toCharArray());
        String formattedCurrentMonth = toWords(CurrentMonth.replaceAll("/",""));
        String formattedCurrentDate  = toWords(CurrentDate.replaceAll("/", ""));
       // Toast.makeText(this, "data entered in "+formattedCurrentMonth+", "+formattedCurrentDate, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < orderDetail.size(); i++)
        {
            if (orderDetail.get(i).getName() != null && orderDetail.get(i).getQuantity() != 0 && orderDetail.get(i).getTotal() != 0)
            {
                String query1 = "Select " + formattedCurrentMonth + ", "+formattedCurrentDate+" from itemTally where " + sqLiteHelper.KEY_Name + " = '" + itemDetails.get(i).getName() + "' ;";
                //String query2 = "Select " + formattedCurrentDate + " from itemTally where " + sqLiteHelper.KEY_Name + " = '" + orderDetail.get(i).getName() + "' ;";

               Cursor cursor = sqLiteDatabase.rawQuery(query1, null);
               // Cursor cursor2 = sqLiteDatabase.rawQuery(query2, null);

                if (cursor != null)  {
                   while (cursor.moveToNext()) {
                        int qtySoldInAMonth = cursor.getInt(0);
                        int qtySoldInADay = cursor.getInt(1);
                        int updateQtySoldInMonth = orderDetail.get(i).getQuantity() + qtySoldInAMonth;
                        int updateQtySoldInDay = orderDetail.get(i).getQuantity() + qtySoldInADay;
                       boolean result = sqLiteHelper.UpdateItemSoldTally(orderDetail.get(i).getName(),formattedCurrentDate,formattedCurrentMonth,updateQtySoldInDay,updateQtySoldInMonth);
/*
                       if (result)
                       {
                           Toast.makeText(BillDisplayAndGeneration.this,"successful",Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           Toast.makeText(BillDisplayAndGeneration.this,"unsuccessful",Toast.LENGTH_SHORT).show();
                       }
*/

                    }
               }



            }

        }

        double ActualTotal = setActualTotal(GSTValue,TransactionCost);
        double GSTCollected = ActualTotal*GSTValue/100 ;
        double OrderTotal = ActualTotal+GSTCollected+TransactionCost;
        // if first order of the day, insert details

        if (OrderNo == 1)
        {

            boolean success = sqLiteHelper.insertIntoDailySummary(formattedCurrentDate,1,OrderTotal,TransactionCost,GSTCollected);
            if(success)
            {
                Toast.makeText(BillDisplayAndGeneration.this,"Successful",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(BillDisplayAndGeneration.this,"UnSuccessful",Toast.LENGTH_SHORT).show();

            }
        }

        //if not the first order then update details
        else if (OrderNo>1)
        {
            String query = "Select "+sqLiteHelper.TotalSale+", "+sqLiteHelper.TotalOrders+", "+sqLiteHelper.TransactionAmountCollected+
                    ", "+sqLiteHelper.GSTCollected+" from "+sqLiteHelper.TABLE_NAME3+
                     " where "+sqLiteHelper.Date+" = '"+formattedCurrentDate+"';";

            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if (cursor!=null)
            {
                while(cursor.moveToNext()) {
                    //https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
                    Double UpdatedTotalSale = cursor.getDouble(0) + ActualTotal+GSTCollected+TransactionCost ;
                    BigDecimal bd = new BigDecimal(UpdatedTotalSale).setScale(2, RoundingMode.UP);
                    Double UpdatedTotalSale2 = bd.doubleValue();

                    int UpdatedTotalOrders = cursor.getInt(1) + 1;

                    Double UpdatedTotalTransactionCostCollected = cursor.getDouble(2) +TransactionCost ;
                    BigDecimal bd3 = new BigDecimal(UpdatedTotalTransactionCostCollected).setScale(2, RoundingMode.UP);
                    Double UpdatedTotalTransactionCostCollected2 = bd3.doubleValue();

                    Double UpdatedTotalGSTCollected = cursor.getDouble(3) +GSTCollected ;
                    BigDecimal bd2 = new BigDecimal(UpdatedTotalGSTCollected).setScale(2, RoundingMode.UP);
                    Double UpdatedTotalGSTCollected2 = bd2.doubleValue();

                    sqLiteHelper.updateDailySummary(formattedCurrentDate, UpdatedTotalSale2, UpdatedTotalOrders, UpdatedTotalTransactionCostCollected2
                    , UpdatedTotalGSTCollected2);
                }
            }

        }


    }


    public String convertNumberToWords(char[] number)
    {

        // Get number of digits
        // in given number
        int length = number.length;
        StringBuilder str = new StringBuilder();
        String[] single_digits = new String[]{ "Zero", "One",
                "Two", "Three", "Four",
                "Five", "Six", "Seven",
                "Eight", "Nine"};

        // the first string is left empty at zeroth position , it is to make
        //        array indexing simple */
        //
        String[] two_digits = new String[]{"", "Ten", "Eleven", "Twelve",
                "Thirteen", "Fourteen",
                "Fifteen", "Sixteen", "Seventeen",
                "Eighteen", "Nineteen"};

        /* The first two string are not used, they are to make array indexing simple*/
        String[] tens_multiple = new String[]{"", "", "Twenty", "Thirty", "Forty",
                "Fifty","Sixty", "Seventy",
                "Eighty", "Ninety"};

        String[] tens_power = new String[] {"Hundred", "Thousand"};



        // Base cases
        if (length == 0)
        {
            return "";
        }

        if (length > 4)
        {
            //System.out.println("Length more than 4 is not supported");
            //does not support more than 4 digit number
            return "";
        }

        /* For single digit number */
        if (length == 1)
        {
            //System.out.println(single_digits[number[0] - '0']);
            str.append(single_digits[number[0] - '0']);
            return str.toString();
        }

    /* Iterate while num
        is not '\0' */
        int x = 0;
        while (x < number.length)
        {

            /* Code path for first 2 digits */
            if(length>=3) {
                if (number[x]-'0' != 0)
                {
                    // first statement will give the digit ex: one, three, etc
                    //second statement will give 'hundred' or 'thousand'

                    //System.out.print(single_digits[number[x] - '0']+" ");
                    //System.out.print(tens_power[length - 3]+" ");
                    str.append(single_digits[number[x] - '0']+"");
                    str.append(tens_power[length - 3]+"");
                    // here len can be 3 or 4
                }
                --length;
                //return str.toString() ;
            }

            /* Code path for last 2 digits */
           // for (length=2;;)
            else {
            // Need to explicitly handle 10-19. Sum of the two digits is used as index of "two_digits"  array of strings *//*

                if (number[x] - '0' == 1)
                {
                    int sum = number[x] - '0' + number[x+1] - '0';
                    //System.out.println(two_digits[sum]);
                    return two_digits[sum];
                }

                //* Need to explicitly handle 20 *//*
                else if (number[x] - '0' == 2 && number[x + 1] - '0' == 0)
                {
                    //System.out.println("Twenty");
                    return "Twenty";
                }

                //* Rest of the two digit numbers i.e., 21 to 99 *//*
                else
                {
                    int i = (number[x] - '0');
                    //if 2nd last in  the 4-digit zero-indexed character array is not zero
                    if(i > 0)
                    {
                        //System.out.print(tens_multiple[i] + " ");
                        str.append(tens_multiple[i] + "");
                    }

                    //if it is zero
                    else
                    {
                    //System.out.print("");
                    str.append("");
                    }

                    // move to final position in the 4-digit zero-indexed character array
                    ++x;

                    if (number[x] - '0' != 0)
                    {
                        str.append(single_digits[number[x] - '0']);
                    }
                }
                ++x;
            }

        }
        return str.toString();
    }


    public static String toWords(String number)
    {

      String[] singleDigits = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[] doubleDigits = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tensMultiple = {"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] tensPower = {"Hundred", "Thousand", "Thousand", "Lakh", "Lakh", "Crore", "Crore"};

        int length = number.length();

        if (length == 0) {
            return "";
        }
        else if (length == 1)
        {
            return singleDigits[number.charAt(0) - 48];
        }
        else {
            String s = "";
            int x = 0;
            while (length >= 3) {
                if (number.charAt(x) == '0')
                {/*Do Nothing*/
                    x++;
                    length--;
                }
                else if (length == 5 || length == 7 || length == 9)
                {
                    s = (s + "" + toWords(number.charAt(x) + "" + number.charAt(x + 1)) + "" + tensPower[length - 3]).trim();
                    x = x + 2;
                    length = length - 2;
                }
                else {
                    s = (s + "" + singleDigits[number.charAt(x) - 48] + "" + tensPower[length - 3]).trim();
                    x++;
                    length--;
                }
            }
            // handling last two digits
            if (number.charAt(x) == '0')
            {
                if (number.charAt(x + 1) == '0') return (s + "").trim();
                return (s + "" + singleDigits[number.charAt(x + 1) - 48]).trim();
            } else if (number.charAt(x) == '1') {
                return (s + "" + doubleDigits[number.charAt(x + 1) - 48]).trim();
            } else {
                if (number.charAt(x + 1) == '0') return (s + "" + tensMultiple[number.charAt(x) - '2']).trim();
                return (s + " " + tensMultiple[number.charAt(x) - '2'] + "" + singleDigits[number.charAt(x + 1) - 48]).trim();
            }
       }
    }



}
