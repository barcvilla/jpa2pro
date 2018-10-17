/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.examples.singleton;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author PC
 */
@Named(value = "hitCounterController")
@RequestScoped
public class HitCounterController {

    private final static Logger logger = Logger.getLogger(HitCounterController.class.getName());
    
    @EJB
    HitCounter hitCounter;
    
    public HitCounterController() {
    }
    
    public void fetchCount()
    {
        //logger.log(Level.INFO, "Counter: ", hitCounter.getCount());
        System.out.println(hitCounter.getCount());
    }
    
    public void incrementCount()
    {
        hitCounter.increment();
    }
    
    public void resetCount()
    {
        hitCounter.reset();
    }
}
