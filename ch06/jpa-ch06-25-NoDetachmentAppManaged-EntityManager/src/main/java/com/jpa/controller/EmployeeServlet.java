/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.domain.Employee;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Para app que no encapsulan sus queries detras de un session bean facade una alternatica al patron Transaction View
 * visto en el ejerciocio jpa-ch06-24 es crear un entity manager application-managed para ejecutar consultas de reporte,
 * cerrandolo solo despues de que la pagina JSP ha sido renderizada. Debido a que la entidad retornada de la consulta
 * del entity manager application-managed permanecera managed hasta que el entity manager es cerrado, esto ofrece los mismos
 * beneficios como el patron Transaction View sin requerir de una transaccion activa.
 * 
 * Lamentablemente, ahora tenemos la logica del query incrustado en la implementacion del servlet. La consulta tampoco
 * puede ser reutilziada como cuando la onsulta era parte del stateless session bean. Hay un par de opciones que podemos
 * explorar como solucion a este problema. En lugar de ejecutar el query directamente, podriamos crear una clase POJO de
 * servicio para ser usado por el entity manager application-manager creado por el servlet para ejecutar el query.
 * 
 * Una segunda opcion la mostramos en el ejemplo jpa-ch06-26-SFSBAndExtendedEM
 * @author PC
 */
@WebServlet(name = "EmployeeServlet", urlPatterns = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet{
    @PersistenceUnit(name = "EmployeeService")
    EntityManagerFactory emf;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            List emps = em.createQuery("select e from Employee e", Employee.class).getResultList();
            request.setAttribute("employees", emps);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
        finally
        {
            em.close();
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        doPost(request, response);
    }
}
