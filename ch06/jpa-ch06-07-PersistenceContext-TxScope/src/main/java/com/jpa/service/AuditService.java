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
 * Audit Service es un ejemplo de la propagacion del contexto de persistencia transactions scope, almacenamos
 * la informacion exitosa al completar una transaccion
 * @author PC
 */
@Stateless
public class AuditService {
    @PersistenceContext(unitName = "EmployeeService")
    private EntityManager em;
    
    public void logTransaction(int empId, String action)
    {
        // verificamos el id del empleado si es valido
        if(em.find(Employee.class, empId) == null)
        {
            throw new IllegalArgumentException("Unknown employee id");
        }
        LogRecord logRecord = new LogRecord(empId, action);
        em.persist(logRecord);
    }
    
    /**
     * Consideremos este metodo que se le ha declarado un Transaction Attribute REQUIRED_NEW en lugar del default
     * REQUIRED. Antes que el metodo logTransaction2() inicie su llamada, el contenedor suspendera la transaccion
     * heredad de createEmployee() e inicia una nueva transaccion. Dentrod el metodo se llama al metodo logTransaction()
     * el cual invoca al metodo find() en el entity manager, verificara la transaccion actual por un contexto de
     * persistencia activo, si este contexto no existe. Un nuevo contexto de persistencia sera creado empezando con
     * la llamada find() y este contexto de persistencia sera el contexto de persitencia activo para lo que queda
     * del metodo logTransaction(). Ya que la transaccion empezo en createEmployee() no se ha hecho commit, la nueva
     * instancia del empleado no esta en la BD y por lo tanto no es visible al nuevo contexto de persistencia. el 
     * metodo find() retornara null, y el metodo logTransaction() lanzara una excepcion
     *  
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void logTransaction2(int empId, String action)
    {
        logTransaction(empId, action);
    }
}
