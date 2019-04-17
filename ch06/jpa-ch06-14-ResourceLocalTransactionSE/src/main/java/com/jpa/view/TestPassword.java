/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.view;

import com.jpa.model.User;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

/**
 * Aplicacion que utiliza EntityTransaction Api para realizar un cambio de password para los usuarios quienes fallaron
 * actualizar su password antes que la fecha expire.
 * @author PC
 */
public class TestPassword {
    public static void main(String[] args) {
        int maxAge = 36;
        String defaultPassword = "password";
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin");
        try
        {
            EntityManager em = emf.createEntityManager();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -maxAge);
            
            em.getTransaction().begin(); //iniciamos una nueva resource transaction
            List<User> expired = em.createQuery("select u from User u where u.lastChange <= ?1", User.class)
                    .setParameter(1, cal, TemporalType.DATE)
                    .getResultList();
            for(User u : expired)
            {
                System.out.println("Expired password for: " + u.getName());
            }
            em.getTransaction().commit();
            em.close();
        }
        finally
        {
            emf.close();
        }
    }
}
