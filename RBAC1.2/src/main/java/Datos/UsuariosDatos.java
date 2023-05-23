package Datos;
import Conexion.ConexionSQL;
import Modelo.Rol;
import Modelo.Usuarios;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDatos {
    private Connection getConnection() throws SQLException{
        return ConexionSQL.getInstance();
    }


    public List<Usuarios> listar() throws SQLException {
        List<Usuarios> usuario= new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT u.id, u.Nombre, r.rol FROM usuario AS u INNER JOIN rol AS r ON u.idRol=r.id")) {
            while (rs.next()) {
                Usuarios u = AgregarA_lista(rs);
                usuario.add(u);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
        return usuario;

    }


    public void Agregar(Usuarios usuario) {
        String sql;
        if (usuario.getId()!=null && usuario.getId()>0) {
            sql = "UPDATE usuario SET Nombre=?, Contraseña=?, idRol=? WHERE id=?";
        } else {
            sql = "INSERT INTO usuario(Nombre, Contraseña, idRol ) VALUES(?,?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContraseña());
            stmt.setInt(3,usuario.getIdRol());

            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setInt(4, usuario.getId());
            }

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public Usuarios AgregarA_lista(ResultSet rs) throws SQLException{
        Rol r = new Rol();
        r.setRol(rs.getString("r.rol"));


        Usuarios u= new Usuarios();
        u.setId(rs.getInt("u.id"));
        u.setNombre(rs.getString("u.Nombre"));
        u.setRol(r);
        return u;
    }


    public void EditarUs(Usuarios usuario) {
        String sql="";

        if (usuario.getId()!=null && usuario.getId()>0) {

            sql = "UPDATE usuario SET Nombre=?, idRol=? WHERE id=?";

        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setInt(2, usuario.getIdRol());

            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setInt(3, usuario.getId());
            }
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Datos editados");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void Eliminar(Usuarios usuario) {
        String sql="";
        if (usuario.getId()!=null && usuario.getId()>0) {
            sql = "DELETE FROM usuario WHERE id=?";

        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setInt(1, usuario.getId());
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
