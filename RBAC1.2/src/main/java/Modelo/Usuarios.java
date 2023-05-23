package Modelo;

public class Usuarios {
    private Integer id;
    private String nombre;
    private String contraseña;
    private Integer idRol;
    private Rol rol;
    private String Roll;

    public Usuarios() {
    }

    public Usuarios(String nombre, String contraseña, Rol rol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public Usuarios(Integer id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.Roll = rol;
    }



    public Usuarios(Integer id) {
        this.id = id;
    }

    public Usuarios(Integer id, String nombre, Integer idRol) {
        this.id = id;
        this.nombre = nombre;
        this.idRol = idRol;
    }

    public Usuarios(String nombre, String contraseña, Integer idRol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.idRol = idRol;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return  nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}
