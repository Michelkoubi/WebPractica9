/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author davidcb
 */
public class Producto 
{
    private int id;
    private String nombre;
    private double precio;
    
    public Producto(int id, String nombre, double precio) 
    {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }    

    @Override
    public boolean equals(Object o) 
    {
        Producto p = (Producto) o;
        if (id == p.getId())
            return true;
        else
            return false;
    }    

    @Override
    public int hashCode() 
    {
        return id;
    }    
}
