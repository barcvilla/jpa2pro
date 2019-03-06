/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Employee;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * @author PC
 */
public class EmployeeService 
{
    protected EntityManager em;
    
    public EmployeeService(EntityManager em)
    {
        this.em = em;
    }
    
    public Employee createEmployee(int id, String name, long salary) {
        Employee emp = new Employee(id);
        emp.setName(name);
        emp.setSalary(salary);
        em.persist(emp);
        return emp;
    }
    
    public Employee findEmployee(int id) {
        return em.find(Employee.class, id);
    }

    public void removeEmployee(int id) {
        Employee emp = findEmployee(id);
        if (emp != null) {
            em.remove(emp);
        }
    }
    
    public Employee raiseEmployeeSalary(int id, long raise) {
        Employee emp = findEmployee(id);
        if (emp != null) {
            emp.setSalary(emp.getSalary() + raise);
        }
        return emp;
    }
    
    public Collection<Employee> findAllEmployees() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        return (Collection<Employee>) query.getResultList();
    }
}
