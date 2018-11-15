/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_10;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author PC
 */
@Entity(name = "Listing_4_10_Employee")
public class Employee implements Serializable{
    @Id
    private int id;
    /**
     * Declarando un atributo de tipo Enum
     * El campo type sera mapeado a un columna de tipo entero TYPE, 
     */
    private EmployeeType type;
}
