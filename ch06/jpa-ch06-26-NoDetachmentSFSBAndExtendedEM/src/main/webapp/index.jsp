<%-- 
    Document   : index
    Created on : 05/06/2019, 11:11:00
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Employees</title>
    </head>
    <body>
        <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Department</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${employees}" var="emp">
          <tr>
            <td><c:out value="${emp.name}"/></td>
            <td><c:out value="${emp.department.name}"/></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    </body>
</html>