/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Employee;
import com.jpa.domain.ParkingSpace;
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
    
    public void removeParkingSpace(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        ParkingSpace ps = emp.getParkingSpace();
        ps.setEmployee(null);
        emp.setParkingSpace(null);
        em.remove(ps);
    }
    
    public void removeParkingSpaceWithFailure(int empId)
    {
        //olvidamos aplicar null value la relacion causara a una falla en db
        Employee emp = em.find(Employee.class, empId);
        em.remove(emp.getParkingSpace());
    }
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
}
