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
 * Ya que un entity manager transaction-scope utilizara un contexto de persistencia existente asociado con la transaccion
 * antes que se creara un nuevo contexto de persistencia, es posible compartir un persistence contexted extended con otro
 * entity manager transaction-scope. Mientras que el contexto de persistencia extendido sea propagado antes de que cualquier
 * entity manager transaction-scope sea accedido, el mismo contexto de persistencia extendido sera compartido por todos los
 * componentes.
 * 
 * Similar al ejemplo 15-6 auditoria EmployeeService bean, consideremos el mismo cambio hecho al stateful session bean
 * DepartmentManager para auditar cuando el empleado es adicionado al department.
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
    
    /**
     * El metodo addEmployee() utiliza el atributo de transaccion por defecto REQUIRED, ya que el contenedor rapidamente
     * asocia el contexto de persistencia extendido, el contexto de persistencia extendido almacenado en el session bean
     * sera inmediatamente asociado con la transaccion cuando la llamada al metodo se inicie. Esto causara que la relacion
     * entre las entidades managed Department y Employee sean persistido en la base de datos cuando la transaccion commits.
     * Esto tambien significa que el contexto de persistencia extendido ahora sera compartido por otro contexto de persistencia
     * transaction-scoped usado en la llamada de metodos desde addEmployee()
     * 
     * logTransaction() en este ejemplo heredara el transaction-context del addEmployee() ya que su atributo de transaccion es
     * el default REQUIRED, y una transaccion esta activa durante la llamada a addEmployee(). cuando el metodo find() es 
     * invocado, el entity manager transaction-scoped verifica que exista un contexto de persistencia activo y encontrara
     * el contexto de persistencia del DepartmentManager. Este, utilizara este contexto de persistencia para ejecutar la
     * operacion. Todas las entidades manejadas del contxto de persistencia extendido llegan a ser visibles para el
     * entity manager transaction-scoped
     */
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
