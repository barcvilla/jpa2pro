/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "dept")
public class Department implements Serializable{
    @Id
    private int id;
    private String name;
    
    @OneToMany(mappedBy = "department")
    private Collection<Employee> employees;
    
    public Department()
    {
        employees = new ArrayList<Employee>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }
    
    public String toString()
    {
        return "Department no: " + getId() + ", name: " + getName();
    }
}
