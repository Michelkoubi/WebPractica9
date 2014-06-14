package dao;

import java.sql.*;
import java.util.*;

import dominio.Producto;

public class ProductoDAO
{
    private static String QUERY_TODOS_PRODUCTOS = "SELECT * FROM Producto ORDER BY nombre";
    private static String QUERY_PRODUCTO_BY_ID = "SELECT * FROM Producto WHERE id = ?";
    private static String QUERY_PRODUCTO_BY_NAME = "SELECT * FROM Producto WHERE nombre LIKE ?";

    private static ProductoDAO dao;
    private static Connection c;

    private ProductoDAO() throws ClassNotFoundException, SQLException
    {
	Class.forName("com.mysql.jdbc.Driver");
        String bd = "jdbc:mysql://127.0.0.1/java";
	c = DriverManager.getConnection(bd, "root", "labcom");
    }
    
    public static ProductoDAO getInstance() throws ClassNotFoundException, SQLException
    {
        if(dao == null || !c.isValid(0))
        {
            dao = new ProductoDAO();
            System.out.println("Nueva DAO");
        }        
        return dao;
    }

    public void close() throws SQLException
    {
            c.close();
    }

    public Collection<Producto> findAllProductos() throws SQLException
    {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        PreparedStatement ps = c.prepareStatement(QUERY_TODOS_PRODUCTOS);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            lista.add(new Producto(id, nombre, precio));
        }

        rs.close();
        ps.close();
        return lista;
    }    
    
    public Producto findById(int id) throws SQLException
    {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        PreparedStatement ps = c.prepareStatement(QUERY_PRODUCTO_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Producto p = null;
        
        while (rs.next())
        {
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            p = new Producto(id, nombre, precio);
        }

        rs.close();
        ps.close();
        return p;
    }   

    public Collection<Producto> findProductosByName(String producto) throws SQLException
    {
        ArrayList<Producto> lista = new ArrayList<Producto>();
        PreparedStatement ps = c.prepareStatement(QUERY_PRODUCTO_BY_NAME);
        ps.setString(1, "%" + producto + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next())
        {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            Producto p = new Producto(id, nombre, precio);
            lista.add(p);
        }

        rs.close();
        ps.close();
        return lista;
        
    
    }
}
