/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.services;

import com.jpa.domain.Employee;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "EmployeeService")
    EntityManager em;
    
    public void updateEmployee(Employee emp)
    {
        if(em.find(Employee.class, emp.getId()) == null)
        {
            throw new IllegalArgumentException("Unknow employee id: " + emp.getId());
        }
        Employee managedEmp = em.merge(emp);
        managedEmp.setLastAccessTime(LocalDateTime.now());
    }
    
    public void updateEmployeeIncorrect(Employee emp)
    {
        if(em.find(Employee.class, emp.getId()) == null)
        {
            throw new IllegalArgumentException("Unknown employee id: " + emp.getId());
        }
        em.merge(emp);
        emp.setLastAccessTime(LocalDateTime.now());
    }
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("Select e from Employee e", Employee.class).getResultList();
    }
}
