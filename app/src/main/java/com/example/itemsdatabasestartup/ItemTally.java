package com.example.itemsdatabasestartup;

public class ItemTally
{
    private String ItemName;
    private int CurrentMonthTally;
    private int CurrentDayTally;


    public ItemTally(String itemName , int MonthTally , int daytally)
    {
        this.ItemName = itemName;
        this.CurrentMonthTally = MonthTally;
        this.CurrentDayTally = daytally;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getCurrentMonthTally() {
        return CurrentMonthTally;
    }

    public void setCurrentMonthTally(int currentMonthTally) {
        CurrentMonthTally = currentMonthTally;
    }

    public int getCurrentDayTally() {
        return CurrentDayTally;
    }

    public void setCurrentDayTally(int currentDayTally) {
        CurrentDayTally = currentDayTally;
    }




}
