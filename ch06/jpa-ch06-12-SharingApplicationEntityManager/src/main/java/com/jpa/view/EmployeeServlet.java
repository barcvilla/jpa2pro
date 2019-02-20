/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.view;

import com.jpa.entity.Employee;
import com.jpa.service.EmpService;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.Collection;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author PC
 */
@WebServlet(name = "EmployeeServlet", urlPatterns = {"/EmployeeServlet"})
public class EmployeeServlet extends HttpServlet {

    private final String TITLE = "Chapter 6: Sharing Application Managed EntityManagers Example";

    private final String DESCRIPTION = "This example shows a brief example of how to share an applicaton "
            + "managed entity manager between components.";

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
    EntityManager em = emf.createEntityManager();
    @Resource
    UserTransaction tx;

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
            printHtmlHeader(out);
            String action = request.getParameter("action");
            if (action == null) {
                printHtmlFooter(out);
                return;
            }

            //EntityManager em = null;
            try {
                em.getTransaction().begin();
                //em = emf.createEntityManager();
                EmpService service = new EmpService(em);
                // process request
                if (action.equals("Create")) {
                    Employee emp = service.createEmployee(
                            parseInt(request.getParameter("createId")),
                            request.getParameter("name"),
                            parseLong(request.getParameter("salary")));
                    out.println("Created " + emp);
                    em.getTransaction().commit();
                } 
                else if (action.equals("Remove")) {
                    String id = request.getParameter("removeId");
                    service.removeEmployee(parseInt(id));
                    out.println("Removed Employee with id: " + id);
                    em.getTransaction().commit();
                } 
                else if (action.equals("Update")) {
                    String id = request.getParameter("raiseId");
                    Employee emp = service.raiseEmployeeSalary(
                            parseInt(id),
                            parseLong(request.getParameter("raise")));
                    out.println("Updated " + emp);
                    em.getTransaction().commit();
                } 
                else if (action.equals("Find")) {
                    Employee emp = service.findEmployee(
                            parseInt(request.getParameter("findId")));
                    out.println("Found " + emp);
                } 
                else if (action.equals("FindAll")) {
                    Collection<Employee> emps = service.findAllEmployees();
                    if (emps.isEmpty()) {
                        out.println("No Employees found ");
                    } 
                    else {
                        out.println("Found Employees: </br>");
                        for (Employee emp : emps) {
                            out.print(emp + "<br/>");
                        }
                    }
                }
            } catch (Exception e) {
                throw new ServletException(e);
            } finally {
                em.close();
            }

            printHtmlFooter(out);
        }
    }

    private void printHtmlHeader(PrintWriter out) throws IOException {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + TITLE + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("<hr/>");
        out.println("<form action=\"EmployeeServlet\" method=\"POST\">");
        // form to create
        out.println("<h3>Create an Employee</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Id:</td><td><input type=\"text\" name=\"createId\"/>(int)</td></tr>");
        out.println("<tr><td>Name:</td><td><input type=\"text\" name=\"name\"/>(String)</td></tr>");
        out.println("<tr><td>Salary:</td><td><input type=\"text\" name=\"salary\"/>(long)</td>"
                + "<td><input name=\"action\" type=\"submit\" value=\"Create\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
        // form to remove
        out.println("<h3>Remove an Employee</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Id:</td><td><input type=\"text\" name=\"removeId\"/>(int)</td>"
                + "<td><input name=\"action\" type=\"submit\" value=\"Remove\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
        // form to update
        out.println("<h3>Update an Employee</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Id:</td><td><input type=\"text\" name=\"raiseId\"/>(int)</td></tr>");
        out.println("<tr><td>Raise:</td><td><input type=\"text\" name=\"raise\"/>(long)</td>"
                + "<td><input name=\"action\" type=\"submit\" value=\"Update\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
        // form to find
        out.println("<h3>Find an Employee</h3>");
        out.println("<table><tbody>");
        out.println("<tr><td>Id:</td><td><input type=\"text\" name=\"findId\"/>(int)</td>"
                + "<td><input name=\"action\" type=\"submit\" value=\"Find\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<hr/>");
        // form to find all
        out.println("<h3>Find all Employees</h3>");
        out.println("<input name=\"action\" type=\"submit\" value=\"FindAll\"/>");
        out.println("<hr/>");
    }

    private int parseInt(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private long parseLong(String longString) {
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException e) {
            return 0;
        }
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
