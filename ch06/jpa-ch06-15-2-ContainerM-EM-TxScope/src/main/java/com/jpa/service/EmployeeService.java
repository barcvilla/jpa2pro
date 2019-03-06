/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Employee;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * En una ambiente Java EE la forma mas comun de obtener un entity manager es usando @PersistenceContext. Un entity
 * Manager obtenido de esta forma se denomina Container Managed porque el contenedor administra el ciclo de vida 
 * del entity manager
 * Container Managed - Entity Managers viene en dos variedades. El estilo de un Container Managed - Entity Managers
 * determina como funciona con el contexto de persistencia. El primero es el mas comun llamado: 
 * Transaction-Scoped: Esto significa que el contexto de persistencia manajado por el entity manager estan alcanzados
 * por la transaccion JTA activa terminando cuando la transaccion es completada. El segundo se llama:
 * Transaction-Scoped Extended: Extended entity manager funciona con un solo contexto de persistencia que esta unido al
 * ciclo de vida de un stateful session bean y estan alcanzados a la vida del stateful session bean.
 * @author PC
 */

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName="EmployeeService")
    EntityManager em;

    public List<Employee> findAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class)
                 .getResultList();
    }
}
