/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "employee_07_dynamic_query_defination")
public class Employee implements Serializable{
    @Id
    private int id;
    private String name;
    private long salary;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @ManyToOne
    private Employee manager;
    
    @OneToMany(mappedBy = "manager")
    private Collection<Employee> directs;
    
    @ManyToOne
    private Department department;
    
    @ManyToMany
    private Collection<Project> projects;
    
    public Employee()
    {
        directs = new ArrayList<Employee>();
        projects = new ArrayList<Project>();
    }

    public int getId() {
        return id;
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

    public Employee getManager() {
        return manager;
    }

    public Collection<Employee> getDirects() {
        return directs;
    }

    public Department getDepartment() {
        return department;
    }

    public Collection<Project> getProjects() {
        return projects;
    }
    
    public String toString()
    {
        return "Employee: " + getId() + 
                ", name: " + getName() +
                ", salary: " + getSalary() +
                ", dept: " + ((getDepartment() == null) ? null : getDepartment().getName());
    }
}
