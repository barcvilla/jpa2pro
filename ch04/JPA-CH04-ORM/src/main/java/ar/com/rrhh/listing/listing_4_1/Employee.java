/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_1;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Listing Using Filed Access
 * @author PC
 */
@Entity(name = "Listing_4_1_Employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = -5279408726400732092L;
    
    @Id
    private int id;
    private String name;
    private BigDecimal salary;
    
    public Employee()
    {}

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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    
}
