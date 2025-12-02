package com.orgms.model;

import java.util.Date;

public class Project {
    private String projectId;
    private String jobName;
    private String customerId;
    private String jobCodeJson;
    private double totalCost;
    private double totalHours;
    private String status;
    private String createdBy;
    private Date createdAt;

    // getters/setters
    public String getProjectId(){return projectId;}
    public void setProjectId(String id){this.projectId=id;}
    public String getJobName(){return jobName;}
    public void setJobName(String j){this.jobName=j;}
    public String getCustomerId(){return customerId;}
    public void setCustomerId(String c){this.customerId=c;}
    public String getJobCodeJson(){return jobCodeJson;}
    public void setJobCodeJson(String j){this.jobCodeJson=j;}
    public double getTotalCost(){return totalCost;}
    public void setTotalCost(double c){this.totalCost=c;}
    public double getTotalHours(){return totalHours;}
    public void setTotalHours(double h){this.totalHours=h;}
    public String getStatus(){return status;}
    public void setStatus(String s){this.status=s;}
	public void setCreatedBy(String attribute) {
		// TODO Auto-generated method stub
		
	}
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return null;
	}
}
