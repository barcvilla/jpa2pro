/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.stateless;

import javax.ejb.Stateless;

/**
 *
 * @author barcvilla
 */
@Stateless
public class HelloServiceBean implements HelloService{
    @Override
    public String sayHello(String name)
    {
        return "Hello " + name;
    }
}
