/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Solo existen dos escenarios donde CascadeType.REMOVE tiene sentido ser utilizado: OneToOne y OneToMany relationships
 * en las cuales hay una clara relacion padre-hijo, esta no puede ser ciegamente aplicado a las relaciones OneToOne y
 * OneToMany ya que la entidad objetivo podria tambien estar participando en otra relacion o podria tener sentido como
 * entiedad stand-alone. Los cuidados deben ser tomados cuando usamos la opcion de cascade REMOVE
 * 
 * Si una entidad Employee es removida (lo cual es una situacion poco comun) podria tener sentido utilizar la operacion
 * Cascade.REMOVE para ambas entidades ParkiSpace y Phone relacionados con Employee. Estos son ambos casos en los cuales
 * Employee es el padre de las entidades objetivo, lo que significa que no estan referenciadas por otras entidades en el
 * sistema.
 * @author PC
 */
@Entity
@Table(name = "employee_removing_with_relationship")
public class Employee implements Serializable{
    @Id
    private int id;
    private String name;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "pspace_id")
    ParkingSpace parkingSpace;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "employee")
    List<Phone> phones;
    
    public Employee()
    {
        phones = new ArrayList<Phone>();
    }

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

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + getId() + ", name=" + getName() + ", parkingSpace=" + getParkingSpace() +'}';
    }
    
}
