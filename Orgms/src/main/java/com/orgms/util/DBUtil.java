package com.orgms.util;
import java.sql.*;
public class DBUtil {
  private static final String URL = "jdbc:mysql://localhost:3306/orgms?useSSL=false&serverTimezone=UTC";
  private static final String USER = "root";
  private static final String PASS = "jenisa@123";
  static { try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (Exception e){ throw new RuntimeException(e);} }
  public static Connection getConnection() throws SQLException { return DriverManager.getConnection(URL,USER,PASS); }
  public static void close(AutoCloseable... ac) { for(AutoCloseable a:ac) if(a!=null) try{a.close();}catch(Exception ignored){} }
}

