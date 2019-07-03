/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author PC
 */
@Stateless
public class QueryService {
    private static final String Query = "SELECT e.salary FROM Employee e WHERE e.department.name = :deptName AND e.name = :empName ";
    
    @PersistenceContext(unitName = "DynamicQueries")
    EntityManager em;
    
    public long queryEmpSalary(String deptName, String empName)
    {
        String query = "SELECT e.salary FROM Employee e WHERE e.department.name = '"+deptName+"' AND e.name = '"+empName+"' ";
        try
        {
            return em.createQuery(query, Long.class).getSingleResult();
        }
        catch(NoResultException e)
        {
            return 0;
        }
    }
    
    public long queryEmpSalaryUsingParams(String deptName, String empName)
    {
        try
        {
            return em.createQuery(Query, Long.class)
                    .setParameter("deptName", deptName)
                    .setParameter("empName", empName)
                    .getSingleResult();
        }
        catch(NoResultException e)
        {
            return 0;
        }
    }
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }
}
