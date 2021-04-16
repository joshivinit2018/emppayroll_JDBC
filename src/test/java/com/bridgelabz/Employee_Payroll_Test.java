package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee_Payroll_Test {
    private Employee_payroll employee_Payroll;

    @BeforeEach
    void setUp() {
        employee_Payroll = new Employee_payroll();
    }

    @Test
    public void givenEmployeePayroll_WhenRetrieved_ShouldMatchEmpCount() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        List<EmployeePayrollData> employeePayrollDataList = employee_Payroll.readData();
        Assertions.assertEquals(4, employeePayrollDataList.size());

    }

    @Test
    public void givenNewSalaryForEmployee_whenUpdate_shouldSyncWithDB() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        double salary = 500000;
        int id = 3;
        long result = employee_Payroll.updateData(id, salary);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void givenDateRangeToEmployeePayRollInDB_WhenRetrieved_DataShouldMatchFilteredEmployeeCount() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        String date = "2019-11-13";
        List<EmployeePayrollData> employeePayrollDataList = employee_Payroll.employeeDetailsfromDate(date);
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void given_sum_avg_min_max_count() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        List<String> list = employee_Payroll.Database_operation();
        Assertions.assertEquals(18, list.size());
    }

    @Test
    public void insert_new_employee_in_employee_table() throws SQLException {
         Employee_payroll employee_Payroll = new Employee_payroll();
         String name = "Sam";
         String date = "2021-07-07";
         double salary = 900000;
         String gender = "M";

        int payroll_id=2;
        employee_Payroll.AddDataInTable(name, date, salary, gender, payroll_id);
        List<EmployeePayrollData> employeePayrollDataList = employee_Payroll.readData();
        Assertions.assertEquals(19, employeePayrollDataList.size());
    }

    @Test
    public void givenName_ShouldDeleteEmployee() throws SQLException {
        String name= "Sam";
        Employee_payroll employee_Payroll = new Employee_payroll();
        int records = employee_Payroll.deactivateEmployee(name);
        Assertions.assertEquals(2, records);
    }

    @Test
    public void insert_multiple_values_into_a_table_at_a_single_time() throws SQLException {
        Employee_payroll employee_Payroll = new Employee_payroll();
        List<EmployeePayrollData> list=new ArrayList<>();
        list.add(new EmployeePayrollData(0,"sandhya", Date.valueOf("2019-05-19"),600000,"F"));
        list.add(new EmployeePayrollData(0,"Sheetal",Date.valueOf("2019-01-21"),800000,"F"));
        employee_Payroll.UsingArrayListAddMultipleEmployee(list);
        List<EmployeePayrollData> employeePayrollDataList=employee_Payroll.readData();
        Assertions.assertEquals(27,employeePayrollDataList.size());
    }
}
