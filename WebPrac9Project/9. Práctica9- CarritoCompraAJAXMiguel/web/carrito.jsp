<!--Este jsp es para actualizar solo el div del carrito por si entramos por comprar, eliminar producto o vaciar carrito-->
<!--Borramos las etiquetas html, title, bldy porque esta página va a estar dentro de otra, la cual ya contiene las etiquetas-->
<!--Tasmpoco copiamos <div id="carrito" class="carrito" style="padding-top: 10px;"> ya que la págnia en la que se va a mostar lo contiene-->
<!--Nos quitamos el div de busquedas recientes porque ese div se va a actualizar por otras vías no por la de comprar, borrar o vaciar-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Collection"%>
<%@page import="dominio.Producto"%>
<%@page import="java.util.ArrayList"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
 
<h3 style="display:inline">Lista de la compra</h3>
<span style="text-align: center">
    <a href="/09.CarritoCompraAJAX/FinalizarCompra"><img src="../images/pagar.jpg" title="Finalizar compra" width="25px" height="20px"/></a> - 
    <a href="/09.CarritoCompraAJAX/VaciarCarrito"><img src="../images/vaciar.jpg" title="Vaciar lista" width="25px" height="20px"/></a>
</span> 

<ul>
    <c:set var="total" value="0"></c:set>
    <c:if test="${not empty sessionScope.carrito}">
        <c:forEach var="productoCarrito" items="${sessionScope.carrito}">
            <li>${productoCarrito.nombre} = ${productoCarrito.precio} €   <a href="/09.CarritoCompraAJAX/eliminar/${productoCarrito.id}"><img src="../images/eliminar.jpg" title="Eliminar producto" width="25px" height="20px"/></a></li>            
            <c:set var="total"  value="${total + productoCarrito.precio}"></c:set>
        </c:forEach>
    </c:if>                   
</ul>

<hr />

<strong>Total: ${total} €</strong>
<c:if test="${empty sessionScope.carrito}">
    Vacío
</c:if>
