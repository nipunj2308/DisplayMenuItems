package com.example.itemsdatabasestartup;

public class ModelClassForSalesAndOrderSummary {
    private boolean expanded;
    private String Date;



    public ModelClassForSalesAndOrderSummary(String Date)
    {
        this.Date = Date;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
        this.expanded = false;
    }
}
