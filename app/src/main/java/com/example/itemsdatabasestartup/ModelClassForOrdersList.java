package com.example.itemsdatabasestartup;

public class ModelClassForOrdersList {

    int id;
    int quantity;
    String Name;
    float Total;
    String Description;



    public ModelClassForOrdersList(int id , String Name, int quantity, float Total, String Description)
    {
        this.id = id;
        this.quantity = quantity;
        this.Name = Name;
        this.Total = Total;
        this.Description = Description;

    }

    public ModelClassForOrdersList(int id, int quantity)

    {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
