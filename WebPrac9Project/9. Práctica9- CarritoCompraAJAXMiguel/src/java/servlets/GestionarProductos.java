/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import dao.ProductoDAO;
import dominio.Producto;
import java.io.IOException;
import java.sql.SQLException;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GestionarProductos extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession sesion = request.getSession();
        if(sesion.getAttribute("user")!=null)
        {
            try 
            {
                String accion = request.getServletPath();
                String vista = "/listadoProductos.jsp"; //Por defecto la vista es toda la página listadoProductos.jsp
                
                if(accion.startsWith("/Comprar"))
                {
                    new Comprar().execute(request, response);
                    vista = "/carrito.jsp"; //Si entra por comprar, la vista es carrito.jsp porque solo actualizamos ese div
                }
                
                /*if(accion.startsWith("/Comprar"))
                {
                    new Eliminar().execute(request, response);
                    vista = "/busquedas.jsp"; //Si entra por comprar, la vista es carrito.jsp porque solo actualizamos ese div
                }*/
                
                if(request.getParameter("clearBusqueda")!=null)
                {
                    Collection<String> cookiesOrdenadas = (Collection<String>)sesion.getAttribute("cookiesSet");
                    cookiesOrdenadas.clear(); 
                }
                    
                String producto = request.getParameter("producto");
                //MVC: Lógica de negocio y forward a la vista
                ProductoDAO dao = ProductoDAO.getInstance();
                Collection<Producto> lista = null;
                if(producto==null)
                {
                    //Función de Caché
                    if(this.getServletContext().getAttribute("productos")==null)
                    {
                        lista = dao.findAllProductos();
                        this.getServletContext().setAttribute("productos", lista);
                    }
                    else
                    {
                        lista = (Collection<Producto>) this.getServletContext().getAttribute("productos");
                    }
                }
                else
                {
                    lista = dao.findProductosByName(producto);
                    
                    Collection<String> cookiesOrdenadas = (Collection<String>)sesion.getAttribute("cookiesSet");
                    cookiesOrdenadas.add(producto.toLowerCase());
                    
                    StringBuffer cookieSB = new StringBuffer();
                    for(String busqueda:cookiesOrdenadas)
                        cookieSB.append(busqueda + ":");
                    
                    cookieSB.deleteCharAt(cookieSB.length()-1);
                                
                    Cookie cookieBusqueda = new Cookie("productosBuscados", cookieSB.toString());
                    
                    //Ejemplo de cookie no crítica manipulable por Javascript. Así la podré eliminar en local sin acceder al server.
                    cookieBusqueda.setHttpOnly(false);
                    cookieBusqueda.setMaxAge(Integer.MAX_VALUE);
                    response.addCookie(cookieBusqueda);
                    
                    request.setAttribute("filtro", true);
                }
                
                request.setAttribute("productos", lista);
                RequestDispatcher rd = request.getRequestDispatcher(vista);
                rd.forward(request, response);
            }
            catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(GestionarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(GestionarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {

            response.sendRedirect("index.html");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
