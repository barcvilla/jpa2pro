/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_8;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

/**
 * Lazy Field Loading
 * @author PC
 */
@Entity(name = "Listing_4_8_Employee")
public class Employee implements Serializable{
    private static final long serialVersionUID = -5273308726400662092L;
    
    @Id
    private int id;
    private String name;
    private BigDecimal salary;
    
    /**
     * FetchType.LAZY : Significa que el proveedor puede demorar la carga del estado para este atributo hasta cuando este
     * sea referenciado. Por defecto la carga de todos los mapeo @Basic son EARGER.
     * Configurando el campo comments con FetchType.LAZY permitimos que la instancia retornada de Employee desde una consulta
     * tenga en campo comments vacion.
     */
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "COMM")
    private String comments;
}
