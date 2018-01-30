/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.servlet;

import examples.stateless.LoggerBean;
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
@WebServlet
(
        name = "LoggerServlet",
        urlPatterns = "/LoggerServlet"
)
public class LoggerServlet extends HttpServlet
{
    private static final long serialVersionUID = 987655432L;
    
    private final String TITLE = "Chapter 3: Stateless Session Bean Lifecycle Example";
    private final String DESCRIPTION =
        "This example demonstrates the basic use of lifecycle callbacks to initialize a Stateless Session Bean. </br>" +
        "Enter a and click 'Go'.  This will trigger a servlet client that talks " +
        "to a Stateless Session Bean to log a message.";
    
    @EJB
    LoggerBean logger;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printHtmlHeader(out);
        
        // si hay un mensaje submitted, loggearlo
        String message = request.getParameter("message");
        if(message != null)
        {
            // usamos el bean logger para hacer un log del mensaje
            logger.logMessage(message);
            out.println("Message '" + message + "' sent to logger bean " + 
            "See the output on the server console or the log file at &lt;SERVER_ROOT&gt;/glassfish/domains/domain1/logs/server.log.");
        }
        printHtmlFooter(out);
    }
    
    private void printHtmlHeader(PrintWriter out) throws IOException
    {
        out.println("<!DOCTYPE html");
        out.println("<html>");
        out.println("<body>");
        out.println("<head><title>" + TITLE + "</title></head>");
        out.println("<center><h1>" + TITLE + "</h1></center>");
        out.println("<p>" + DESCRIPTION + "</p>");
        out.println("</hr>");
        out.println("<form action=\"LoggerServlet\" method=\"GET\">");
        out.println("<table><tbody>");
        out.println("<tr><td>Message:</td><td><input type=\"text\" name=\"message\"/></td></tr>");
        out.println("</tbody></table>");
        out.println("<input name=\"action\" type=\"submit\" value=\"Go\"/>");
        out.println("</form>");
        out.println("<hr/>");        
    }
    
    private void printHtmlFooter(PrintWriter out) throws IOException
    {
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
