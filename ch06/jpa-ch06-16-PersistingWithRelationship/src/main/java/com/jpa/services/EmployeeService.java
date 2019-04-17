/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.services;

import com.jpa.domain.Department;
import com.jpa.domain.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class EmployeeService {
   @PersistenceContext(unitName = "EmployeeService")
   EntityManager em;
   
   public Employee createEmployee(int empId, String name, int deptId)
   {
       Department dept = em.find(Department.class, deptId);
       Employee emp = new Employee();
       emp.setId(empId);
       emp.setName(name);
       emp.setDepartment(dept);
       dept.getEmployees().add(emp);
       em.persist(emp);
       return emp;
   }
   
   public List<Employee> findAllEmployees()
   {
       return em.createQuery("select e from Employee as e", Employee.class).getResultList();
   }
   
   public List<Department> findAllDepartments()
   {
       return em.createQuery("select d from Department as d", Department.class).getResultList();
   }
}
