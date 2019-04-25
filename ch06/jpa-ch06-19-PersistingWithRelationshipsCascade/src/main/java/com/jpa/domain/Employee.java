/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author PC
 */

@Entity
@Table(name = "employee_persisting_with_relationship_cascade")
public class Employee implements Serializable{
    @Id
    private int id;
    private String name;
    
    /**
     * La configuracion de cascade son unidireccional. esto significa que deben ser explicitamente configurado en ambos
     * lados de la relacion si el mismo comportamiento se prentende en ambas situaciones. 
     * Por ejemplo, adicionamos unicamente la configuracion cascade a la relacion de Address con la entidad Employee
     * Si cambianos estas lineas de codigo para persistir unicamente la entidad Address y no la entidad Employee, la entidad
     * Employee no seria managed por el entity manager no ha sido instruido para navegar fuera de la relacion definida
     * en la entiedad Address.
     * Es poco probable que adicionemos operaciones de cascading  desde la entidad Address a la entiedad Employee porque es
     * un hijo de la entidad Employee. 
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    Address address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", address=" + address + '}';
    }
    
}
