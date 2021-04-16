package com.bridgelabz;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Employee_payroll {
    private String employeePayrollData;
    private int ConnectionCounter=0;

    private synchronized Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3307/payroll_service?useSSL=false";
        String username = "root";
        String password = "admin123";
        Connection connection = null;
        System.out.println("Connecting to database: " +jdbcURL);
        System.out.println("Processing Thread "+Thread.currentThread().getName()+"Connecting to database with id "+ConnectionCounter);
        connection= DriverManager.getConnection(jdbcURL,username,password);
        System.out.println("Processing Thread "+Thread.currentThread().getName()+" id "+ConnectionCounter+ " Connection was sucessfull!!! " +connection);
        System.out.println("Connection successful: "+connection);
        return connection;

    }


    public List<EmployeePayrollData> readData() {
        List<EmployeePayrollData> employeePayrollDataArrayList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from employee_payroll where is_active=true; ");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                EmployeePayrollData employeePayrollData = new EmployeePayrollData(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDouble(4),
                        resultSet.getString(5));
                employeePayrollDataArrayList.add(employeePayrollData);
            }
            System.out.println(employeePayrollDataArrayList.toString());
            connection.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataArrayList;
    }

    public long updateData(int id, double salary) {
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Update employee_payroll set salary=? where id=? ; ");
            preparedStatement.setDouble(1, salary);
            preparedStatement.setInt(2, id);
            long resultSet = preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


    public List<EmployeePayrollData> employeeDetailsfromDate(String date) {
        List<EmployeePayrollData> employeePayrollDataArrayList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from employee_payroll where start>=? ");
            preparedStatement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                EmployeePayrollData employeePayrollData = new EmployeePayrollData(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDouble(4),
                        resultSet.getString(5));
                employeePayrollDataArrayList.add(employeePayrollData);

            }
            System.out.println(employeePayrollDataArrayList.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataArrayList;
    }

    public List<String> Database_operation() {
        List<String> list = new ArrayList();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select gender,sum(salary), Avg(salary),min(salary),max(salary),count(salary) from employee_payroll group by gender; ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int num = 1;
                System.out.println("Gender: " + resultSet.getString(1));
                System.out.println("Salary: " + resultSet.getString(2));
                for (int i = 0; i < 6; i++) {
                    if (num < 8) {
                        list.add(i, resultSet.getString(num));
                        num++;
                    }
                }
                System.out.println(list);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void AddDataInTable(String name, String date, double salary, String gender, int payroll_id) throws SQLException {
        try {
            Connection connection = this.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(name,start,salary,gender) values(?,?,?,?); ");

            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, salary);
            preparedStatement.setString(4, gender);
            int result = preparedStatement.executeUpdate();
            int j = 3;
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into payroll_details(name, start, salary, gender, payroll_id,basic_pay,deduction,tax_pay,tax,net_pay) " +
                                                                                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ");
            preparedStatement1.setString(1, name);
            preparedStatement1.setDate(2, Date.valueOf(date));
            preparedStatement1.setDouble(3, salary);
            preparedStatement1.setString(4, gender);
            preparedStatement1.setInt(5, payroll_id);
            preparedStatement1.setDouble(6, salary);
            double deductions = salary * 0.2;
            preparedStatement1.setDouble(7, deductions);
            double taxable_pay = salary - deductions;
            preparedStatement1.setDouble(8, taxable_pay);
            double tax = taxable_pay * 0.1;
            preparedStatement1.setDouble(9, tax);
            double net_pay = salary - tax;
            preparedStatement1.setDouble(10, net_pay);
            int resultSet1 = preparedStatement1.executeUpdate();
            j++;
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void delete_Employee_From_Employee_Payroll_Table(String name) throws SQLException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from employee_payroll where name=?; ");
            preparedStatement.setString(1, name);
            int resultSet = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
    }

    public void UsingArrayListAddMultipleEmployee(List<EmployeePayrollData> employeePayrollData) throws SQLException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(id,name,start,salary,gender) values(?,?,?,?,?) ;");
            for (Iterator<EmployeePayrollData> iterator = employeePayrollData.iterator(); iterator.hasNext(); ) {
                EmployeePayrollData employeePayrollData1 = (EmployeePayrollData) iterator.next();
                preparedStatement.setInt(1, employeePayrollData1.getId());
                preparedStatement.setString(2, employeePayrollData1.getName());
                preparedStatement.setDate(3, employeePayrollData1.getDate());
                preparedStatement.setDouble(4, employeePayrollData1.getSalary());
                preparedStatement.setString(5, employeePayrollData1.getGender());
                preparedStatement.addBatch();
            }
            int[] updatecounts = preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
    }


    public int deactivateEmployee(String name) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update employee_payroll set is_active=false where name=?;");
        preparedStatement.setString(1, name);
        return preparedStatement.executeUpdate();

    }

    public void RecordsinsertInDataBase(String name, String date, double salary, String gender) throws SQLException {
        Connection connection = this.getConnection();
        try {


            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(name,start,salary,gender) values(?,?,?,?); ");
            preparedStatement.setNString(1, name);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, salary);
            preparedStatement.setNString(4, gender);
            int resultSet = preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
        finally {
            connection.close();
        }

    }

    public void insertDataUsingThreads(List<EmployeePayrollData> employeePayrollData){
        Map<Integer, Boolean> employee = new HashMap<>();
        employeePayrollData.forEach(employeePayrollData1 -> {
            Runnable task = () -> {
                employee.put(employeePayrollData.hashCode(),  false);
                System.out.println("Employee being added : " + Thread.currentThread().getName());

                try {
                    this.RecordsinsertInDataBase(employeePayrollData1.name, String.valueOf(employeePayrollData1.date), employeePayrollData1.salary,employeePayrollData1.gender);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                employee.put(employeePayrollData.hashCode(), true);
                System.out.println("Employee added : " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, employeePayrollData1.name);
            thread.start();
        });
        while (employee.containsValue(false)){

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("" + this.employeePayrollData);
            }
}