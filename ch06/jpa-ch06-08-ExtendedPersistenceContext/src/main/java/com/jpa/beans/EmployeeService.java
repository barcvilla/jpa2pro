/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.beans;

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
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
    
    public List<Department> findAllDepartment()
    {
        return em.createQuery("select d from Deparment d", Department.class).getResultList();
    }
}
