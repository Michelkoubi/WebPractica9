/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author labcom
 */
public class Usuario 
{
    private String nombre;
    private String contrasena;
    
    public Usuario(String nombre, String contrasena) 
    {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }
    
    

    
}
