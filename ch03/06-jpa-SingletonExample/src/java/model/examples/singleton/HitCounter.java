/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.examples.singleton;

import javax.ejb.Singleton;

/**
 *
 * @author PC
 */
@Singleton
public class HitCounter {
    private int count;
    
    public void increment()
    {
        ++count;
    }
    
    public int getCount()
    {
        return count;
    }
    
    public void reset()
    {
        count = 0;
    }
}
