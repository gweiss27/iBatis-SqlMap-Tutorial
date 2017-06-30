package com.ibatis.demo;

/**
 * Created by gw186023 on 6/30/17.
 */
public class Employee {
    private int emp_id;
    private String emp_name;
    private int emp_salary;

    public Employee() {}

    public Employee(int emp_id, String emp_name, int emp_salary) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_salary = emp_salary;
    }

    public int getEmpId() {
        return emp_id;
    }

    public void setEmpId(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmpName() {
        return emp_name;
    }

    public void setEmpName(String emp_name) {
        this.emp_name = emp_name;
    }

    public int getEmpSalary() {
        return emp_salary;
    }

    public void setEmpSalary(int emp_salary) {
        this.emp_salary = emp_salary;
    }
}
