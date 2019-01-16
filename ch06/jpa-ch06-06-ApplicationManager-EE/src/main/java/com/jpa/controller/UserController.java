/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.model.User;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author PC
 */
@Named(value = "Users")
@RequestScoped
public class UserController {
    
    //@PersistenceUnit(unitName = "EmployeeService")
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
    
    EntityManager em = emf.createEntityManager();

    public UserController() {
    }
    
    public List<User> getAllUser()
    {
        List<User> users = null;
        try
        {
            users = em.createQuery("select u from User u").getResultList();
            System.out.println(users);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       
        return users;
    }
    
}
