/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.listing.listing_4_3;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author PC
 */
@Entity(name = "Listing_4_3_Employee")
@Table(name = "EMP", schema = "RRHH") // tambien puede ser catalog
@Access(AccessType.FIELD)
public class Employee implements Serializable{
    private static final long serialVersionUID = -5273308726400755092L;
    
    private static final String LOCAL_AREA_CODE = "613";
    
    private int id;
    @Transient private String phoneNum;
    
    public Employee(){}

    @Id
    @Access(AccessType.PROPERTY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    @Access(AccessType.PROPERTY)
    @Column(name = "PHONE")
    public String getPhoneNumberForDb()
    {
        if(phoneNum.length() == 10)
        {
            return phoneNum;
        }
        else
        {
            return LOCAL_AREA_CODE + "-" + phoneNum;
        }
    }
    
    public void setPhoneNumberForDb(String num)
    {
        if(num.startsWith(LOCAL_AREA_CODE))
        {
            phoneNum = num.substring(3);
        }
        else
        {
            phoneNum = num;
        }
    }
}
