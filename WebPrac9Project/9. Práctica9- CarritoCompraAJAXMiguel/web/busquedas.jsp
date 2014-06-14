<%-- 
    Document   : busquedas
    Created on : 15-mar-2013, 16:24:36
    Author     : labcom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Collection"%>
<%@page import="dominio.Producto"%>
<%@page import="java.util.ArrayList"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

     <h4>Búsquedas recientes:<a href="javascript:eliminarCookieBusqueda();"><img src="../images/eliminar.gif" title="Eliminar búsquedas"/></a></h4>
     <ul>

    <c:forEach var="valor" items="${sessionScope.cookiesSet}">
        <li><a href="javascript:buscar('${valor}');">${valor}</a></li>
    </c:forEach>
   
     </ul>
