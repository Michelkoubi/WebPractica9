function buscar(producto)
{
    document.formulario.producto.value = producto;
    document.formulario.submit();
}

function eliminarCookieBusqueda()
{
    document.cookie="productosBuscados=";
    window.location = "javascript:peticionAJAX('/09.CarritoCompraAJAX/historial/limpiar, 'busquedas')";
}

function validar()
{
    if(document.formulario.producto.value.length == 0)
    {
        document.getElementById("error").innerHTML = "Introduzca un criterio de búsqueda";
        document.formulario.producto.select;
        return false;
    }      
}    

function mostrarFactura()
{
    
}

function peticionAJAX(url, idDiv)
{
    var xmlHttpReq = new XMLHttpRequest();
    xmlHttpReq.onreadystatechange=function()
    {
        if (xmlHttpReq.readyState == 4 && xmlHttpReq.status==200)
            document.getElementById(idDiv).innerHTML = xmlHttpReq.responseText;
    }

    xmlHttpReq.open('GET', url, true); 
    xmlHttpReq.send();

}  
