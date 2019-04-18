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
 * El mantenimiento de las relaciones entre entidades es responsabilidad de la aplicacion. Casi todo problema relacionado
 * con remover una entidad siempre tiene que ver con el mantenimiento de las relaciones entre entidades. Si la entidad a 
 * ser removida es el objetivo de la llave foranea en otras tablas, esta llavea foraneas debens er limpiadas para una
 * eliminacion exitosa.
 * Una entidad solo puede ser removida si es administrada por un contexto de persistencia. Esto significa que Entity Manager
 * Transaction-Scoped puede ser usado para remover una entidad solo si existe una transaccion activa. Invoca al metodo 
 * remove() cuando no hay una transaccion produce una excepcion TrasactionRequiredExecption. 
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
    
    /**
     * Cuando la transaccion realiza un commit, vemos la sentencia delete para la tabla ParkingSpace, pero luego
     * obtenemos una excepcion ya que se viola la restriccion foreign key ya que existen una integridad referencial entre
     * employee y parking space. Para corregir el problema debemos explicitamente debemo colocar en nulo el atributo
     * parkingSpace de la entidad employee (como se demuestra en el metodo removeParkingSpace()) 
     * antes que la transaccion haga commit.
     */
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
