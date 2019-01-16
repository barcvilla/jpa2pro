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
    
    private void logTransaction(int empId, String action)
    {
        // verificamos el id del empleado si es valido
        if(em.find(Employee.class, empId) == null)
        {
            throw new IllegalArgumentException("Unknown employee id");
        }
        LogRecord logRecord = new LogRecord(empId, action);
        em.persist(logRecord);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void logTransaction2(int empId, String action)
    {
        logTransaction(empId, action);
    }
}
