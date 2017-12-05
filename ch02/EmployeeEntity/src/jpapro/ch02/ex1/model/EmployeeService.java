/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpapro.ch02.ex1.model;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author barcvilla
 */
public class EmployeeService 
{
    protected EntityManager em;
    
    public EmployeeService(EntityManager em)
    {
        this.em = em;
    }
    
    public Employee createEmployee(int id, String name, long salary)
    {
        Employee emp = new Employee(id);
        emp.setName(name);
        emp.setSalary(salary);
        em.persist(emp);
        return emp;
    }
    
    public void removeEmployee(int id)
    {
        Employee emp = findEmployee(id);
        if(emp != null)
        {
            em.remove(emp);
        }
    }
    
    public Employee raiseEmployeeSalary(int id, long raise)
    {
        Employee emp = em.find(Employee.class, id);
        if(emp != null)
        {
            emp.setSalary(emp.getSalary() + raise);
        }
        return emp;
    }
    
    public Employee findEmployee(int id)
    {
        return em.find(Employee.class, id);
    }
    
    public Collection<Employee> findAllEmployee()
    {
        TypedQuery query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }
    
}
