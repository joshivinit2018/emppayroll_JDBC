package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Employee_Payroll_Test {
    @Test
    public void givenEmployeePayroll_WhenRetrieved_ShouldMatchEmpCount() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        List<EmployeePayrollData> employeePayrollDataList= employee_Payroll.readData();
        Assertions.assertEquals(4,employeePayrollDataList.size());

    }

    @Test
    public void givenNewSalaryForEmployee_whenUpdate_shouldSyncWithDB() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        double salary=500000;
        int id=3;
        long result=employee_Payroll.updateData(id,salary);
        Assertions.assertEquals(1,result);
    }

    @Test
    public void givenDateRangeToEmployeePayRollInDB_WhenRetrieved_DataShouldMatchFilteredEmployeeCount() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        String date = "2019-11-13";
        List<EmployeePayrollData>employeePayrollDataList=employee_Payroll.employeeDetailsfromDate(date);
        Assertions.assertEquals(3,employeePayrollDataList.size());
    }
    @Test
    public void given_sum_avg_min_max_count() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        List<String> list=employee_Payroll.Database_operation();
        Assertions.assertEquals(12,list.size());
    }

    @Test
    public void insert_new_employee_in_employee_table(){
        Employee_payroll employee_Payroll = new Employee_payroll();
        String name="Sam";
        String date="2021-07-07";
        double salary=900000;
        String gender="M";
        employee_Payroll.AddDataInTable(name,date,salary,gender);
        List<EmployeePayrollData> employeePayrollDataList=employee_Payroll.readData();
        Assertions.assertEquals(4,employeePayrollDataList.size());
    }

    @Test
    public void givenNewEmployee_Add_In_Payroll_details() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        int payroll_id=2;
        double basic_pay=8000;
        double deduction=30000;
        double tax_pay=20000;
        double tax=90000;
        double net_pay=4000;
        employee_Payroll.InsertDataInPayroll_Details(payroll_id,basic_pay,deduction,tax_pay,tax,net_pay);
    }
}
