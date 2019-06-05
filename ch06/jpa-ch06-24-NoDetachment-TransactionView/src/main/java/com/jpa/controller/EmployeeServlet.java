/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.service.EmployeeService;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 * El contxto de persistencia creado por un entity manager transacction scope permanece abierto solo mientras la
 * transaccion en la cual fue creada no ha terminado. por tanto, a fin de usar un entity manager transaction scope
 * para ejecutar un query y ser capaz de renderizar  mientras resuelve la relacion lasy-loading ambas operaciones
 * deben ser de la misma transaccion. 
 * Cuando una transaccion es iniciada en la capa web e incluye ambos, session bean invocacion y jsp rendering antes de
 * del commit, llamamos a este patrin Transaction View
 * @author PC
 */
@WebServlet(name = "EmployeeServlet", urlPatterns = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet{
    @Resource
    UserTransaction tx;
    @EJB
    private EmployeeService bean;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        try
        {
            tx.begin();
            List emps = bean.findAll();
            request.setAttribute("employees", emps);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
        catch(Exception ex)
        {
            throw new ServletException(ex);
        }
        finally
        {
            try
            {
                tx.commit();
            }
            catch(Exception ex)
            {
                throw new ServletException(ex);
            }
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
