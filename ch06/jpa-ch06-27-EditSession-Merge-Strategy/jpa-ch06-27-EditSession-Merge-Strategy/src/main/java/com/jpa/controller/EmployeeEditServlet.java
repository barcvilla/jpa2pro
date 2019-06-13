/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.service.EmployeeEdit;
import java.io.IOException;
import javax.naming.InitialContext;
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
@WebServlet(name = "EmployeeEditServlet", urlPatterns = "/EmployeeEditServlet")
public class EmployeeEditServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseInt(req.getParameter("id"));
        EmployeeEdit bean = getBean();
        bean.begin(id);
        HttpSession session = req.getSession();
        session.setAttribute("employee.edit", bean);
        req.setAttribute("employee", bean.getEmployee());
        getServletContext().getRequestDispatcher("/editEmployee.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int parseInt(String intString)
    {
        try
        {
            return Integer.parseInt(intString);
        }
        catch(NumberFormatException e)
        {
            return 0;
        }
    }
    
    public EmployeeEdit getBean() throws ServletException
    {
        try
        {
            return (EmployeeEdit) new InitialContext().lookup("java:comp/env/EmployeeEdit");
        }
        catch(Exception e)
        {
            throw new ServletException(e);
        }
    }
}
