/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.beans;

import com.jpa.model.Department;
import com.jpa.model.Employee;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author PC
 */
@Stateful
public class DepartmentManager {
    @PersistenceContext(unitName = "EmployeeService")
    EntityManager em;
    
    int deptId;
    
    public void init(int deptId)
    {
        this.deptId = deptId;
    }
    
    public Department getDepartment()
    {
        return em.find(Department.class, deptId);
    }
    
    public void setName(String name)
    {
        Department dept = em.find(Department.class, deptId);
        dept.setName(name);
    }
    
    public void addEmployee(int empId)
    {
        Department dept = em.find(Department.class, deptId);
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
    }
    
    /*
    @Remove
    public void finished(){}
    */
}
