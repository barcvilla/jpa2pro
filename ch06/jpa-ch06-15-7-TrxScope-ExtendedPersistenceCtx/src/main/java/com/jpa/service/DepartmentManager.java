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
 * El ciclo de vida de un contexto de persistencia extendido esta unido al stateful session bean. A diferencia de un 
 * entity manager transaccion-scoped que crea un nuevo contexto de persistencia para cada transaccion. El entity manager
 * extened de un stateful sessin bean siempre usa el mismo contexto de persistencia. El stateful session bean esta asociado
 * con un solo persistence context extendido que es creado cuando la instancia del bean es creado y cerrado cuando la 
 * instancia del bean ha sido removido. 
 * 
 * La asociacion de transaccion para un contexto de persistencia extendido es veloz. En el caso de una transaccion container-
 * managed es tan pronto como la llamada al metodo del bean se inicie. el contenedor automaticamente asocia el contexto
 * de persistencia con la transaccion. Como en el caso del transaction bean-managed tan pronto como UserTransaction.begin()
 * es invocado dentro de un metodo bean el contenedor intercepta la llamada y realiza la misma asociacion.
 * 
 * 
 * @author PC
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
    
    public void addEmployee(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
        audit.logTransaction(emp.getId(), "added to department__ " + dept.getName());
    }
    
    @Remove
    public void finished(){}
}
