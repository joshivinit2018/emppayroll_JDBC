package com.bridgelabz;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DBDemo {
    public static void main(String[] args){

        String jdbcURL = "jdbc:mysql://localhost:3307/payroll_service?useSSL=false";
        String username = "root";
        String password = "admin123";
        Connection connection;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!",e);
        }
        ListDrivers();
        try {
            System.out.println("connecting to database"+jdbcURL);
            connection= DriverManager.getConnection(jdbcURL,username,password);
            System.out.println("connection is successful!!!!"+connection);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void ListDrivers(){
        Enumeration<java.sql.Driver> driverlist=DriverManager.getDrivers();
        while (driverlist.hasMoreElements()){
            Driver driverClass = (Driver) driverlist.nextElement();
            System.out.println(" "+driverClass.getClass().getName());
        }
    }
}
