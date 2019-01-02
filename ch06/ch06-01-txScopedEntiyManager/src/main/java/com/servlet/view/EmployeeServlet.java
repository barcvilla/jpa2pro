/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.view;

import com.ejb.beans.EmployeeService;
import com.ejb.beans.ProjectService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
@WebServlet(name = "EmployeeServlet", urlPatterns = {"/EmployeeServlet"})
public class EmployeeServlet extends HttpServlet {

    private final String TITLE = "Chapter 6: Transaction-scoped Container Managed EntityManager Example";
    private final String DESCRIPTION = "This example demonstrates the basic use of a tx-scoped EntityManager. </br>"
            + "The example allows you to assign employees to projects.";

    @EJB
    private EmployeeService empService;
    @EJB
    private ProjectService projService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void printHtmlHeader(PrintWriter out)
            throws IOException {
        out.println("<body>");
        out.println("<html>");
        out.println("<head><title>" + TITLE + "</title></head>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("<hr/>");
        out.println("<form action=\"EmployeeServlet\" method=\"POST\">");
        // form to update
        out.println("<h3>Assign Employee to Project</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Employee Id:</td><td><input type=\"text\" name=\"empId\"/>(int)</td></tr>");
        out.println("<tr><td>Project Id:</td><td><input type=\"text\" name=\"projId\"/>(int)</td></tr>"
                + "<td><input name=\"action\" type=\"submit\" value=\"AssignProject\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
    }

    private void printHtmlFooter(PrintWriter out) throws IOException {
        out.println("</html>");
        out.println("</body>");
        out.close();
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
    
    private void printCollection(Collection c, PrintWriter out)
    {
        for(Object o : c)
        {
            out.print(o + "<br/>");
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printHtmlHeader(out);
        
        // Procesamos el request
        String action = request.getParameter("action");
        if(action != null)
        {
            if(action.equals("AssignProject"))
            {
                projService.assignEmployeeToProject(parseInt(request.getParameter("empId")), parseInt(request.getParameter("projId")));
            }
        }
        out.println("Employees: <br/>");
        printCollection(empService.findAllEmployees(), out);
        out.println("Projects: <br/>");
        printCollection(projService.findAllProjects(), out);
        printHtmlFooter(out);
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
