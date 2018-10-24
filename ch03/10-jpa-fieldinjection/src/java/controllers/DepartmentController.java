/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import service.DepartmentService;

/**
 *
 * @author PC
 */
@Named(value = "departmentController")
@RequestScoped
public class DepartmentController {

    @EJB
    DepartmentService departmentService;
    
    public DepartmentController() {
    }
    
    public void printAudit()
    {
        System.out.println(departmentService.performAduit());
    }
}
