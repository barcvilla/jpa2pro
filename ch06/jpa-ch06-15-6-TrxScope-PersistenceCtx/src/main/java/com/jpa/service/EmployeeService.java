/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Employee;
import java.util.List;
import javax.ejb.EJB;
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
    private EntityManager em;
    
    @EJB
    AuditService audit;
    
    //Luego que se crea un empleado, el metodo logTransaction del AuditService es invocado para registrar el evento
    /**
     * Aunque el nuevo empleado creado no esta aun en la BD, el bean audit puede encontrar la entidad y verificar que
     * este existe. Esto es posible ya que ambos session beans utilizan el mismo contexto de persistencia
     * 
     * Cuando se llama a audit.logTransaction() se llama al metodo find() del entity manager del bean AuditService. Eso
     * nos garantiza estar en una transaccion porque el atributo es REQUIRED por defecto y el Container-managed transaction
     * desde el createEmployee() ha sido extendido a este metodo por el contenedor. Cuando el find() es invocado, el 
     * contenedor nuevamente verifica un contexto de persistencia activo. este encuentra uno creado en el metodo 
     * createEmployee() y utiliza ese contexto de persistencia para buscar la entidad. Ya que la nueva instancia de la
     * entidad Employee creada es manejado por este contexto de persistencia, este es retornado exitosamente.
     */
    public void createEmployee(Employee emp)
    {
        em.persist(emp);
        audit.logTransaction(emp.getId(), "created employee");
    }
    
    public void createEmployee2(Employee emp)
    {
        em.persist(emp);
        audit.logTransaction2(emp.getId(), "created employee");
    }
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("Select e from Employee e", Employee.class).getResultList();
    }
}
