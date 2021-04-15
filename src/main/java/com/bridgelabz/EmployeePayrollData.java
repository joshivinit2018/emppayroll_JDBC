 package com.bridgelabz;

import java.sql.Date;

public class EmployeePayrollData {

    public int id;
    public String name;
    public Date date;
    public double salary;
    public String gender;


    public EmployeePayrollData(int id, String name, Date date, double salary, String gender)

    {
        this.id=id;
        this.name=name;
        this.date=date;
        this.salary=salary;
        this.gender=gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                '}';

    }



}
