package com.orgms.model;

public class Customer {
    private String customerId;
    private String custName;
    private String custCode;
    private String custEmail;
    private String custAddress;
    private boolean isActive;

    // getters/setters
    public String getCustomerId(){return customerId;}
    public void setCustomerId(String id){this.customerId=id;}
    public String getCustName(){return custName;}
    public void setCustName(String n){this.custName=n;}
    public String getCustCode(){return custCode;}
    public void setCustCode(String c){this.custCode=c;}
    public String getCustEmail(){return custEmail;}
    public void setCustEmail(String e){this.custEmail=e;}
    public String getCustAddress(){return custAddress;}
    public void setCustAddress(String a){this.custAddress=a;}
    public boolean isActive(){return isActive;}
    public void setActive(boolean a){this.isActive=a;}
}
