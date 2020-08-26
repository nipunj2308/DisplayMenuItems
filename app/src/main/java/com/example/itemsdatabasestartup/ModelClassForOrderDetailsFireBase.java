package com.example.itemsdatabasestartup;

import android.media.audiofx.AudioEffect;

public class ModelClassForOrderDetailsFireBase {

    private int OrderNumber;
    private String OrderNo;
    private String Time;
    private String CustomerName;
    private String CustomerContact;
    private String itemNames;
    private String quantities;
    private String Counters;
    private String Descriptions;
    private String Statuses;
    private String ItemTotals;
    private String orderPreparationTime;
    private String OrderTotal;
    private String itemName;
    private int quantity;
    private int counter;
    private String Status;
    private int ItemPositionInArrray;
    private String messageKey;
    private boolean Expanded;


    public ModelClassForOrderDetailsFireBase()
    {

    }


    public ModelClassForOrderDetailsFireBase(int OrderNo, String itemName, int quantity, int Counter,String Description,String Status, String orderPreparationTime, String messageKey, int position)
    {
        this.OrderNumber = OrderNo;
        this.itemName = itemName;
        this.quantity =quantity;
        this.counter  = Counter;
        this.Descriptions = Description;
        this.Status = Status;
        this.orderPreparationTime = orderPreparationTime;
        this.ItemPositionInArrray =position;
        this.messageKey = messageKey;
        //,String Description

    }


    public ModelClassForOrderDetailsFireBase(int OrderNo, String itemName, int quantity, int Counter,String Description,String Status, String orderPreparationTime, int position)
    {
        this.OrderNumber = OrderNo;
        this.itemName = itemName;
        this.quantity =quantity;
        this.counter  = Counter;
        this.Descriptions = Description;
        this.Status = Status;
        this.orderPreparationTime = orderPreparationTime;
        this.ItemPositionInArrray =position;
        //this.messageKey = messageKey;
        //,String Description

    }



    public ModelClassForOrderDetailsFireBase(String OrderNo, String Time, String CustomerName, String CustomerContact, String Items, String Quantities, String Counters, String OrderPreparationTime, String OrderTotal)
    {
        this.OrderNo = OrderNo;
        this.Time = Time;
        this.CustomerName = CustomerName;
        this.CustomerContact = CustomerContact;
        this.itemNames = Items;
        this.quantities = Quantities;
        this.Counters = Counters;
        this.orderPreparationTime = OrderPreparationTime;
        this.OrderTotal = OrderTotal;
        this.Expanded =false;

    }




    public int getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        OrderNumber = orderNumber;
    }


    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerContact() {
        return CustomerContact;
    }

    public void setCustomerContact(String customerContact) {
        CustomerContact = customerContact;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public String getCounters() {
        return Counters;
    }

    public void setCounters(String counters) {
        Counters = counters;
    }


    public String getItemTotals() {
        return ItemTotals;
    }

    public void setItemTotals(String itemTotals) {
        ItemTotals = itemTotals;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String descriptions) {
        Descriptions = descriptions;
    }

    public String getStatuses() {
        return Statuses;
    }

    public void setStatuses(String statuses) {
        Statuses = statuses;
    }



    public String getOrderPreparationTime() {
        return orderPreparationTime;
    }

    public void setOrderPreparationTime(String orderPreparationTime) {
        this.orderPreparationTime = orderPreparationTime;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }


    public boolean isExpanded() {
        return Expanded;
    }

    public void setExpanded(boolean expanded) {
        Expanded = expanded;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public int getItemPositionInArrray() {
        return ItemPositionInArrray;
    }

    public void setItemPositionInArrray(int itemPositionInArrray) {
        ItemPositionInArrray = itemPositionInArrray;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
