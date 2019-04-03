/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Department;
import com.jpa.model.Employee;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * La entidad Deparment tiene un manager, tiene sentido, extender este facade a Employee entity tambien.
 * DepartmentManager bean retorna un EmployeeManager bean desde el metodo getManager() a fin de representar el manager
 * del departamento. El EmployeeManager ha sido injectado y luego inicializado durante la invocacion del metodo init()
 * 
 * Funcionara o fallara el metodo init(). Segun lo dicho para como si fallaria. Cuando se invoca init() en el bean
 * DepartmentManager, su contexto de persistencia sera propagado con la transaccion. en la subsiguiente llamada al init()
 * en el bean EmployeeManager, este intentara asociar su propio contexto de persistencia extendido con la transaccion,
 * causando colision entre los dos.
 * 
 * Para sorpresa este ejemplo funciona. Cuando un session bean stateful con un contexto de persistencia extendido crea
 * otro session bean stateful que tambien utiliza un contexto de persistencia extendido, el hijo heredara el contexto
 * de persistencia del hijo. el bean EmployeeManager hereda el contexto de persistencia del bean DepartmentManager
 * cuando este es injectado en la instancia DepartmentManager. Los dos beans ahora pueden ser usados juntos en la
 * misma transaccion
 * @author PC
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DepartmentManager {
    @PersistenceContext(unitName = "EmployeeService", type=PersistenceContextType.EXTENDED)
    EntityManager em;
    Department dept;
    
    @EJB
    AuditService audit;
    @EJB
    EmployeeManager manager;
    
    public void init(int deptId)
    {
        dept = em.find(Department.class, deptId);
        manager.inti();
    }
    
    public EmployeeManager getManager()
    {
        return manager;
    }
    
    public Department getDepartment()
    {
        return dept;
    }
        
    public void setName(String name)
    {
        dept.setName(name);
    }
    
    public void addEmployee(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(emp.getId(), "added to department " + dept.getName());
    }
    
    @Remove
    public void finished(){}
}
