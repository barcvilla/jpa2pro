/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Department;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import com.jpa.model.Employee;
import javax.ejb.Remove;

/**
 * Persistence Context Collision
 * Dijimos al inicio que solo un contexto de persistencia podria ser propagado con una transaccion JTA. Tambien dijimos que
 * el contexto de persistencia extendido siempre intentaria ser el mismo el contexto de persistencia activo. Esto puede
 * traer dos situaciones en la cual dos contextos de persistencia colisionen uno con otro. Consideremos el siguiente ejemplo:
 * Un session bean Stateless con un entity manager transaction-scoped crea un nuevo contexto de persistencia y luego invoca
 * un metodo de un session bean Stateful el cual tiene un contexto de persistencia extendido. Durante la asociacion del 
 * contexto de persistencia extendido el contenedor verificara si ya existe un contexto de persistencia activo. Si lo hay,
 * debe ser el mismo contexto de persistencia extendido que se trata de asociar, o una excepcion sera lanzada. en este
 * ejemplo, session bean stateful encontrara el contexto de persistencia transaction-scoped ya creado por el session bean
 * stateless y la llamada al metodo del session bean stateful fallara. Puede solo existir  un contexto de persistencia activo
 * para una transaccion.
 * 
 * 
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DepartmentManager {
    @PersistenceContext(unitName = "EmployeeService", type = PersistenceContextType.EXTENDED)
    EntityManager em;
    
    Department dept;
    @EJB
    AuditService audit;
    
    public void init(int deptId)
    {
        dept = em.find(Department.class, deptId);
    }
    
    public Department getDepartment()
    {
        return dept;
    }
    
    public void setName(String name)
    {
        dept.setName(name);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getName()
    {
        return dept.getName();
    }
    
    /**
     * 
     */
    public void addEmployee(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(emp.getId(), "added to department__ " + dept.getName());
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addEmployee2(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(empId, "added to department " + dept.getName());
    }
    
    @Remove
    public void finished(){}
}
