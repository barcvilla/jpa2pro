/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Employee;
import com.jpa.model.LogRecord;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class AuditService {
    @PersistenceContext(unitName = "EmployeeService")
    private EntityManager em;
    
    public void logTransaction(int empId, String action)
    {
        if(em.find(Employee.class, empId) == null)
        {
            throw new IllegalArgumentException("Unknown employee id");
        }
        
        LogRecord lg = new LogRecord(empId, action);
        em.persist(lg);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void logTransaction2(int empId, String action)
    {
        logTransaction(empId, action);
    }
}
