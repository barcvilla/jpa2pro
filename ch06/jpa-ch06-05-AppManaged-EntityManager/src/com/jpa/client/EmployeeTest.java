/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.client;

import java.util.Collection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;

import com.jpa.model.EmployeeService;
import com.jpa.model.Employee;

/**
 * Ejemplo de un Entity Manager Application-Managed, este nombre viene del hecho de que la aplicacion, en lugar del
 * contenedor maneja el ciclo de vida del entity manager. Note que todos los entities manager abiertos, ya sea
 * container-manager o application-managed estan asociadas con la instancia EntityManagerFactory. El factory usado
 * para crear el entity manager puede ser accedido desde la llamada al metodo getEntityManagerFactory() de la 
 * interface EntityManager.
 * 
 * Aunque se espera que la mayoria de aplicaciones sean desarrolladas utilizando Entity Manager container-managed, pero
 * entity manager application manager aun tiene un rol que jugar. 
 * @author barcvilla
 */
public class EmployeeTest 
{
    public static void main(String[] args) 
    {
        // en el entorno Java SE usamos la clase Persistence
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
        EntityManager em = emf.createEntityManager(); // devolvemos un EntityManager
        
        // creamos la unidad de persistencia EmployeeService
        EmployeeService service = new EmployeeService(em);
        
        // creamos y almacenamos  un empleado
        em.getTransaction().begin(); // transaccion aplicable en entorno SE
        Employee emp = service.createEmployee(159, "Paul Doe", 50000);
        em.getTransaction().commit();
        System.out.println("Almacenado " + emp );
        
        // find empleado especifico
        emp = service.findEmployee(158);
        System.out.println("Empleado encontrado: " + emp);
        
        // encontramos todos los empleados
        Collection<Employee> emps = service.findAllEmployee();
        for(Employee e : emps)
        {
            System.out.println("Empleado encontrado: " + e);
        }
        
        // actualizamos un empleado
        em.getTransaction().begin();
        emp = service.raiseEmployeeSalary(159, 1000);
        em.getTransaction().commit();
        System.out.println("Empleado actualizado: " + emp);
        
        em.close();
        emf.close();
    }
}
