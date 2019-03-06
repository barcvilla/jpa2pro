/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.view;

import com.jpa.model.Employee;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Llamamos a este ejemplo Entity Manager Application Managed ya que es la aplicacion y no el contenedor quien maneja
 * el ciclo de vida de las entidades. 
 * Este es el unico tipo de entity manager disponible en Java SE.
 * Lo que separa Entity Manager Application Manager y Entity Manager Container Managed es como se crea el entity manager.
 * @author PC
 */
public class EmployeeClient {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
        EntityManager em = emf.createEntityManager();
        
        Collection emps = em.createQuery("SELECT e FROM Employee e").getResultList();
        for(Iterator i = emps.iterator(); i.hasNext();)
        {
            Employee e = (Employee)i.next();
            System.out.println("Employee: " + e.getId() + " " + e.getName());
        }
        em.clear();
        emf.close();
    }
}
