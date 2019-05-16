/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Department;
import com.jpa.domain.Employee;
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
    private EntityManager em;
    
    public List<Employee> findAll()
    {
        List<Employee> employees = em.createQuery("select e from Employee e", Employee.class).getResultList();
        for(Employee e : employees)
        {
            Department dept = e.getDepartment();
            if(dept != null)
            {
                dept.getName();
            }
            
        }
        return employees;
    }
}
