/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

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
 * La restriccion de un solo stateful session bean con un contexto de persistencia extendida que puede participar
 * en una Transaccion JTA puede causar dificultades en algunas situacion. Por ejemplo, el patron que seguimos 
 * anteriormente en este capitulo para el contexto de persistencia extendida era encapsular  el comportamiento
 * de una entidad detras de un facade session con estado. En este ejemplo, los cliente trabajaron con un session bean
 * DepartmentManager en lugar de la instancia de la entidad Department, ya que un departamento tiene un manager,
 * tiene sentido esta facade para la entidad employee
 * 
 * DepartmentManager retorna un bean EmployeeManager desde el metodo getManager() a fin de representar
 * al manager del departamento. el bean EmployeeManager es injectado y luego inicializado durante la invocacion
 * del metodo init()
 * @author PC
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DepartmentManager {
    @PersistenceContext(unitName = "EmployeeService", type = PersistenceContextType.EXTENDED)
    EntityManager em;
    Department dept;
    @EJB
    AuditService audit;
    @EJB
    EmployeeManager manager;
    
    public void init(int deptId)
    {
        dept = em.find(Department.class, deptId);
        manager.init();
    }
    
    public Department getDepartment()
    {
        return dept;
    }
    
    public void setName(String name)
    {
        dept.setName(name);
    }
    
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
