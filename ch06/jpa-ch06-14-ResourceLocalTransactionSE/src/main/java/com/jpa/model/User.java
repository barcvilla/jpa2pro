/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "user_db")
public class User implements Serializable{
    @Id
    private String name;
    private String password;
    @Temporal(TemporalType.DATE)
    private Calendar lastChange;
    
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getLastChange() {
        return lastChange;
    }

    public void setLastChange(Calendar lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", password=" + password + ", lastChange=" + lastChange + '}';
    }
    
    
}
