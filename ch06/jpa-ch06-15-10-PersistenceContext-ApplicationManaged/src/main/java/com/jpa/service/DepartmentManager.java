/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.domain.Department;
import com.jpa.domain.Employee;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Como con el contexto de persistencia container-managed, el contexto de persistencia applicaction-managed puede ser
 * sincronizado con transacciones JTA. Sincronizando el contexto de persistencia con la transaccion significa que un
 * emparejamiento ocurrira si se realiza un commit de la transaccion, pero el contexto de persistencia no sera considerado
 * asociado a ningun persistence context container-managed. 
 * No hay limite al numero de persistence context application-managed que puede ser sincronizado con una transaccion, pero
 * un solo contexto de persistencia application-manahed puede ser asociado. esta es una de la mas importantes diferencias
 * entre application-managed y container-managed entity managers
 * 
 * Un entity manager application-context participa en una transaccion JTA en una de dos formas. Si el contexto de persistencia
 * es creado dentro de la transaccion, el proveedor de persistencia automaticamente sincrinizara el contexto de persistencia
 * con la transaccion. si el contexto de persistencia fue creado temprano (fuera d una transaccion o en una transaccion que
 * ya ha terminado) el contexto de persistencia puede ser manualmente sincronizado con la transaccion llamando a
 * joinTransaction() de la interface EntityManager. Una vez sincronizado, el contexto de persistencia automaticamanete sera
 * emparejado con el commit de la transaccion
 * @author PC
 */
@Stateful
public class DepartmentManager {
    @PersistenceUnit(unitName = "EmployeeService")
    private EntityManagerFactory emf;
    private EntityManager em;
    private Department dept;
    
    public void init(int deptId)
    {
        em = emf.createEntityManager();
        dept = em.find(Department.class, deptId);
    }
    
    public Department getDepartment()
    {
        return dept;
    }
    
    public void setName(String name)
    {
        em.joinTransaction();
        dept.setName(name);
    }
    
    public String getName()
    {
        return dept.getName();
    }
    
    public void addEmployee(int empId)
    {
        em.joinTransaction();
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
    }
    
    @Remove
    public void finished()
    {
        em.close();
    }
}
