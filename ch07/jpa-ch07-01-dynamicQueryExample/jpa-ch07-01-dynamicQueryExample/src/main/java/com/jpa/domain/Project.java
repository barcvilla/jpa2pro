/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "project")
public class Project implements Serializable{
    @Id
    private int id;
    private String name;
    
    @ManyToMany(mappedBy = "projects")
    private Collection<Employee> employee;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Employee> getEmployee() {
        return employee;
    }
    
    public String toString()
    {
        return "Project id: " + getId() + ", name: " + getName();
    }
}
