/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_9;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author PC
 */
@Entity(name = "Listing_4_9_Employee")
public class Employee implements Serializable{
    private static final long serialVersionUID = -5273308726400662092L;
    
    @Id
    private int id;
    private String name;
    private BigDecimal salary;
    
    /**
     * Las columnas BLOB en java equivale a byte[], Byte[], Serializable
     * Las columnas CLOB en java equivale a char[], Character[], String
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "PIC")
    private byte[] picture;
}
