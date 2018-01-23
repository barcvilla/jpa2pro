/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.stateless;

import javax.ejb.Stateless;

/**
 * Clase que implementa la interface comercial HelloService. La anotacion @Stateless marca esta clase como un bean
 * de session sin estado. Esta es una clase regular que simplemente resulta ser un EJB 
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
