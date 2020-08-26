package com.example.itemsdatabasestartup;

public class ModelClassForAdminIDDetailsFirebase {


    private String email;
    private String password;
    private String emailPassword;
    private String OutletName;
    private String OutletType;
    private String Location;

    public ModelClassForAdminIDDetailsFirebase(String email, String password, String emailPassword, String outletName, String outletType, String location) {
        this.email = email;
        this.password = password;
        this.emailPassword = emailPassword;
        OutletName = outletName;
        OutletType = outletType;
        Location = location;
    }

    public ModelClassForAdminIDDetailsFirebase(String email, String password, String emailPassword) {
        this.email = email;
        this.password = password;
        this.emailPassword = emailPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getOutletName() {
        return OutletName;
    }

    public void setOutletName(String outletName) {
        OutletName = outletName;
    }

    public String getOutletType() {
        return OutletType;
    }

    public void setOutletType(String outletType) {
        OutletType = outletType;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
