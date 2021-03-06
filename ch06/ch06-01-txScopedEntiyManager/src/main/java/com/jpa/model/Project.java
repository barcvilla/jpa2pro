/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author PC
 */
@Entity
public class Project implements Serializable {

    @Id
    protected int id;
    protected String name;
    @ManyToMany(mappedBy = "projects")
    private Collection<Employee> employees;

    public Project() {
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

    public String toString() {
        String empString = "";
        for (Employee e : getEmployees()) {
            empString += "(" + e.getId() + ", " + e.getName() + ")";
        }
        return "Project id: " + getId() + ", name: " + getName()
                + ", emps={" + empString + "}";
    }
}
