/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_12;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapping Temporal Types
 * @author PC
 */
@Entity(name = "Listing_4_12_Employee")
public class Employee implements Serializable{
    @Id
    private int id;
    //TemporalType.DATE for java.util.Calendar and java.util.Date
    @Temporal(TemporalType.DATE)
    private Calendar dob;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "S_DATE")
    private Date startDate;
}
