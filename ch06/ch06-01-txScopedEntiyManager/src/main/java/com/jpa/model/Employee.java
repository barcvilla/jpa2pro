/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Entity
public class Employee implements Serializable {

    @Id
    private int id;
    private String name;
    private long salary;
    @ManyToMany
    private Collection<Project> projects;

    public Employee() {
        projects = new ArrayList<Project>();
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

    public long getSalary() {
        return salary;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public String toString() {
        return "Employee " + getId()
                + ": name: " + getName()
                + ", salary: " + getSalary();
    }

}
