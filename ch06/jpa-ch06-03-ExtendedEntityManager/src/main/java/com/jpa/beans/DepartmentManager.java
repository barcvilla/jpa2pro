/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.beans;

import com.jpa.model.Department;
import com.jpa.model.Employee;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * La unica diferencia entre este ejemplo y el proyecto jpa-ch06-02-ExtendedEntityManager es la linea
 * type = PersistenceContextType.EXTENDED esto significa un entity manager extendido sera utilizado.
 * Entity Manager Extendido crea un contexto de persistencia cuando una instancia stateful session bean
 * is creado hasta que el bean es removido. A diferencia del contexto de persistencia de un entity manager con un 
 * Transaction scope, el cual, empieza cuando la transaccion inicia y durara al terminar la transaccion. El contexto
 * de persistencia de un entity manager extendido durara durante toda la longitud de la conversacion. Ya que la 
 * entidad Department es aun managed por el mismo contexto de persistencia, ya sea que este sea usado en una
 * transaccion cualquier cambio sera automaticamente escrito en la BD
 * 
 * El contexto de persistencia extendido permite a los stateful session bean ser escrito en una forma que es mas
 * adecuada a sus capacidades. 
 * @author PC
 */
@Stateful
public class DepartmentManager {
    @PersistenceContext(unitName = "EmployeeService", type = PersistenceContextType.EXTENDED)
    EntityManager em;
    
    int deptId;
    
    public void init(int deptId)
    {
        this.deptId = deptId;
    }
    
    public Department getDepartment()
    {
        return em.find(Department.class, deptId);
    }
    
    public void setName(String name)
    {
        Department dept = em.find(Department.class, deptId);
        dept.setName(name);
    }
    
    public void addEmployee(int empId)
    {
        Department dept = em.find(Department.class, deptId);
        Employee emp = em.find(Employee.class, empId);
        dept.getEmployees().add(emp);
        emp.setDepartment(dept);
    }
    
    /*
    @Remove
    public void finished(){}
    */
}
