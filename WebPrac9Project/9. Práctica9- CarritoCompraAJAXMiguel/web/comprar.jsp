<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Collection"%>
<%@page import="dominio.Producto"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Comprar productos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/09.CarritoCompraAJAX/css/compra.css" type="text/css" rel="stylesheet" />
    </head>
    <body>
    <div class="fondoFactura">
        <div class="contenidoFactura">

            <h1>Factura</h1>
            <h3>Listado de productos comprados</h1>
            <ul>
                <c:forEach var="productoCarrito" items="${sessionScope.carrito}">
                    <li>${productoCarrito.nombre}.............${productoCarrito.precio} €</li>            
                    <c:set var="total"  value="${total + productoCarrito.precio}"></c:set>
                </c:forEach>
             </ul>
             <hr />
             <strong>Total a pagar: ${total} € </strong>      
       </div>
     </div>         
    </body>
</html>
