/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.view;

import com.jpa.service.LogServiceEE;
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
@WebServlet(name = "LoggingServlet", urlPatterns = {"/LoggingServlet"})
public class LoggingServlet extends HttpServlet {

    private final String TITLE
            = "Chapter 6: Resource-Local Transaction in EE Example";

    private final String DESCRIPTION
            = "This example shows the basic use of an application managed "
            + "EntityManager with ResourceLocal transactions in EE.";

    @EJB
    private LogServiceEE service;

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
            /* TODO output your page here. You may use following sample code. */

            printHeader(out);
            
            // procesamos request
            String action = request.getParameter("id");
            if(action != null)
            {
                int id = parseInt(request.getParameter("id"));
                String access = request.getParameter("access");
                service.logAccess(id, access);
                out.print("Logged id: " + id + " access " + access + "</br>");
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

    private void printHeader(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>" + TITLE + "</title></head>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<body>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("<hr/>");

        out.println("<form action=\"LoggingServlet\" method=\"POST\">");
        out.println("<h3>Log Access</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Id:</td><td><input type=\"text\" name=\"id\"/>(int)</td></tr>");
        out.println("<tr><td>Action:</td><td><input type=\"text\" name=\"access\"/>(String)</td>");
        out.println("<td><input name=\"action\" type=\"submit\" value=\"Log\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
    }

    private void printHtmlFooter(PrintWriter out) throws IOException {
        out.println("</body>");
        out.println("</html>");
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
