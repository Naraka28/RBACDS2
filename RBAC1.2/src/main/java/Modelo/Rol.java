package Modelo;

public class Rol {
    private int id;
    private String rol;//nombre

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", rol='" + rol;
    }
}
