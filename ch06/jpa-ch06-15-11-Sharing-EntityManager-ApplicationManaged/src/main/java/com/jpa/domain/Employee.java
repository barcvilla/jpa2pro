/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author barcvilla
 */
@Entity
@Table(name = "employee_app_managed")
public class Employee implements Serializable
{
    @Id
    private int id;
    private String name;
    private double salary;
    
    public Employee()
    {}
    
    public Employee(int id)
    {
        this.id = id;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String toString()
    {
        return "Employee id: " + getId() + " name: " + getName() + " salary: " + getSalary();
    }
}
