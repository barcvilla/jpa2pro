/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.MyLogRecord;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PC
 */
@Stateless
public class LogService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Logging");
    EntityManager em = emf.createEntityManager();

    public void logAccess(int userId, String action) {
        try {
            MyLogRecord lr = new MyLogRecord(userId, action);
            em.getTransaction().begin();
            em.persist(lr);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

}
