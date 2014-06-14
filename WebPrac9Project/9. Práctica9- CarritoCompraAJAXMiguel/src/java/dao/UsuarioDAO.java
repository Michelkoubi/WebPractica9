/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dominio.Usuario;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author labcom
 */
public class UsuarioDAO 
{
    private static String QUERY_USER = "SELECT * FROM Usuario WHERE usuario = ? AND contrasena = ?";
    private static UsuarioDAO dao;
    private static Connection c;

    private UsuarioDAO() throws ClassNotFoundException, SQLException
    {
	Class.forName("com.mysql.jdbc.Driver");
        String bd = "jdbc:mysql://127.0.0.1/java";
	c = DriverManager.getConnection(bd, "root", "labcom");
    }
    
    public static UsuarioDAO getInstance() throws ClassNotFoundException, SQLException
    {
        if(dao == null || !c.isValid(0))
        {
            dao = new UsuarioDAO();
            System.out.println("Nueva DAO");
        }
            
        return dao;
    }

    public void close() throws SQLException
    {
            c.close();
    }

    public boolean findByUsuario(Usuario u) throws SQLException
    {
        PreparedStatement ps = c.prepareStatement(QUERY_USER);
        ps.setString(1, u.getNombre());
        ps.setString(2, u.getContrasena());
        ResultSet rs = ps.executeQuery();

        if (rs.next())
            return true;
        else
            return false;

    }

}
