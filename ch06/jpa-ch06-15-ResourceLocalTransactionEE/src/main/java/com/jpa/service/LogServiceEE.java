/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.LogRecord;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class LogServiceEE {
    @PersistenceContext(unitName = "Logging")
    EntityManager em;
    
    public void logAccess(int userId, String action)
    {
           LogRecord lr = new LogRecord(userId, action);
           em.persist(lr);
        
    }
}
