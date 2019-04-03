/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Department;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import com.jpa.model.Employee;
import javax.ejb.Remove;

/**
 * Persistence Context Collision
 * Dijimos al inicio que solo un contexto de persistencia podria ser propagado con una transaccion JTA. Tambien dijimos que
 * el contexto de persistencia extendido siempre intentaria ser el mismo el contexto de persistencia activo. Esto puede
 * traer dos situaciones en la cual dos contextos de persistencia colisionen uno con otro. Consideremos el siguiente ejemplo:
 * Un session bean Stateless con un entity manager transaction-scoped crea un nuevo contexto de persistencia y luego invoca
 * un metodo de un session bean Stateful el cual tiene un contexto de persistencia extendido. Durante la asociacion del 
 * contexto de persistencia extendido el contenedor verificara si ya existe un contexto de persistencia activo. Si lo hay,
 * debe ser el mismo contexto de persistencia extendido que se trata de asociar, o una excepcion sera lanzada. en este
 * ejemplo, session bean stateful encontrara el contexto de persistencia transaction-scoped ya creado por el session bean
 * stateless y la llamada al metodo del session bean stateful fallara. Puede solo existir  un contexto de persistencia activo
 * para una transaccion.
 * 
 * Mientras que la propagacion del contexto de persistencia extendido es util si un session bean stateful con un contexto
 * de persistencia extendido es el primer EJB a ser invocado en una cadena de llamado. esto limita la situacion en la cual
 * otros componentes pueden hacer llamadas al session bean stateful si ellos tambien usan entity managers. Esto podria o
 * no ser comun dependiendo de la arquitectura de la app, pero algo a tener en mente cuando planificamos las dependencias de
 * componentes. 
 * 
 * Una forma de trabajar con este problema es cambiar el atributo de transaccion por defecto para el session bean stateful
 * que utiliza el contxto de persistencia extendido. Si el atributo de transaccion por defecto es REQUIRED_NEW, cualquier
 * transaccion activa sera suspendida antes que el metodo del session bean stateful inicie, permitiendo asociar
 * su contexto de persistencia extendido con la nueva transaccion. Esta es una buena estrategia si el session bean 
 * stateful llama a otro session bean stateless y necesita ser propagado el contexto de persistencia. Note que el uso
 * excesivo de REQUIRES_NEW transaction puede ocasionar problemas de desempeno ya que muchas transacciones de lo normal
 * seran creadas, y transaccion activa sera suspendido y resumido.
 * 
 * Si session bean stateful no llama a otro session bean y no necesita que se propague si contexto de persistencia, un
 * tipo de atributo de transaccion de NOT_SUPPORTED puede ser valioso. en este caso, cualquier transaccion activa sera
 * suspendida antes que la llamada al metodo del sessio bean stateful inicie, pero no sera iniciada una nueva transaccion.
 * 
 * En este ejemplo usamos el atributo de transaccion REQUIRES_NEW para forzar una nueva transaccion por defecto cuando
 * un metodo del negocio es invocado. Para el metodo getName() no necesitamos una nueva transaccion porque ningun cambio
 * es realizado, por ello NOT_SUPPORTED. Esto suspendera la actual transaccion, pero no resultara en una nueva transaccion
 * creada. Con estos cambios, el bean DepartmentManager puede ser accedido en cualquier situacion, inluso si hay
 * un contexto de persistencia activo
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DepartmentManager {
    @PersistenceContext(unitName = "EmployeeService", type = PersistenceContextType.EXTENDED)
    EntityManager em;
    
    Department dept;
    @EJB
    AuditService audit;
    
    public void init(int deptId)
    {
        dept = em.find(Department.class, deptId);
    }
    
    public Department getDepartment()
    {
        return dept;
    }
    
    public void setName(String name)
    {
        dept.setName(name);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getName()
    {
        return dept.getName();
    }
    
    /**
     * 
     */
    public void addEmployee(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(emp.getId(), "added to department__ " + dept.getName());
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addEmployee2(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(empId, "added to department " + dept.getName());
    }
    
    @Remove
    public void finished(){}
}
