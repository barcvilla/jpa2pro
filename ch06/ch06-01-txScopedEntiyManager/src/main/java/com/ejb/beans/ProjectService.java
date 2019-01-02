/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb.beans;

import com.jpa.model.Employee;
import com.jpa.model.Project;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class ProjectService {
    @PersistenceContext(unitName = "EmployeeService")
    EntityManager em;
    
    /**
     * 1. Buscamos una instancia de Employee y Project
     * 2. Cuando el primero metodo find() es invocado, el contenedor verifica si hay una transaccion por defecto, el contenedor
     *    se asegurara que una transaccion este activa si un session bean metodo inicia, asi el entity manager en este ejemplo
     *    encontrara una lista. Este luego, verifica por un contexto de persistencia. Esta es la primera vez que una llamada al 
     *    entity manager ha ocurrido, asi que aun no hay un persistence context. El entity manager crea uno nuevo y lo utiliza
     *    para encontrar el proyecto.
     * 3. Cuando el entity manager es usado para buscar un empleado, este verifica la transaccion nuevamente y esta vez encuentra
     *    una creada cuando se busco el project. Este entonces, reutiliza el contexto de persistencia para buscar el empleado.
     *    Em este punto, empleado y proyecto ambos son entity managed.
     * 4. El empleado es luego adicionado al proyecto, actualizando las entidades employee y project. Cuando la llamda al metodo
     *    termina, la transaccion es committed. Ya que employee y project fueron administradas. el contexto de persistencia puede
     *    detectar cualquier estado de cambio en ellos, y este actualiza la base de datos durante el commit. Cuando la transaccion
     *    termina, el contexto de persistencia se va, este proceso se repite cada vez que una o mas operaciones entity manager
     *    son invocados dentro de una transaccion
     * @param empId
     * @param projectId 
     */
    public void assignEmployeeToProject(int empId, int projectId)
    {
        Project project = em.find(Project.class, projectId);
        Employee employee = em.find(Employee.class, empId);
        project.getEmployees().add(employee);
        employee.getProjects().add(project);
    }
    
    public List<Project> findAllProjects()
    {
        return em.createQuery("select p from Project p", Project.class).getResultList();
    }
}
