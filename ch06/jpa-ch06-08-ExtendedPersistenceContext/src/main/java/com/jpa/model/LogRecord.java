/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "logrecord_extendedpersistencecntx")
public class LogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int empId;
    private String action;
    
    public LogRecord(){}
    
    public LogRecord(int empId, String action)
    {
        setEmpId(empId);
        setAction(action);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "LogRecord{" + "id=" + id + ", empId=" + empId + ", action=" + action + '}';
    }
    
    
}
