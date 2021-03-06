/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * En este ejemplo Employee tiene una relacion many-to-one con Department, el tipo fetch por defecto para una relacion
 * many-to-one es eager loading, pero la clase fue modelada para usar explicitamente lazy loading. Removiendo el fetch type
 * Lazy de la relacion con Department o especificando fetch type Eager explicitamente, nos aseguramos que la instancia de
 * Department esta siempre disponible a la instancia de Employee
 * @author PC
 */
@Entity
@Table(name = "employee_detachment_with_triggeredlazyloading")
public class Employee implements Serializable{
    @Id
    private int id;
    private String name;
    private long salary;
    
    @ManyToOne(fetch = FetchType.EAGER)
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

    public void setName(String name) {
        this.name = name;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", salary=" + salary + ", department=" + department + '}';
    }
    
    
}
