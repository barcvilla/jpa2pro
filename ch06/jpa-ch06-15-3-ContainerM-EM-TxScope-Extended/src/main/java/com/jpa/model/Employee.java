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
@Table(name = "employee_em_txscope_extended_15_3")
public class Employee implements Serializable{
    @Id
    private int id;
    private String name;
    private long salary;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @ManyToOne
    private Department department;
    

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public long getSalary() {
        return salary;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department dept) {
        this.department = dept;
    }
    
    public String toString() {
        return "Employee " + getId() + 
               ": name: " + getName() +
               ", salary: " + getSalary() +
               ", dept: " + ((getDepartment() == null) ? null : getDepartment().getName());
    }
}
