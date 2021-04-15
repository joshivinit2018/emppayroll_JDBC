package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Employee_Payroll_Test {
    @Test
    void givenEmployeePayroll_WhenRetrieved_ShouldMatchEmpCount() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        List<EmployeePayrollData> employeePayrollDataList= employee_Payroll.readData();
        Assertions.assertEquals(3,employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_whenUpdate_shouldSyncWithDB() {
        Employee_payroll employee_Payroll = new Employee_payroll();
        employee_Payroll.updateData();
        List<EmployeePayrollData> employeePayrollDataList=employee_Payroll.readData();
        Assertions.assertEquals(3,employeePayrollDataList.size());
        long result=employee_Payroll.updateData();
        Assertions.assertEquals(1,result);
    }

}
