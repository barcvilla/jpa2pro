/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_11;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 *
 * @author PC
 */
@Entity(name = "Listing_4_10_Employee")
public class Employee implements Serializable{
    @Id
    private int id;
    
    @Enumerated(EnumType.STRING)
    private EmployeeCategory type;
}
