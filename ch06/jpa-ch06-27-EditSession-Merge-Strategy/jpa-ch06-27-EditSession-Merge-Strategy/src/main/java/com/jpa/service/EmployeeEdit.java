/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Employee;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Colocando un Stateful session bean en una ubicacion central como el HTTP Session, podemos operar sobre entidades
 * administradas por el extended entity manager sin tener que merge a fin de persistir cambios. Nos referiremos a este
 * patron Edit Session para reflejar el hecho de que el principal objetivo de este patron es encapsular el caso de uso 
 * de edicion usando stateful session bean
 * EmployeeEdit  es un stateful session bean que representa un employee editing session. a diferencia de EmployeeService
 * session bean que contien un numero de metodos de negocios reusables, este estilo de session bean esta dirigido a
 * a un solo caso de uso app. A fon de utilizar el extended entity manager, tambien hemos configurado el default
 * transaction type a ser NOT_SUPPORTED con la excepcion del metodo save() No hay necesidad de transacciones para metodos
 * que simplemente acceden a la instancia Employee ya qu estos metodos solo operan en memoria, es solo cuando queremos
 * persistir los cambios en la base de datos cuando necesitamos una transaccion y eso solo pasa en el metodo save()
 * 
 * El patron para usar staeful session bean y entity manager extended en la capa web es como sigue:
 * 1. Para cada caso de aplicacion que modifica datos de entidades, creamos un stateful session bean con un contexto
 *    de persistencia extendido. Este bean contendra todas las instancias de las entidades.
 * 2. El Http request que inicia el caso de uso editing crea una instancia del stateful session bean y se coloca en el
 *    Http session. Las entidades son recuperadas en este punto y se usan para poblar el formulario web para la edicion
 * 3. El Http request que completa el caso de uso de editing obtiene el stateful session bean previamente colocado en el
 *    Http session instance y escribe los datos modificados desde el formulario web hacia la entidad almacenada
 *    en el bean. Un metodo es invocado en el bean para commit los cambios en la base de datos.
 * @author PC
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeEdit {
    @PersistenceContext(type = PersistenceContextType.EXTENDED, unitName = "EmployeeService")
    EntityManager em;
    Employee emp;
    
    public void begin(int id)
    {
        emp = em.find(Employee.class, id);
        if(emp == null)
        {
            throw new IllegalArgumentException("Unknown employee id: " + id);
        }
    }
    
    public Employee getEmployee()
    {
        return emp;
    }
    
    @Remove
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save()
    {
        em.merge(emp);
    }
    
    @Remove
    public void cancel(){}
}
