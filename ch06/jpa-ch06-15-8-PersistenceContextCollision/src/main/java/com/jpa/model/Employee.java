/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "employee_extendedpersistencecntx")
public class Employee implements Serializable{
    @Id
    private int id;
    private String name;
    
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @ManyToOne
    private Department department;
    private long salary;
    
    public Employee(){}

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
    
    public void setDepartment(Department dept)
    {
        this.department = dept;
    }
    
    public Department getDepartment()
    {
        return department;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", startDate=" + startDate + ", salary=" + salary + '}';
    }
}
