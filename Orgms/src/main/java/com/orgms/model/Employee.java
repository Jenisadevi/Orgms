package com.orgms.model;

import java.util.Date;

public class Employee {
    private String employeeId;
    private String employeeCode;
    private String empName;
    private String empCemail;
    private Date empDob;
    private String empAddress;
    private String empDesignation;
    private String empBgp;
    private String empCmob;
    private boolean isActive;

    // getters/setters
    public String getEmployeeId(){return employeeId;}
    public void setEmployeeId(String id){this.employeeId=id;}
    public String getEmployeeCode(){return employeeCode;}
    public void setEmployeeCode(String code){this.employeeCode=code;}
    public String getEmpName(){return empName;}
    public void setEmpName(String n){this.empName=n;}
    public String getEmpCemail(){return empCemail;}
    public void setEmpCemail(String e){this.empCemail=e;}
    public Date getEmpDob(){return empDob;}
    public void setEmpDob(Date d){this.empDob=d;}
    public String getEmpAddress(){return empAddress;}
    public void setEmpAddress(String a){this.empAddress=a;}
    public String getEmpDesignation(){return empDesignation;}
    public void setEmpDesignation(String d){this.empDesignation=d;}
    public String getEmpBgp(){return empBgp;}
    public void setEmpBgp(String b){this.empBgp=b;}
    public String getEmpCmob(){return empCmob;}
    public void setEmpCmob(String m){this.empCmob=m;}
    public boolean isActive(){return isActive;}
    public void setActive(boolean a){this.isActive=a;}
}

