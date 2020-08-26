package com.example.itemsdatabasestartup;

public class ModelClassFirebaseForActiveOrdersAdapter {

    private String counter;

    private String customerContact;

    private String customerName;

    private String description;

    private String itemName;

    private String messageKey;

    private String orderNo;

    private String preparationTime;

    private String quantity;

    private String status;

    private String time;

    private String Position;


    public ModelClassFirebaseForActiveOrdersAdapter()
    {

    }


    public ModelClassFirebaseForActiveOrdersAdapter(String counter, String customerContact, String customerName, String description, String itemName, String messageKey, String orderNo, String position,String preparationTime, String quantity, String status, String time) {
        this.counter = counter;
        this.customerContact = customerContact;
        this.customerName = customerName;
        this.description = description;
        this.itemName = itemName;
        this.messageKey = messageKey;
        this.orderNo = orderNo;
        this.preparationTime = preparationTime;
        this.quantity = quantity;
        this.status = status;
        this.Position = position;
        this.time = time;
    }


    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }
}


