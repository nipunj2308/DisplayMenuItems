package com.example.itemsdatabasestartup;

public class ModelClassForItemSold {
    private String ItemName;
    private double itemPrice;
    private double ItemRevenue;
    private int CounterNo;


    public ModelClassForItemSold(String itemName, double itemPrice, double ItemRevenue, int CounterNo)
    {
        this.ItemName =itemName;
        this.itemPrice = itemPrice;
        this.ItemRevenue = ItemRevenue;
        this.CounterNo = CounterNo;

    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemRevenue() {
        return ItemRevenue;
    }

    public void setItemRevenue(double itemRevenue) {
        ItemRevenue = itemRevenue;
    }

    public int getCounterNo() {
        return CounterNo;
    }

    public void setCounterNo(int counterNo) {
        CounterNo = counterNo;
    }
}
