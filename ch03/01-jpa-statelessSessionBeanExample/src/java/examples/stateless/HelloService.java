/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.stateless;

/**
 * Business Interface soportada por el Session Bean. Cuando se implementa por el session bean sera tratado
 * automaticamente como una interface de negocios local, lo que significa que solo es accesible para los clientes
 * dentro del mismo servidor de aplicaciones
 * @author barcvilla
 */
public interface HelloService {
    // el service contiene un solo metodo.
    public String  sayHello(String name);
}
