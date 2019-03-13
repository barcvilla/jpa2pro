/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.controller;

import com.jpa.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * La mejor forma de crear un application managed entity manager java EE es usar la anotacion @PersistenceUnit
 * para declarar una referencia al EntityManagerFactory para un persistence unit. Una vez adquirido, el factory
 * puede ser usado para crear un entity manager, el cual puede ser usado como si seria un java SE.
 * Una cosa comun en ambos ejemplos (Application Managed Entity Manager SE y Application Managed Entity Manager EE)
 * es que el Entity Manager es cerrado (close()) explicitamente cuando no se llama o necesita mas.
 * Este es uno de los requerimientos en el ciclo de vida del entity manager que debe ser cerrado manualmente.
 * A diferencia del Application Managed Entity Manager container que la tarea de cerrar el entity manager es
 * realizado automaticamente.
 * 
 * @author PC
 */

@WebServlet(name="LoginServlet", 
            urlPatterns="/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final String TITLE = 
        "Chapter 6: Application Managed EntityManager in EE Example";
    
    private final String DESCRIPTION = 
        "This example demonstrates the basic use of an application-managed " +
        "EntityManager in an EE container.";

    @PersistenceUnit(unitName="EmployeeService")
    EntityManagerFactory emf;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printHtmlHeader(out);
        
        String userId = request.getParameter("user");

        // check valid user
        EntityManager em = emf.createEntityManager();
        try {
            if (userId != null) {
                User user = em.find(User.class, userId);
                if (user == null) {
                    // return error page
                    out.println("User with Id: " + userId + " not found!<br> ");
                } else {
                    out.println("Found " + user + "</br>");
                }
            }
            out.println("Users:<br> ");
            printCollection(em.createQuery("SELECT u FROM User u").getResultList(), out);
        } finally {
            em.close();
        }

        printHtmlFooter(out);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void printCollection(Collection c, PrintWriter out) {
        for (Object o : c) {
            out.print(o + "<br/>");
        }
    }
    
    private void printHtmlHeader(PrintWriter out) throws IOException {
        out.println("<body>");
        out.println("<html>");
        out.println("<head><title>" + TITLE + "</title></head>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("<hr/>");
        out.println("<form action=\"LoginServlet\" method=\"POST\">");
        // form to update
        out.println("<h3>Login User</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>User Id:</td><td><input type=\"text\" name=\"user\"/>(String)</td>");
        out.println("<td><input name=\"action\" type=\"submit\" value=\"Login\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
    }
    
    
    private void printHtmlFooter(PrintWriter out) throws IOException {
        out.println("</html>");
        out.println("</body>");
        out.close();
    }
}
