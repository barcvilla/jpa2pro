/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.view;

import com.jpa.domain.Employee;
import com.jpa.service.EmployeeService;
import java.io.IOException;
import java.io.PrintWriter;
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
public class EmployeeServiceServlet extends HttpServlet {

    private final String TITLE
            = "Chapter 6: Persisting Entities with Relationships using Cascade Example";

    private final String DESCRIPTION
            = "This example demonstrates how to persist a simple relationship.</br> "
            + "It allows you to create/find employees and address.";

    // inject una referencia de EmployeeService
    @EJB
    EmployeeService service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            printHtmlHeader(out);
            //procesamos el request
            String action = request.getParameter("action");
            if (action == null) {
                // do nothing if no action requested
            } else if (action.equals("Create")) {
                service.createEmployeeAndAddress(
                        parseInt(request.getParameter("empId")),
                        request.getParameter("name"),
                        parseInt(request.getParameter("addrId")),
                        request.getParameter("street"),
                        request.getParameter("city"),
                        request.getParameter("state"));
            }

            out.println("Employees and Addresses: </br>");
            for (Employee emp : service.findAllEmployees()) {
                out.print(emp + "<br/>");
            }

            printHtmlFooter(out);
        }
    }

    private int parseInt(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void printHtmlHeader(PrintWriter out) throws IOException {
        out.println("<body>");
        out.println("<html>");
        out.println("<head><title>" + TITLE + "</title></head>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("<hr/>");
        out.println("<form action=\"EmployeeServlet\" method=\"POST\">");
        // form to create and Employee and Address
        out.println("<h3>Create an Employee and Address</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Employee Id:</td><td><input type=\"text\" name=\"empId\"/>(int)</td></tr>");
        out.println("<tr><td>Employee Name:</td><td><input type=\"text\" name=\"name\"/>(String)</td></tr>");
        out.println("<tr><td>Address Id:</td><td><input type=\"text\" name=\"addrId\"/>(int)</td></tr>");
        out.println("<tr><td>Address Street:</td><td><input type=\"text\" name=\"street\"/>(String)</td></tr>");
        out.println("<tr><td>Address City:</td><td><input type=\"text\" name=\"city\"/>(String)</td></tr>");
        out.println("<tr><td>Address State:</td><td><input type=\"text\" name=\"state\"/>(String)</td>"
                + "<td><input name=\"action\" type=\"submit\" value=\"Create\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
    }

    private void printHtmlFooter(PrintWriter out) throws IOException {
        out.println("</html>");
        out.println("</body>");
        out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
