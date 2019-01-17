/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.model.Employee;
import com.jpa.service.EmployeeService;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author PC
 */
@Named(value = "EmployeeController")
@RequestScoped
public class EmployeeController {
    
    private String id;
    private String name;
    private String salary;

    @EJB
    EmployeeService service;
    
    public EmployeeController() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
    
    public void addEmployee()
    {
        Employee emp = new Employee();
        emp.setId(Integer.parseInt(id));
        emp.setName(name);
        emp.setSalary(Long.parseLong(salary));
        service.createEmployee(emp);
    }
    
    public void addEmployee2()
    {
        Employee emp = new Employee();
        emp.setId(Integer.parseInt(id));
        emp.setName(name);
        emp.setSalary(Long.parseLong(salary));
    }
}
