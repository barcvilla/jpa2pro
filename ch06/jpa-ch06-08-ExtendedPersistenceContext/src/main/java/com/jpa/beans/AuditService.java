/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.beans;

import com.jpa.model.Employee;
import com.jpa.model.LogRecord;
import javax.ejb.Stateless;
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
    
    /**
     * Este metodo hereda el contexto de transaccion desde addEmployee() ya que su atributo de transaccion es el por
     * defecto REQUIRED, y una transaccion esta activa durante la llamada al metodo addEmployee(). Cuando el metodo
     * find() es invocado, el entity manager transaction-scope verifica que exista un contexto de persistencia activo
     * y encontrara el contexto de persistencia extendido del DeparmentManagement. Este entonces usara este contexto
     * de persistencia para ejecutar la operacion. Todas las entidades administradas del contexto de persistencia extendido
     * sera visible al entity manager transacion scope
     *  
     */
    public void logTransaction(int empId, String action)
    {
        // verificamos si el numero id es valido
        if(em.find(Employee.class, empId) == null)
        {
            throw new IllegalArgumentException("Unknown employee id");
        }
        LogRecord lr = new LogRecord(empId, action);
        em.persist(lr);
    }
}
