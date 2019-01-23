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
    
    /**
     * Aunque el nuevo empleado creado no esta aun en la BD, audit bean puede encontrar la entidad y verificar si esta
     * existe. Esto se realiza debido a que los dos bean estan compartiendo el mismo contexto de persistencia.
     * El atributo de la transaccion del metodo createEmployee() es REQUIRED por defecto ya que no se especifica
     * un atributo expicitamente. 
     * El contenedor garantiza que una transaccion se haya iniciado antes de que el metodo es invocado. Cuando
     * se llama al metodo persist() en el entity manager, el contenedor verifica si un contexto de persistencia ya esta
     * asociado con la transaccion. Asumamos que en este caso, que esta fue la primera operacion del entity manager
     * en la transaccion, asi que el contenedor crea un nuevo contexto de persistencia y la marca como propagada.
     * 
     * Cuando el metodo logTransaction() inicia, se lleva a cabo el metodo find() en el entity manager de AuditService
     * Se nos garantiza estar en una transaccion ya que el atributo de la transaccion es REQUIRED y el la transaccion
     * container-management del metodo createEmployee() ha sido extendido a este metodo por el contenedor.
     * Cuando el metodo fin() es invocado, el contenedor nuevamente verifica por un contexto de persistencia activo. Este
     * encuentra el unico creado en el metodo createEmployee() y utiliza ese contexto de persistencia para buscar por
     * la entidad. Debido a la nueva instancia del empleado creada es manejada por el contexto de persistencia,
     * este es retornado satisfactoriamente.
     * @param emp 
     */
    public void createEmployee(Employee emp)
    {
        // creamos un empleado
        em.persist(emp);
        // creamos un log event
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
