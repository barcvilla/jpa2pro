/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.servlet;

import examples.stateless.HelloService;
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
 * @author barcvilla
 */
@WebServlet(name="HelloServiceServlet", urlPatterns = "/HelloServiceServlet")
public class HelloServiceServlet extends HttpServlet
{
    private final String TITLE = "Chapter 3 - Stateless Session Bean Example";
    private final String DESCRIPTION = 
            "This example demostrates the basics of defining and accessing " + 
            "a Stateless Session Bean. </br>" + 
            "Enter a name and click 'Go'. This will trigger a servlet client that talks " + 
            "to a Stateless Bean to create a 'hello' String that is the " + 
            "displayed in the browser";
    @EJB
    HelloService service;
    
    public void  doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printHtmlHeader(out);
        
        //si se ha sometido un nombre, print hello string
        String name = request.getParameter("name");
        if(name != null)
        {
            // usamos el servicio para imprimir el string hello al stream html
            out.println(service.sayHello(name));
        }
        printHtmlFooter(out);
    }
    
    private void printHtmlHeader(PrintWriter out)throws IOException
    {
        out.println("<!DOCTYPE html>");
        out.println("<head><title>" + TITLE + "</title></head>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("</hr>");
        out.println("form action=\"HelloServiceServlet\" method=\"POST\">");
        out.println("<table><tbody>");
        out.println("<tr><td>Name:</td><td><input type=\"text\" name=\"name\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<input name=\"action\" type=\"submit\" value=\"Go\"/>");
        out.println("</form>");
        out.println("<hr/>");
    }
    
    private void printHtmlFooter(PrintWriter out)throws IOException
    {
        out.println("</body>");
        out.println("</html>");
    }
}
