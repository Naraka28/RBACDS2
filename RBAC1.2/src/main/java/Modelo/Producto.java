package Modelo;

import java.sql.Date;
public class Producto {
    private Integer id;
    private String nombre;
    private int cantidad;
    private int precio;
    private int idUser;

    private Date fechaIngreso;

    public Producto() {
    }

    public Producto(String nombre, int cantidad, int precio, int registrador,Date fechaIngreso) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idUser = registrador;
        this.fechaIngreso =fechaIngreso;


    }
    public Producto(Integer id,String nombre, int cantidad, int precio) {
        this.id=id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;


    }
    public Producto(String nombre, int cantidad, int precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;



    }

    public Producto(Integer id, String nombre, int cantidad, int precio, int idUser, Date fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idUser = idUser;
        this.fechaIngreso = fechaIngreso;
    }

    public Producto(Integer id) {
        this.id= id;



    }

    @Override
    public String toString() {
        return
                "\t|" + id +
                "\t|" + nombre  +
                "\t|"+ cantidad +
                "\t|" + precio +
                "\t|" + idUser +
                "\t|" + fechaIngreso +
                "\n";
    }

    public Integer getId() {
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getidUser() {
        return idUser;
    }

   public Integer setidUser(Usuarios idUser) {

       return idUser.getId();
   }


    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
