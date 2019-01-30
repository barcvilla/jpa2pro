/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Department;
import com.jpa.model.Employee;
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
    
    public List<Employee> findAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class)
                 .getResultList();
    }

    public List<Department> findAllDepartments() {
        return em.createQuery("SELECT d FROM Department d", Department.class)
                 .getResultList();
    }
}
