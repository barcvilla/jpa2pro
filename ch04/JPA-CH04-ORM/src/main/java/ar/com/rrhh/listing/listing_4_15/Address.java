/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_15;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 *
 * @author PC
 */
@Entity(name = "Listing_4_15_Address")
public class Address implements Serializable{
    @TableGenerator(name = "Address_Gen",
            table = "ID_GEN",
            pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "Addr_Gen",
            initialValue = 10000,
            allocationSize = 100)
    @Id
    private long id;
}
