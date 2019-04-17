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
 * Usando Resource-Local Transaction en un ambiente Java EE
 * Dentro de las aplicaciones de servidor, el manejo de transacciones JTA es por defecto la opcion en muchas app.
 * Un ejemplo del uso de resource-local Transaction en un ambiente Java EE podria ser para el caso logging. Si su app
 * requiere auditar el log y almacenarlo en una BD.
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
            em.getTransaction().begin(); // iniciamos un nuevo recurso - transaccion local
            em.persist(lr);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

}
