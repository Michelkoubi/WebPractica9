<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Collection"%>
<%@page import="dominio.Producto"%>
<%@page import="java.util.ArrayList"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />   
<script src="/09.CarritoCompraAJAX/js/scripts.js" language="Javascript">
</script>
<title>Supermercados Argüelles</title>
<c:if test="${empty cookie.tema}" >
    <c:set var="tema" value="${initParam.temaDefecto}"></c:set>
</c:if>
<c:if test="${not empty cookie.tema}" >
    <c:set var="tema" value="${cookie.tema.value}"></c:set>
</c:if>
<link href="/09.CarritoCompraAJAX/css/estilo${tema}.css" type="text/css" rel="stylesheet" />

</head>
<body>
   
 <div style="width:100%;height:100px;clear:both;border:1px solid black">
        <h1 style="text-align: center">Supermercados Argüelles</h1>
        <div style="float:right;width:400px">
            Hola, ${sessionScope.user} [<a href="/09.CarritoCompraAJAX/Desconectar">desconectar</a>] -  Elige tu tema: <a href="/09.CarritoCompraAJAX/CambiarColorTema?tema=azul">Azul</a> - <a href="/09.CarritoCompraAJAX/CambiarColorTema?tema=rojo">Rojo</a>
        </div>
 </div>
 <div class="productos">
  <h1 style="display:inline">Listado de Productos</h1>
  <c:if test="${not empty filtro}" >
        <span><a href="/09.CarritoCompraAJAX/productos/listar"><img src="../images/refresh.jpg"  width="20px" height="20px" title="Actualizar Lista"/></a></span>
  </c:if>
    <form action="/09.CarritoCompraAJAX/productos/listar" method="POST" name="formulario" onsubmit="javascript:return validar();">
          <input type="text" name="producto" />
          <input type="submit" value="buscar" /> <span id="error" style="color:currentColor; "></span>
     </form>
     <ul>
     <c:forEach var="p" items="${productos}">
       <!--Llamamos a la función peticionAJAX a la cual le pasamos la URL y el DIV donde queramos actualizar (el id del DIV-->
       <li>${p.nombre} (${p.precio} €)   <a href="javascript:peticionAJAX('/09.CarritoCompraAJAX/comprar/${p.id}', 'carrito');"><img src="../images/comprar.jpg" title="Comprar producto" width="25px" height="20px"/></a></li>
    </c:forEach>
    </ul>
 </div>
    
 <div class="panelLateral" >  
     <div id="carrito" class="carrito" style="padding-top: 10px;">
         <%@include file="carrito.jsp" %> <!--Con esto llamamos al jsp de carrito desde el jsp de listadoProductos-->
     </div> 
        <div style="heigth:100px;overflow:auto;">
         <h4>Búsquedas recientes:<a href="javascript:eliminarCookieBusqueda();"><img src="../images/eliminar.gif" title="Eliminar búsquedas"/></a></h4>
         <ul>

        <c:forEach var="valor" items="${sessionScope.cookiesSet}">
            <li><a href="javascript:buscar('${valor}');">${valor}</a></li>
        </c:forEach>

         </ul>
     </div>
         
 </div>

</body>
</html>