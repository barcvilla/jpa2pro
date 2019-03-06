/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.service;

import com.jpa.model.Employee;
import com.jpa.model.Project;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Un Entity Manager Transaction Scope es retornado si la referencia creada por el @PersitenceContext es resuelto.
 * Entity Manager Transaction Scope es stateless, lo que significa que puede ser guardado con seguridad sobre cualquier
 * componente Java EE, porque el contenedor lo maneja por nosotros.
 * @author PC
 */

@Stateless
public class ProjectService {
    @PersistenceContext(unitName="EmployeeService")
    EntityManager em;
    
    /**
     * Empezamos buscando un project y un employee, luego el contenedor verifica por una transaccion. Por defecto
     * el contenedor se asegurara que haya una transaccion activa siempre que un metodo de un session bean inicie,
     * asi, el entity manager en este ejemplo, encontrara una lista. luego se verifica el contexto de persistencia. Esta
     * es la primera vez que ha ocurrido una llamada al entity manager asi que no hay un contexto de persistencia
     * El entity manager crea un nuevo contexto de persistencia y lo usa para encontrar el proyecto.
     * 
     * Cuando el Entity manager es usado para encontrar un employee, verifica la transaccion nuevamente y en esta
     * ocasion encuentra uno que se creo cuando se busco el project. Y el entity manager reutiliza el contexto de
     * persistencia para buscar el empleado. 
     * En este punto project y employee ambas son instancias de entidades administradas. El employee es adicionado
     * al project, actualizando tanto a la entidad employee y project. Cuando la llamada al metodo finaliza, la 
     * transaccion es commited ya que la instancia employee y project fueron manajeadas, el contexto de persistencia
     * puede detectar cualquier cambio de estado en ellas y actualizarlos durante el commit a la BD. Cuando la transaccion
     * termina, el contextto de persistencia se va.
     * Este proceso se repite cada vez o mas operaciones entity manager son invocadas dentro de una transaccion.
     * 
     * Cuando el 
     * @param empId
     * @param projectId 
     */
    public void assignEmployeeToProject(int empId, int projectId) {
        Project project = em.find(Project.class, projectId);
        Employee employee = em.find(Employee.class, empId);
        project.getEmployees().add(employee);
        employee.getProjects().add(project);
    }

    public List<Project> findAllProjects() {
        return em.createQuery("SELECT p FROM Project p", Project.class)
                 .getResultList();
    }
}
