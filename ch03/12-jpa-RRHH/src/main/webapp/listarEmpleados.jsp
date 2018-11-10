<%-- 
    Document   : listarEmpleados
    Created on : 31/10/2018, 12:29:27
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Empleados</title>
    </head>
    <body>
        <h1>Lista de Empleados Activos</h1>
        <a href="agregarEmpleado.jsp">Adicionar Empleado</a>
        <br/><br/>
        <table border="1">
            <tr>
                <th>Nombre</th>
                <th>Apellido Paterno</th>
                <th>Apellido Materno</th>
                <th>Email</th>
                <th>Telefono</th>
                <th>Modificar</th>
                <th>Eliminar</th>
            </tr>
            <c:forEach var="employee" items="${employees}" >
                <tr>
                    <td>${employee.nombre}</td>
                    <td>${employee.apellidoPaterno}</td>
                    <td>${employee.apellidoMaterno}</td>
                    <td>${employee.email}</td>
                    <td>${employee.telefono}</td>
                    <td><a href="">Modificar</a></td>
                    <td><a href="">Eliminar</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
