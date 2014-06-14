/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ProductoDAO;
import dominio.Producto;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DyO
 */
public class Comprar 
{
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try 
        {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductoDAO dao = ProductoDAO.getInstance();
            Producto producto = dao.findById(id);
            HttpSession sesion = request.getSession();
            Collection<Producto> carrito = (Collection<Producto>) sesion.getAttribute("carrito");
            if(carrito == null)
            {
               carrito = new ArrayList<Producto>();
               sesion.setAttribute("carrito", carrito);
            }
            
            carrito.add(producto);
        }
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(Comprar.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(Comprar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
