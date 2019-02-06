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
 * Atributo de transaccion REQUIRES_NEW para forzar una nueva transaccion de forma predeterminada cuando se invoca
 * un metodo de negocio. Para el metodo getName() no necesitamos una nueva transaccion porque no se realizan cambios,
 * por lo que se ha establecido NOT_SUPPORTED. Esto suspendera la transaccion actual, pero no dara lugar a que se
 * cree una nueva transaccion. Con estos cambios, se puede acceder al bean DepartmentManager en cualquier situacion,
 * incluso si ya existe  un contexto de persistencia activo.
 * 
 * Finalmente, la ultima opcion a considerar una entity manager application-managed en lugar de un
 * entity manager extended. Si no hay necesidad de propagar el contexto de persistencia, el entity manager extended
 * no esta adicionando mucho valor sobre el entity manager applicaction-managed. El stateful session bean puede crear
 * de forma segura un entity manager application managed, almacenarlo en la instancia del bean y usarlo para las 
 * operaciones de persistencia sin tener que preocuparse sobre si una transaccion activa ya ha sido propagada al
 * contexto de persistencia 
 * @author PC
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DepartmentManager {

    @PersistenceContext(unitName = "EmployeeService",type = PersistenceContextType.EXTENDED)
    EntityManager em;
    Department dept;
    @EJB
    AuditService audit;

    public void init(int deptId) {
        dept = em.find(Department.class, deptId);
    }

    public Department getDepartment() {
        return dept;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getName() {
        return dept.getName();
    }

    public void setName(String name) {
        dept.setName(name);
    }

    public void addEmployee(int empId) {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(emp.getId(),
                "added to department " + dept.getName());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addEmployee2(int empId) {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(emp.getId(),
                "added to department " + dept.getName());
    }

    @Remove
    public void finished() {
    }
}
