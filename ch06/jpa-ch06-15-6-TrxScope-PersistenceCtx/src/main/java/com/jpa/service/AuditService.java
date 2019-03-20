/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Employee;
import com.jpa.model.LogRecord;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Cuando hablamos de transacciones en JPA existen tres conceptos: Sincronizacion de Transaccion, Asociacion de Transaccion,
 * Propagacion de Transaccion. 
 * 
 * La sincronizacion de Transaccion es el proceso por el cual el contexto de persistencia esta registrado con una transaccion
 * asi, este contexto de persistencia puede ser notificado cuando una transaccion es committed. El proveedor utiliza esta
 * notificacion para asegurarse que un contexto de persistencia dado ha sido impactado en la BD
 * 
 * La asociacion de Transaccion es el acto de unir un contexto de persistencia a una transaccion. Podemos pensar esto como
 * un contexto de persistencia activo dentro del alcance de la transaccion
 * 
 * Propagacion de Transaccio es el proceso de compartir un contexto de persistencia entre multiples entity managers container
 * managed en una sola transaccion. 
 * 
 * Transaction-Scoped Persistence Contexts: esta unido al ciclo de vida del Persistence Context. Es creado por el contenedor
 * durante una transaccion y sera cerrada cuando la transaccion se complete. Transaction-Scoped Entity Manager son los 
 * responsable de la creacion de un Transaction-Scoped Persistence context automaticamente cuando se necesiten. Decimos 
 * cuando se necesite porque la Transaction-Scope Persistence Context es lazy. Un entity manager creara un contexto de
 * persistencia solo cuando un metodo es invocado en el entity manager y cuando no hay contexto de persistencia disponible.
 * 
 * Cuando un metodo es invocado en el transaction-scoped entity manager, debe primero verificar su hay un contexto de 
 * persistencia propagado. Si no lo hay, el entity manager utiliza el contexto de persistencia para llevar a cabo la operacion.
 * Si no existe un contexto de persistencia, el entity manager solicita un nuevo contexto de persistencia al persistence
 * provider y luego marca ese nuevo contexto de persistencia como un contexto de persistencia propagado para la transaccion
 * antes de realizar la llamada al metodo. 
 * 
 * La propagacion del contexto de persistencia simplifica la construccion de aplicaciones empresariales. Cuando una entidad
 * es actualizada por un componente dentro de una transaccion, cualquier referencia subsecuente a la misma entidad 
 * correspondera a la instancia correcta, no importa que componente obtiene la refrencia de la entidad. La propagacion del
 * contexto de persistencia le da a los desarrolladores la libertad de construir aplicaciones loosely coupled, 
 * 
 * Propagacion de un contexto de persistencia  transaction-scope
 * @author PC
 */
@Stateless
public class AuditService {
    @PersistenceContext(unitName = "EmployeeService")
    private EntityManager em;
    
    /**
     * Metodo para asegurarnos que el id del empleado es valido y se intenta encontrar al empleado usando el entity manager
     * 
     * Por defecto el atributo de Transaccion en el metodo logTransaction es TransationAttributeType.REQUIRED. El contenedor
     * garantizara que una transaccion se inicie antes que el metodo sea invocado. Cuando se llama a persist() en el
     * entity manager, el contenedor verifica si un contexto de persistencia esta ya asociado con la transaccion.
     * Asumamos en este caso que este fue la primera operacion del entity manager en la transaccion, asi que el contenedor 
     * crea un nuevo contexto de persistencia y lo marca como uno propagado.
     * 
     * Cuando logTransaction inicia, se realiza una llamada al metodo find() del entity manager
     * @param empId
     * @param action 
     */
    public void logTransaction(int empId, String action)
    {
        if(em.find(Employee.class, empId) == null)
        {
            throw new IllegalArgumentException("Unknown employee id");
        }
        
        LogRecord lr = new LogRecord(empId, action);
        em.persist(lr);
    }
    
    /**
     * Antes que empiece la llamada al metodo logTransaction() el contenedor suspende la transaccion heredada desde el
     * metodo createEmployee() en el bean EmployeeService e iniciar una nueva transaccion. Cuando el metodo find() es 
     * invocado en el entity manager , el entity manager verificara la transaccion actual en busqueda de un contexto
     * de persistencia activo solo para determinar que no existe. Un nuevo contexto de persistencia sera creado al
     * llamar al metodo find() y este contexto de persistencia sera el contexto de persistencia activo para la
     * llamada logTransaction() porque la transaccion iniciada en el metodo createEmployee() del bean EmployeeService
     * aun no se ha hecho committed, y la nueva instancia creada del empleado no esta en la BD y por lo tanto no es visible
     * al nuevo contexto de persistencia. El metodo find() retornara null y el metodo logTransaction() lanzara una 
     * excepcion.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void logTransaction2(int empId, String action)
    {
        logTransaction(empId, action);
    }
}
