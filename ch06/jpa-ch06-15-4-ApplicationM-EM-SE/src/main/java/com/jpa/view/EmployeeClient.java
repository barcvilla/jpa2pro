/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.view;

import com.jpa.model.Employee;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Llamamos a este ejemplo Entity Manager Application Managed ya que es la aplicacion y no el contenedor quien maneja
 * el ciclo de vida de las entidades. 
 * Este es el unico tipo de entity manager disponible en Java SE.
 * Lo que separa Entity Manager Application Manager y Entity Manager Container Managed es como se crea el entity manager.
 * La clase Persistence ofrece 2 variaciones del mismo metodo createEntityManager() puede ser usado para crear
 * una instancia del EntityManagerFactory para una unidad de persistencia. Primero, especificar solo el nombre de la
 * unidad de persistencia, retorna el factory con las propiedades por defecto definidad en el archivo persistence.xml
 * La segunda forma de la llamada al metodo permite mapear propiedades para ser pasados,adicionar o sobreescribir las
 * propiedades especificadas en el persistence.xml Esta forma es muy util cuando se requiere propiedades JDBC que no se
 * pueden conocer hasta que la aplicacion sea instalada, 
 * @author PC
 */
public class EmployeeClient {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
        EntityManager em = emf.createEntityManager();
        
        List<Employee> emps = em.createQuery("SELECT e FROM Employee e").getResultList();
        for(Employee e : emps)
        {
            System.out.println("employee data: " + e.getId() + " " + e.getName());
        }
        /**
        for(Iterator i = emps.iterator(); i.hasNext();)
        {
            Employee e = (Employee)i.next();
            System.out.println("Employee: " + e.getId() + " " + e.getName());
        }
        * **/
        em.close();
        emf.close();
    }
}
