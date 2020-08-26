package com.example.itemsdatabasestartup;

public class ModelClassForItemDetails
{
    private int ID;
    private String ItemName;
    private float Price;
    private int Counter;
    private String Category;



    private boolean Expanded;


    public ModelClassForItemDetails(int ID , String itemName , float Price, int Counter, String Category)
    {
        this.ID = ID;
        this.ItemName = itemName;
        this.Price = Price;
        this.Counter = Counter;
        this.Category = Category;

        this.Expanded = false;

    }

    public ModelClassForItemDetails(int ID, String itemName, float Price)
    {

        this.ID = ID;
        this.ItemName = itemName;
        this.Price = Price;
    }

    public boolean isExpanded() {
        return Expanded;
    }

    public void setExpanded(boolean expanded) {
        Expanded = expanded;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getCounter() {
        return Counter;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
