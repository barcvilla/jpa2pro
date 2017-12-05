/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpapro.ch02.ex1.client;

import java.util.Collection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;

import jpapro.ch02.ex1.model.EmployeeService;
import jpapro.ch02.ex1.model.Employee;

/**
 *
 * @author barcvilla
 */
public class EmployeeTest 
{
    public static void main(String[] args) 
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
        EntityManager em = emf.createEntityManager();
        EmployeeService service = new EmployeeService(em);
        
        // creamos y almacenamos  un empleado
        em.getTransaction().begin();
        Employee emp = service.createEmployee(158, "John Doe", 45000);
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
        emp = service.raiseEmployeeSalary(158, 1000);
        em.getTransaction().commit();
        System.out.println("Empleado actualizado: " + emp);
        
        em.close();
        emf.close();
    }
}
