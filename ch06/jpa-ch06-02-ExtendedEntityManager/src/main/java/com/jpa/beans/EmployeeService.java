/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.beans;

import com.jpa.model.Department;
import com.jpa.model.Employee;
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
    /**
     * Entity Transaction Scope
     * Asumiendo que no hay una transaccion activda desde el cliente, cada metodo del bean start y commit una nueva
     * transaccion ya que el atributo de la transaccion por defecto para cada metodo es REQUIRED. Debido a que hay
     * una nueva transaccion para cada metodo, el entity manager usara un persistence context diferente cada vez.
     */
    @PersistenceContext(unitName = "EmployeeService")
    EntityManager em;
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
    
    public List<Department> findAllDepartments()
    {
        return em.createQuery("select d from dept d", Department.class).getResultList();
    }
}
