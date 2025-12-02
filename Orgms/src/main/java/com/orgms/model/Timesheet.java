package com.orgms.model;

import java.util.Date;

public class Timesheet {
    private String timesheetId;
    private String userId;
    private String employeeId;
    private String projectId;
    private String taskId;
    private Date weekStart;
    private Date weekEnd;
    private String dayHoursJson;
    private double weeklyTotal;
    private String status;

    // getters/setters...
    public String getTimesheetId(){return timesheetId;}
    public void setTimesheetId(String id){this.timesheetId=id;}
    public String getUserId(){return userId;}
    public void setUserId(String u){this.userId=u;}
    public String getEmployeeId(){return employeeId;}
    public void setEmployeeId(String e){this.employeeId=e;}
    public String getProjectId(){return projectId;}
    public void setProjectId(String p){this.projectId=p;}
    public String getDayHoursJson(){return dayHoursJson;}
    public void setDayHoursJson(String j){this.dayHoursJson=j;}
    public double getWeeklyTotal(){return weeklyTotal;}
    public void setWeeklyTotal(double t){this.weeklyTotal=t;}
    public String getStatus(){return status;}
    public void setStatus(String s){this.status=s;}
	public Date getWeekStart() {
		// TODO Auto-generated method stub
		return null;
	}
	public Date getWeekEnd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getTaskId() {
		// TODO Auto-generated method stub
		return null;
	}
}

