/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.model;

/**
 *
 * @author barcvilla
 */
public class Order {
    private int id;
    private String name;
    private int paymentId;

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

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public String toString() {
        return "Order id: " + getId() + " name: " + getName() + " paymentId: " + getPaymentId();
    }

}
