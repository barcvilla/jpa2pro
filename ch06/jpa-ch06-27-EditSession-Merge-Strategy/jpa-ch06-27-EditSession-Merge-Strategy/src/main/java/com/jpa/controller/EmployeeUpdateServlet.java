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
 *
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
