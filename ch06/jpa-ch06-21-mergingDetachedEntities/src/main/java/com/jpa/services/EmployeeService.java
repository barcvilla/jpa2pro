/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.services;

import com.jpa.domain.Employee;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * La operacion merge() es utilizada para unir el estado de una entidad separada en un contexto de persistencia.
 * el metodo es sencillo de usar solo se requiere una instancia de una entiedad separada como argumento. Hay
 * algunas sutilizas al usar merge()
 */
@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "EmployeeService")
    EntityManager em;
    
    /**
     * Correcta implementacion de merge()
     * Un diferente manejo de entidad (ya sea una nueva instancia o una version managed existente presente en el contexto
     * de persistencia) es actualizada y luego retornada por el metodo merge(). Por tanto, para capturar este cambio,
     * necesitamos usar el valor de retorno desde merge() ya que es una entidad administrada.
     * 
     * Retornar una instancia administrada otra de la entidad original es critico en lap parte del proceso merge. Si una
     * instancia de una entiedad con el mismo identificador ya existe en el contexto de persistencia, el proveedor
     * sobreescribira su estado conel estado de la entiedad que esta siendo fusionado, pero la version managed que existe
     * debe ser retornado al cliente para ser usado. 
     */
    public void updateEmployee(Employee emp)
    {
        if(em.find(Employee.class, emp.getId()) == null)
        {
            throw new IllegalArgumentException("Unknow employee id: " + emp.getId());
        }
        Employee managedEmp = em.merge(emp);
        managedEmp.setLastAccessTime(new Date());
    }
    
    /**
     * asumiendo que una transaccion empieza y termina con este metodo, cualquier cambio hecho sobre la instancia Employee
     * mientras esta estaba separada sera escrita en la BD. Que no sera escrito?, por otro lado, es el cambio al ultimo
     * accessTime. el argumento merge() no llega a ser administrado como un resultado del merge. 
     */
    public void updateEmployeeIncorrect(Employee emp)
    {
        if(em.find(Employee.class, emp.getId()) == null)
        {
            throw new IllegalArgumentException("Unknown employee id: " + emp.getId());
        }
        em.merge(emp);
        emp.setLastAccessTime(new Date());
    }
    
    public List<Employee> findAllEmployees()
    {
        return em.createQuery("Select e from Employee e", Employee.class).getResultList();
    }
}
