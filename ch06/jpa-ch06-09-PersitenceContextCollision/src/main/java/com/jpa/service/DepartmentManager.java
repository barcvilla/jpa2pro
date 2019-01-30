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
 *
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
