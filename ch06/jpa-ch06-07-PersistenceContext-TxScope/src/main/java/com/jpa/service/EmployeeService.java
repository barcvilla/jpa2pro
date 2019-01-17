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
 * Cuando un metodo es invocado en el entity manager transaction scope, este primero verifica si hay un contexto
 * de persistencia propagado. Si existe, el entity manager utiliza el contexto de persistencia para llevar a cabo
 * la operacion. Si no hay un contexto de persistencia, el entity manager solicita un nuevo contexto de persistencia
 * al persistence provider y luego marca este nuevo contexto de persistence como un contexto de persistencia propagado
 * para la transaccion antes de llevar a cabo la llamda del metodo. Todos las operaciones subsecuentes entity manager
 * transaction scope, en este componente u otro, usara este persistence context recientemente creado. Este comportamiento
 * funciona indepediente de si ha sido utilizado una transaccion demarcada con container-managed o bean managed transaction
 * @author PC
 */
@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "EmployeeService")
    private EntityManager em;
    
    @EJB
    AuditService audit;
    
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
    
    public List<Employee> getAllEmployee()
    {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
}
