/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.domain.Employee;
import com.jpa.service.EmployeeEdit;
import com.jpa.service.EmployeeService;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Miremos la segunda mitad del editing session, en la cual deseamos commit los cambios, cuando el usuario submit los 
 * el formulario que contiene los cambios necesarios en el Employee, el EmployeeUpdateServlet es invocado. Este empieza
 * recuperando el EmployeeEdit del HTTP Session. Los parametros request con los valores modificados son luego copiados
 * a una instancia employee obtenido de la llamada al metodo getEmployee() en el bean EmployeeEdit, si todo esta en orden
 * el metodo save() es invocado para escribir los cambios en la BD. 
 * @author PC
 */
@WebServlet(name = "EmployeeUpdateServlet", urlPatterns = "/EmployeeUpdateServlet")
@EJB(name = "EmployeeEdit", beanInterface = EmployeeEdit.class)
public class EmployeeUpdateServlet extends HttpServlet{
    @EJB
    EmployeeService empService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EmployeeEdit bean = (EmployeeEdit)session.getAttribute("employee.edit");
        
        String action = request.getParameter("action");
        if(action.equals("Save"))
        {
            String name = request.getParameter("name");
            long salary = parseLong(request.getParameter("salary"));
            Employee emp = bean.getEmployee();
            emp.setName(name);
            emp.setSalary(salary);
            bean.save();
        }
        else if(action.equals("Cancel"))
        {
            bean.cancel();
        }
        //removemos el bean del http session una vez que el editing session ha sido completado
        session.removeAttribute("employee.edit");
        
        request.setAttribute("employee", empService.findAll());
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    private long parseLong(String longString)
    {
        try
        {
            return Long.parseLong(longString);
        }
        catch(NumberFormatException e)
        {
            return 0;
        }
    }
}
