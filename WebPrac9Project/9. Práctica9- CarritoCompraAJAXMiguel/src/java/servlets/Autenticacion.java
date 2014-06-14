/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.UsuarioDAO;
import dominio.Usuario;
import java.io.IOException;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

/**
 *
 * @author labcom
 */
public class Autenticacion extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {

        String nombre = request.getParameter("user");
        String contrasena = request.getParameter("password");
        
        Usuario u = new Usuario(nombre, contrasena);
        
        try
        {
            UsuarioDAO dao = UsuarioDAO.getInstance();
            boolean existe = dao.findByUsuario(u);

            if(existe)
            {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("user", nombre);
                
                Cookie cookies[] = request.getCookies();
                TreeSet<String> cookiesOrdenadas = new TreeSet<String>();
                boolean sw = false;
                String valorCookie = null;
                if(cookies!=null)
                {
                    for(Cookie c:cookies)
                    {
                        if(c.getName().equals("productosBuscados"))
                        {
                            valorCookie = c.getValue();
                            String valores[] = valorCookie.split(":");
                            sw = true;
                            for(String valor:valores)
                            {  
                                cookiesOrdenadas.add(valor.toLowerCase());
                                
                            }    
                        }
                    }   
                }
                
                if(valorCookie != null && cookiesOrdenadas.isEmpty())
                    cookiesOrdenadas.add(valorCookie);
                
                sesion.setAttribute("cookiesSet", cookiesOrdenadas);
                response.sendRedirect("productos/listar");
            }
            else
            {
                response.sendRedirect("error.html");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();;
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
