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
