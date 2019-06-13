/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Facede
 * Para usar el patron Session Facade para capturar los cambios en las entidades, proveemos un metodo de negocio que
 * unira los cambios hechos a una instancia entidad detached (separada). En este ejemplo, aceptamos una instancia
 * Employee y la mezclamos en un contexto de persistencia transaction-scoped.
 * @author PC
 */
@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "EmployeeService")
    private EntityManager em;
    
    public List<Employee> findAll()
    {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
    
    /**
     * Al metodo updateEmployee se le pasa una instancia detached de employee, primero se intenta verificar si
     * ya existe un empleado, si no lo hay una excepcion es lanzada porque no queremos que un nuevo empleado sea creado
     * Luego usamos el metodo merge() para copiar los cambios al contexto de persistencia, los cuales son luego guardados
     *  cuando se realiza el commit de la transaccion.
     * 
     */
    public void updateEmployee(Employee emp)
    {
        if(em.find(Employee.class, emp.getId()) == null)
        {
            throw new IllegalArgumentException("Unknown employee id: " + emp.getId());
        }
        em.merge(emp);
    }
}
