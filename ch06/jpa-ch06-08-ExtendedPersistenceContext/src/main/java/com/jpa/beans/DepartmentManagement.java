/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.beans;

import com.jpa.model.Department;
import com.jpa.model.Employee;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author PC
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DepartmentManagement {
    @PersistenceContext(unitName = "EmployeeService", type = PersistenceContextType.EXTENDED)
    EntityManager em;
    Department dept;
    
    @EJB
    AuditService audit;
    
    public void init(int deptId)
    {
        dept = em.find(Department.class, deptId);
    }

    public Department getDepartment() {
        return dept;
    }

    public void setName(String name) {
        dept.setName(name);
    }
    
    /**
     * Este metodo tiene un atributo de transaccion de tipo REQUIRED. Ya que le contenedor se asocia a contexto de 
     * persistencia extendido, el contexto de persistencia extendido almacenado en el session bean estara inmediatamente
     * asociado con la transaccion cuando la llamada al metodo inicia. esto causa que la relacion entre las entidades
     * administradas Department y Employee sean persistido en la BD cuando la transaccion hace commits. Esto tambien
     * sisgnifica que el contexto de persistencia extendido estara ahora compartido por otro contexto de persistencia
     * transaction-scoped usado en metodo llamado desde addEmployee()
     *  
     */
    public void addEmployee(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(empId, "added to department " + dept.getName());
    }
    
    @Remove
    public void finished(){}
}
