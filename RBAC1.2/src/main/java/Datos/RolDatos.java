package Datos;

import Conexion.ConexionSQL;
import Modelo.Rol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RolDatos{
    private Connection getConnection() throws SQLException {
        return ConexionSQL.getInstance();

    }


    public List<Rol> listar() {
        List<Rol> roles= new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT rol FROM rol")) {
            while (rs.next()) {
                Rol r= AgregarA_listaRol(rs);
                roles.add(r);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }


    public Rol AgregarA_listaRol(ResultSet rs) throws SQLException{
        Rol r= new Rol();
        r.setRol(rs.getString("rol"));
        return r;
    }

}
