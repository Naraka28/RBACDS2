package Vista;
import Datos.ProductosDatos;
import Datos.UsuariosDatos;
import Modelo.Producto;
import Modelo.Usuarios;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class Pantalla extends JFrame implements ActionListener{


    LogIn plog;
    Menu m;
    ProductosDatos pd;
    UsuariosDatos ud;
    Integer id,idUs;


    public Pantalla() {
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(new BorderLayout());

        setTitle("Til Inc");

        plog = new LogIn();
        add(plog, BorderLayout.CENTER);
        plog.btnLog.addActionListener(this);
        plog.btnExit.addActionListener(this);

        try {
            m = new Menu();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        m.piAgregar.addActionListener(this);

        m.uiAdd.addActionListener(this);
        m.btSalir.addActionListener(this);
        m.btAgregarPro.addActionListener(this);
        m.btEliminarPro.addActionListener(this);
        m.btBuscarPro.addActionListener(this);
        m.btEditarPro.addActionListener(this);
        m.btEliminarUs.addActionListener(this);
        m.btEditarUs.addActionListener(this);
        m.btAgregarUs.addActionListener(this);
        m.btBuscarUs.addActionListener(this);
        setVisible(true);
        setResizable(false);

        m.tablaProd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UsuariosDatos ud= new UsuariosDatos();
                super.mouseClicked(e);
                int i=m.tablaProd.getSelectedRow();
                TableModel model=m.tablaProd.getModel();
                id= (Integer) model.getValueAt(i,0);

                m.txNombrePro.setText(model.getValueAt(i,1).toString());
                m.txCantidad.setText(model.getValueAt(i,2).toString());
                m.txPrecio.setText(model.getValueAt(i,3).toString());
                m.txRegistrador.setText(model.getValueAt(i,4).toString());
                m.txRegistrador.setEnabled(false);
            }
        });
        m.tablaUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UsuariosDatos ud= new UsuariosDatos();
                super.mouseClicked(e);
                int i=m.tablaUser.getSelectedRow();
                TableModel model=m.tablaUser.getModel();
                idUs= (Integer) model.getValueAt(i,0);
                m.txNombre.setText(model.getValueAt(i,1).toString());
                String s=model.getValueAt(i,2).toString();

                if (s.equals("1")){
                    s="1: Admin";
                }
                if (s.equals("2")){
                    s="2: Empleado";
                }
                if (s.equals("3")){
                    s="3: gerente";
                }
                if (s.equals("4")){
                    s="4: RRHH";
                }
                m.txRol.setSelectedItem(s);
                m.pswd.setEnabled(false);
                m.Cpswd.setEnabled(false);


            }
        });


    }
    public ArrayList<Producto> getProductoList() {
        ArrayList<Producto> prodList = new ArrayList<Producto>();
        pd= new ProductosDatos();
        String sql = "SELECT p.id, p.nombre, p.cantidad, p.precio, p.idUser, p.fechaIngreso FROM producto AS p INNER JOIN usuario AS u on p.idUser=u.id";
        try {
            m.stmt = m.con.createStatement();
            m.rs = m.stmt.executeQuery(sql);
            Producto prod;
            Calendar calendario = Calendar.getInstance();
            java.util.Date fechaActual = calendario.getTime();
            // Convertir la fecha actual a formato SQL
            Date fechaSQL = new Date(fechaActual.getTime());
            while(m.rs.next())

            {
                prod = new Producto(m.rs.getInt("id"),m.rs.getString("nombre"),m.rs.getInt("cantidad"),m.rs.getInt("precio"), m.rs.getInt("p.idUser"), m.rs.getDate("fechaIngreso"));
                prodList.add(prod);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return prodList;
    }

    public void refreshProd(){
        ArrayList<Producto> list = getProductoList();

        DefaultTableModel modelo= (DefaultTableModel) m.tablaProd.getModel();
        Object[] fila= new Object[6];
        for (int i = 0; i < list.size() ; i++) {
            fila[0]=list.get(i).getId();
            fila[1]=list.get(i).getNombre();
            fila[2]=list.get(i).getCantidad();
            fila[3]=list.get(i).getPrecio();
            fila[4]=list.get(i).getidUser();
            fila[5]=list.get(i).getFechaIngreso();
            modelo.addRow(fila);
        }
    }

    public void actualizar(){
        DefaultTableModel modelo= (DefaultTableModel) m.tablaProd.getModel();
        modelo.setRowCount(0);
        refreshProd();
    }



    public ArrayList<Usuarios> getUsuariosList() {
        ArrayList<Usuarios> usuariosList = new ArrayList<Usuarios>();
        ud=new UsuariosDatos();
        String sql = "SELECT id, Nombre, idRol FROM usuario";
        try {
            m.stmt = m.con.createStatement();
            m.rs = m.stmt.executeQuery(sql);
            Usuarios user;
             while(m.rs.next()) {
                user=new Usuarios(m.rs.getInt("id"), m.rs.getString("Nombre" ),m.rs.getInt("idRol"));
                usuariosList.add(user);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return usuariosList;
    }

    public void refreshUs(){
        ArrayList<Usuarios> list = getUsuariosList();

        DefaultTableModel modelo= (DefaultTableModel) m.tablaUser.getModel();
        Object[] fila= new Object[3];
        for (int i = 0; i < list.size() ; i++) {
            fila[0]=list.get(i).getId();
            fila[1]=list.get(i).getNombre();
            fila[2]=list.get(i).getIdRol();
            modelo.addRow(fila);
        }
    }
    public void actualizarUs(){
        DefaultTableModel modelo= (DefaultTableModel) m.tablaUser.getModel();
        modelo.setRowCount(0);
        refreshUs();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plog.btnLog) {
            //LOGIN
            try{
            char[] ar = plog.ps1.getPassword();
            String psw = "";
            for (int i = 0; i < ar.length; i++) {
                psw += ar[i];
            }

            String sql = "SELECT Nombre, Contraseña FROM usuario WHERE Nombre='" + plog.tf1.getText() + "'" + " AND Contraseña='" + psw + "'";
            if (!m.UserExistsLOG(sql, plog.tf1)) {
                JOptionPane.showMessageDialog(null, "Usuario NO Registrado \n Contacta con el Gerente para que Agregue tu Usuario");
            } else {
                try {
                    m.rs = m.stmt.executeQuery(sql);

                    m.rs.next();
                    String nom = m.rs.getString(1);

                    String contra = m.rs.getString(2);

                    if (nom.equals(plog.tf1.getText()) && psw.equals(contra)) {
                        String sqlRol = "SELECT Nombre, idRol FROM usuario WHERE Nombre='" + plog.tf1.getText()+"'";

                        m.rs=m.stmt.executeQuery(sqlRol);
                        m.rs.next();
                        int idRol=m.rs.getInt(2);
                        String rol="";
                        if (idRol==1) {//ADMIN
                            rol="Administrador";
                            m.mProducto.setEnabled(true);
                            m.mUsuarios.setEnabled(true);
                            m.btEliminarUs.setEnabled(true);
                            m.btEliminarPro.setEnabled(true);
                        }
                        if (idRol==2){//EMPLEADO
                            m.mUsuarios.setEnabled(false);
                            m.mProducto.setEnabled(true);
                            m.btEliminarPro.setEnabled(false);

                            rol="Empleado";
                        }
                        if (idRol==3){//Gerente
                            m.mProducto.setEnabled(true);
                            m.mUsuarios.setEnabled(true);
                            m.btEliminarPro.setEnabled(true);
                            rol="Gerente";

                        }
                        if (idRol==4){//RRHH
                            m.mProducto.setEnabled(false);
                            m.btEliminarUs.setEnabled(true);
                            m.mUsuarios.setEnabled(true);
                            rol="Recursos Humanos";
                        }
                        JPanel pp=new JPanel();
                        //pp.setLayout(null);
                        m.panelCentral.add(pp,BorderLayout.CENTER);
                        m.lBienvenido.setText("<html><p> <br> <p> <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Bienvenido "+nom+" <p>  <br>Permisos de: "+rol+"</html>");

                        m.lBienvenido.setLocation(350,220);

                        pp.add(m.lBienvenido);
                        m.lBienvenido.setVisible(true);

                        setContentPane(m);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            }catch (RuntimeException ev){
                JOptionPane.showMessageDialog(null,"Favor de rellenar todos los campos correctamente");

            }



        }

        if (e.getSource() == m.piAgregar) {
            //PANEL PRODUCTOS
            m.panelCentral.removeAll();
            m.panelCentral.add(m.panelProductos);
            m.panelProductos.setVisible(true);

            m.updateUI();
        }
            //PANEL USUARIOS
        if (e.getSource() == m.uiAdd) {
            m.panelCentral.removeAll();
            m.panelCentral.add(m.panelUsuarios);
            m.panelUsuarios.setVisible(true);
            limpiar();
            m.updateUI();
            JOptionPane.showMessageDialog(null,"1: ADMIN \n 2: EMPLEADO \n 3: GERENTE \n 4: RRHH", "CLASIFICACIÓN DE ROLES", JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource() == m.btSalir) {
            m.panelCentral.removeAll();
            plog.tf1.setText("");
            plog.ps1.setText("");
            setContentPane(plog);
        }

        if (e.getSource() == plog.btnExit) {
            System.exit(0);
        }

        if (e.getSource() == m.btEliminarPro) {
            pd= new ProductosDatos();
            pd.Eliminar(new Producto(id));
        }

        if (e.getSource() == m.btBuscarPro) {
            actualizar();
            limpiar();
        }

        if (e.getSource() == m.btEditarPro) {
            pd = new ProductosDatos();
            pd.Editar(new Producto(id,m.txNombrePro.getText(),Integer.parseInt(m.txCantidad.getText()),Integer.parseInt(m.txPrecio.getText())));
        }

        if (e.getSource() == m.btAgregarPro) {
            int txCant = Integer.parseInt(m.txCantidad.getText());
            int txPrice = Integer.parseInt(m.txPrecio.getText());
            String nombre = m.txNombrePro.getText();
            String k = m.txRegistrador.getText();


            pd = new ProductosDatos();

            Calendar calendario = Calendar.getInstance();
            java.util.Date fechaActual = calendario.getTime();
            Date fechaSQL = new Date(fechaActual.getTime());

            if (m.verificarUsuario(k)) {
                pd.Agregar(new Producto(nombre, txCant, txPrice, Integer.parseInt(k), fechaSQL));
                JOptionPane.showMessageDialog(null, "Agregado con Éxito");
            }
        }



        if (e.getSource()==m.btAgregarUs){
            ud= new UsuariosDatos();

            if (!m.UserExists("SELECT COUNT(Nombre) FROM usuario WHERE Nombre='"+m.txNombre.getText()+"'")) {
                if (!Arrays.equals(m.pswd.getPassword(), m.Cpswd.getPassword())) {
                    JOptionPane.showMessageDialog(null, "La Contraseña no coincide");
                }else {
                 ud.Agregar(new Usuarios(m.txNombre.getText(), String.valueOf(m.pswd.getPassword()),m.txRol.getSelectedIndex()));
                 JOptionPane.showMessageDialog(null,"Usuario creado con éxito");
                }
            }else {
                JOptionPane.showMessageDialog(null,"Nombre de Usuario Ocupado");
                if (!Arrays.equals(m.pswd.getPassword(), m.Cpswd.getPassword())) {
                    JOptionPane.showMessageDialog(null, "La Contraseña no coincide");
                }
            }



        }

        if (e.getSource()==m.btEditarUs){
            ud= new UsuariosDatos();
            ud.EditarUs(new Usuarios(idUs,m.txNombre.getText(), Integer.parseInt(Objects.requireNonNull(m.txRol.getSelectedItem()).toString().substring(0,1))));

        }
        if (e.getSource()==m.btEliminarUs){
            ud=new UsuariosDatos();
            ud.Eliminar(new Usuarios(idUs));
        }

        if (e.getSource()==m.btBuscarUs){
            actualizarUs();
            limpiar();
        }


    }

    public void limpiar(){
        m.txNombrePro.setText("");
        m.txPrecio.setText("");
        m.txCantidad.setText("");
        m.txRegistrador.setText("");
        m.txNombre.setText("");
        m.txRol.setSelectedIndex(-1);
        m.pswd.setText("");
        m.Cpswd.setText("");
        m.txRegistrador.setEnabled(true);
        m.pswd.setEnabled(true);
        m.Cpswd.setEnabled(true);
    }
}


