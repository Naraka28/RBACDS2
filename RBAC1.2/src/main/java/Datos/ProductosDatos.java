package Datos;
import Conexion.ConexionSQL;
import Modelo.Producto;
import Modelo.Usuarios;
import Vista.Menu;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDatos {
    Menu m;

    private Connection getConnection() throws SQLException {
        return ConexionSQL.getInstance();
    }

    public List<Producto> listar() throws SQLException {
        List<Producto> producto = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.id, p.nombre, p.cantidad, p.precio, u.id, u.nombre, p.fechaIngreso FROM producto AS p INNER JOIN usuario AS u on p.idUser=u.id")) {
            while (rs.next()) {
                Producto p = AgregarA_lista(rs);
                producto.add(p);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return producto;
    }

    public void Agregar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE producto SET nombre=?, cantidad=?, precio=?, idUser=? WHERE id=?";
        } else {
            sql = "INSERT INTO producto(nombre, cantidad, precio, idUser, fechaIngreso) VALUES(?,?,?,?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getCantidad());
            stmt.setInt(3, producto.getPrecio());
            stmt.setInt(4, producto.getidUser());
            stmt.setDate(5, new Date(producto.getFechaIngreso().getTime()));

            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setInt(5, producto.getId());
            } else {
            }
            stmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public Producto AgregarA_lista(ResultSet rs) throws SQLException {

        Usuarios u = new Usuarios();
        u.setId(rs.getInt("u.id"));
        u.setNombre(rs.getString("u.Nombre"));


        Producto p = new Producto();
        p.setId(rs.getInt("p.id"));
        p.setNombre(rs.getString("p.nombre"));
        p.setCantidad(rs.getInt("p.cantidad"));
        p.setPrecio(rs.getInt("p.precio"));
        p.setFechaIngreso(rs.getDate("p.fechaIngreso"));

        return p;
    }


    public void Editar(Producto producto) {
        String sql = "";

        if (producto.getId() != null && producto.getId() > 0) {

            sql = "UPDATE producto SET nombre=?, cantidad=?, precio=? WHERE id=?";

        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getCantidad());
            stmt.setInt(3, producto.getPrecio());


            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setInt(4, producto.getId());
            }
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Datos editados");
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }


    public void Eliminar(Producto producto) {
        String sql = "";
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "DELETE FROM producto WHERE id=?";

        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setInt(1, producto.getId());
            }
            try {
                String y = JOptionPane.showInputDialog("Confirme si desde eliminar el registro:\n ESCRIBA Y / N");
                if (y.equals("Y") || y.equals("y")) {
                    stmt.execute();
                    JOptionPane.showMessageDialog(null, "Datos eliminados");
                } else {
                    JOptionPane.showMessageDialog(null, "Proceso Cancelado");
                }
            } catch (NullPointerException ev) {
                JOptionPane.showMessageDialog(null, "Proceso Cancelado");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

