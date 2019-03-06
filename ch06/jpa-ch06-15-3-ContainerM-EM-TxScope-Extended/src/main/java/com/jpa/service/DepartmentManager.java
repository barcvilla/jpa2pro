/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Department;
import com.jpa.model.Employee;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Statefull session bean estan disenados para contener estados conversacional. Una vez adquirido por el cliente la misma
 * instancia bean es usado para la vida de la conversacion hasta que el cliente invoca uno de los metodos marcados con
 * @Removed en el bean. 
 * 
 * Usamos statefull session bean para ayudar a manejar un departamento. El objetivo es crear un objeto de negocio para
 * la entidad Department que ofrece operaciones de negocio relacionados con la entidad. 
 * 
 * @PersistenceContext tiene el atributo type que puede ser PersistenceContextType.TRANSACTION o 
 * PersistenceContextType.EXTENDED .
 * 
 * PersistenceContextType.TRANSACTION es el valor por defecto y corresponde a Entity Manager - Transaction Scope. 
 * PersistenceContextType.EXTENDED significa que un entity manager EXTENDIDO deberia ser utilizado.
 * 
 * Con este cambio realizado, department managed bean ahora funciona como se espera. Entity manager extended crea un
 * contexto de persistencia con una instancia del un stateful session bean es creado hasta que el bean es removido. A
 * diferencia de el contexto de perstencia de un entity manager transaction-scoped, la cual empieza cuando la transaccion
 * empieza y termina hasta el final de la transaccion, el contexto de persistencia de un entity managed extended terminara
 * con toda la longitud de la conversacion. Porque la entidad Department es aun manajeda por por el mismo contexto de 
 * persistencia ya sea que es usado en una transaccion cualquier cambio sera impactadi en la BD
 * 
 * @author PC
 */
@Stateful
public class DepartmentManager {
    @PersistenceContext(unitName="EmployeeService", type = PersistenceContextType.EXTENDED)
    EntityManager em;
    Department dept;
    
    //es llamado por el cliente para inicializar el department id. Almacenamos el id en una bean instance
    public void init(int deptId) {
        this.dept = em.find(Department.class, deptId);
    }
    
    public Department getDepartment() {
        return dept;
    }

    public void setName(String name) {
        //Department dept = em.find(Department.class, this.dept);
        dept.setName(name);
    }
    
    /**
     * Utilizo para encontrar el departamento y realizar los camibios necesarios. Desde la perspectiva del cliente, ellos
     * solo tienen que establecer el department id una sola vez y las siguiente operaciones siempre hacen referencia al
     * mismo department.
     */
    public void addEmployee(int empId)
    {
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
    }
    
    @Remove
    public void finished() {
    }
}
