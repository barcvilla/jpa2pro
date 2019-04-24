/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Address;
import com.jpa.domain.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Operaciones en Cascada
 * Por defecto, cada operacion entity manager aplica solo a la entidad provista como un argumento a la operacion.
 * la operacion no hara cascada a otra entidad que tiene una relacion con la entidad que esta siendo operada. Para
 * algunas operaciones como remove(), este seria el comportamiento deseado. No quisieramos que el entity manager
 * hiciera una suposicion incorrecta sobre que instancia de entidad deberia ser removida como efecto secundario de la
 * operacion. Pero no lo mismo ocurre con operaciones com persist().
 * Las oportunidades son si tenemos una nueva entidad y tiene una relacion con otra nueva entidad, las dos deben ser
 * persistidas juntas.
 * JPA provee un mecanismo para definir cuando las operaciones como persist() deberian automaticamente aplicar cascade
 * por todas las relaciones. El atributo cascade en todas las relaciones logicas (@OneToOne, @OneToMany, @MayToOne y
 * @ManyToMany) define la lista de operaciones del entity manager a ser cascade.
 * Laas operaciones del Entity Manager son definidas usando el tipo enumerado CascadeType y seguido del atributo cascade
 * Las constantes PERSIST, REFRESH, REMOVE, MERGE y DETACH corresponden a la operacion del entity manager del mismo nombre
 * Por defecto las relaciones tienen el atributo cascade vacio.
 * @author PC
 */
@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "EmployeeService")
    protected EntityManager em;
    
    public Employee createEmployeeAndAddress(int id, String name, int addrId, String street, String city, String state)
    {
        Employee emp = new Employee();
        emp.setId(id);
        emp.setName(name);
        Address addr = new Address();
        addr.setId(addrId);
        addr.setStreet(street);
        addr.setCity(city);
        addr.setState(state);
        emp.setAddress(addr);
        em.persist(emp);
        return emp;
    }
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
}
