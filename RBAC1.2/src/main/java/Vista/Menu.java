package Vista;


import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Menu extends JPanel implements ActionListener, RowSetListener {
    JPanel panelBarra, divisor1, divisor2,panelCentral, panelProductos,  panelUsuarios, panelFormProd, panelFormUs, panelFormMov;
    JMenuBar barra;
    JMenu mProducto,  mUsuarios;
    JMenuItem piAgregar, uiAdd;
    JScrollPane scrollTabla,  scrollTablaUs;
    public JTable tablaProd,  tablaUser;
    DefaultTableModel tm;
    JLabel lNombre, lPrecio, lCantidad, lRegistrador, lRol, lPsw, lCpsw, lBienvenido;
    JTextField txNombre, txPrecio, txCantidad, txRegistrador,txNombrePro;
    JComboBox txRol;
    JPasswordField pswd,Cpswd;

    Dimension dText=new Dimension(300,40);
    Dimension dLabel=new Dimension(500,50);
    Dimension dDivisor=new Dimension(900,2);
    Dimension dEtiquetas=new Dimension(120,20);
    Dimension dBtn=new Dimension(115,30);

    Font fuenteEncabezado=new Font("arial",Font.BOLD,28);
    Font fuenteItem=new Font("arial",Font.PLAIN,18);
    Font fuenteTxt=new Font("arial",Font.PLAIN,18);
    Font fuente3=new Font("arial",Font.PLAIN,16);
    JButton btAgregarPro, btBuscarPro, btBuscarUs, btEliminarUs, btAgregarUs,btEliminarPro,btEditarPro, btEditarUs, btSalir;
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tilinc", "root", "12345");
    Statement stmt=con.createStatement();
    ResultSet rs=null;

    public Menu() throws SQLException {
        setSize(900,700);
        setLayout(null);

        barra= new JMenuBar();
        panelBarra= new JPanel();
        panelBarra.setSize(dLabel);
        panelBarra.setLocation(200,0);

        //divisores
        divisor1=new JPanel();
        divisor1.setSize(dDivisor);
        divisor1.setBackground(Color.DARK_GRAY);
        divisor1.setLocation(0,50);
        divisor1.setVisible(true);

        divisor2=new JPanel();
        divisor2.setSize(dDivisor);
        divisor2.setBackground(Color.DARK_GRAY);
        divisor2.setLocation(0,600);
        divisor2.setVisible(true);

        add(divisor1);
        add(divisor2);

        //declarar
        mProducto = new JMenu("Producto");
        //mProducto.setEnabled(false); para cuando no tenga permiso para usar esa funcion
        piAgregar= new JMenuItem("Seleccionar");


        mUsuarios= new JMenu("Usuarios");
        uiAdd= new JMenuItem("Seleccionar");


        //fuentes del menu producto y sus items
        mProducto.setFont(fuenteEncabezado);
        piAgregar.setFont(fuenteItem);


        //Fuente del menu usuarios y sus items
        mUsuarios.setFont(fuenteEncabezado);
        uiAdd.setFont(fuenteItem);

        //agregar los items al menu correspondiente
        mProducto.add(piAgregar);

        mUsuarios.add(uiAdd);

        //Agregar los menus a la barra de menu
        barra.add(mProducto);

        barra.add(mUsuarios);

        panelBarra.add(barra);
        add(panelBarra);


        //panel Central
        panelCentral= new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setSize(875,540);
        panelCentral.setLocation(0,60);
        add(panelCentral);

        lBienvenido= new JLabel();
        lBienvenido.setSize(500,100);
        panelCentral.add(lBienvenido, BorderLayout.CENTER);
        lBienvenido.setFont(fuenteEncabezado);


        //panel  y tabla productos
        panelProductos= new JPanel();
        panelProductos.setLayout(new BorderLayout());
        panelProductos.setLocation(0,60);


        tablaProd=tabla("SELECT p.id, p.nombre, p.cantidad, p.precio, p.idUser, p.fechaIngreso FROM producto AS p INNER JOIN usuario AS u on p.idUser=u.id");

        scrollTabla= new JScrollPane(tablaProd);
        scrollTabla.setSize(875,540);
        panelFormProducto();

        panelProductos.add(scrollTabla,BorderLayout.EAST);



        //panel y tabla usuarios
        panelUsuarios=new JPanel();
        panelUsuarios.setLayout(new BorderLayout());
        panelUsuarios.setSize(875,540);
        panelUsuarios.setLocation(0,60);

        tablaUser=tabla("SELECT id, Nombre, idRol FROM usuario");
        tablaUser.setSize(875,540);

        scrollTablaUs= new JScrollPane(tablaUser);
        //scrollTablaUs.setSize(500,540);
        panelFormUser();
        panelUsuarios.add(scrollTablaUs,BorderLayout.EAST);

        btSalir= new JButton("Salir");
        btSalir.setSize(dBtn);
        btSalir.setLocation(40,610);
        btSalir.setFont(fuenteTxt);
        btSalir.setFocusable(false);
        btSalir.setVisible(true);
        add(btSalir);

        setVisible(true);


    }

    public JTable tabla(String sql) throws SQLException {

        JTable t=null;
        try {
        rs = stmt.executeQuery(sql);

        tm= new DefaultTableModel();

        int nColumnas = rs.getMetaData().getColumnCount();
            for (int i = 1; i <=nColumnas ; i++) {
                tm.addColumn(rs.getMetaData().getColumnLabel(i));
            }
            while (rs.next()){
                Object[] filas= new Object[nColumnas];
                for (int i = 1; i <=nColumnas ; i++) {
                    filas[i-1]= rs.getObject(i);
                }
                tm.addRow(filas);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        t=new JTable(tm);

        //auto ajustar ancho
        final TableColumnModel columnModel = t.getColumnModel();
               for (int column = 0; column < t.getColumnCount(); column++) {
            int width = 5; // Min width
            for (int row = 0; row < t.getRowCount(); row++) {
                TableCellRenderer renderer = t.getCellRenderer(row, column);
                Component comp = t.prepareRenderer(renderer, row, column);

                width = Math.max(comp.getPreferredSize().width-3, width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        t.setEnabled(true);
        return t;
    }

    public void panelFormMovim(){
        panelFormMov = new JPanel();
        panelFormMov.setLayout(null);
        panelFormMov.setSize(350,540);
        panelFormMov.setLocation(15,0);

    }

    public void panelFormUser(){
        panelFormUs= new JPanel();
        panelFormUs.setLayout(null);
        panelFormUs.setSize(350,540);
        panelFormUs.setLocation(15,0);

        lNombre= new JLabel("Nombre");
        lNombre.setLocation(panelFormUs.getWidth()/2-160,20);
        lNombre.setSize(dEtiquetas);
        lNombre.setFont(fuenteTxt);
        lNombre.setVisible(true);
        txNombre=new JTextField();
        txNombre.setLocation(panelFormUs.getWidth()/2-50,20);
        txNombre.setSize(220,25);
        txNombre.setFont(fuente3);
        txNombre.setVisible(true);

        lRol= new JLabel("Rol");
        lRol.setSize(dEtiquetas);
        lRol.setLocation(panelFormUs.getWidth()/2-160,90);
        lRol.setFont(fuenteTxt);
        lRol.setVisible(true);

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        try {
            String sql = "SELECT rol FROM rol";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            int i=1;
            while (rs.next()) {

                String nombre = rs.getString("rol");
                modelo.addElement(i+": "+nombre);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txRol=new JComboBox<>(modelo);
        txRol.setLocation(panelFormUs.getWidth()/2-50,90);
        txRol.setSize(220,25);
        txRol.setFont(fuente3);
        txRol.setVisible(true);


        lPsw= new JLabel("Contraseña");
        lPsw.setSize(dEtiquetas);
        lPsw.setLocation(panelFormUs.getWidth()/2-160,160);
        lPsw.setFont(fuenteTxt);
        lPsw.setVisible(true);
        pswd=new JPasswordField();
        pswd.setLocation(panelFormUs.getWidth()/2-50,160);
        pswd.setSize(220,25);
        pswd.setFont(fuente3);
        pswd.setVisible(true);


        lCpsw= new JLabel("Confirmar");
        lCpsw.setSize(dEtiquetas);
        lCpsw.setLocation(panelFormUs.getWidth()/2-160,230);
        lCpsw.setFont(fuenteTxt);
        lCpsw.setVisible(true);
        Cpswd=new JPasswordField();
        Cpswd.setLocation(panelFormUs.getWidth()/2-50,230);
        Cpswd.setSize(220,25);
        Cpswd.setFont(fuente3);
        Cpswd.setVisible(true);



        btAgregarUs= new JButton("Agregar");
        btAgregarUs.setSize(dBtn);
        btAgregarUs.setLocation(panelFormUs.getWidth()/2-120,350);
        btAgregarUs.setFont(fuenteTxt);
        btAgregarUs.setFocusable(false);

        btEliminarUs= new JButton("Eliminar");
        btEliminarUs.setSize(dBtn);
        btEliminarUs.setLocation(panelFormUs.getWidth()/2+20,350);
        btEliminarUs.setFont(fuenteTxt);
        btEliminarUs.setFocusable(false);


        btEditarUs= new JButton("Editar");
        btEditarUs.setSize(dBtn);
        btEditarUs.setLocation(panelFormUs.getWidth()/2-120,400);
        btEditarUs.setFont(fuenteTxt);
        btEditarUs.setFocusable(false);

        btBuscarUs= new JButton("Actualizar");
        btBuscarUs.setSize(dBtn);
        btBuscarUs.setLocation(panelFormUs.getWidth()/2+20,400);
        btBuscarUs.setFont(fuenteTxt);
        btBuscarUs.setFocusable(false);

        panelFormUs.add(lNombre);
        panelFormUs.add(txNombre);
        panelFormUs.add(lRol);
        panelFormUs.add(txRol);
        panelFormUs.add(lPsw);
        panelFormUs.add(pswd);
        panelFormUs.add(lCpsw);
        panelFormUs.add(Cpswd);
        panelFormUs.add(btAgregarUs);
        panelFormUs.add(btEliminarUs);
        panelFormUs.add(btEditarUs);
        panelFormUs.add(btBuscarUs);

        panelFormUs.setVisible(true);
        panelUsuarios.add(panelFormUs);


    }

    public void panelFormProducto(){
        panelFormProd= new JPanel();
        panelFormProd.setLayout(null);
        panelFormProd.setSize(350,540);
        panelFormProd.setLocation(15,0);

        lNombre= new JLabel("Nombre");
        lNombre.setLocation(panelFormProd.getWidth()/2-160,20);
        lNombre.setSize(dEtiquetas);
        lNombre.setFont(fuenteTxt);
        lNombre.setVisible(true);
        txNombrePro=new JTextField();
        txNombrePro.setLocation(panelFormProd.getWidth()/2-50,20);
        txNombrePro.setSize(220,25);
        txNombrePro.setFont(fuente3);
        txNombrePro.setVisible(true);


        lCantidad= new JLabel("Cantidad");
        lCantidad.setSize(dEtiquetas);
        lCantidad.setLocation(panelFormProd.getWidth()/2-160,90);
        lCantidad.setFont(fuenteTxt);
        lCantidad.setVisible(true);
        txCantidad=new JTextField();
        txCantidad.setLocation(panelFormProd.getWidth()/2-50,90);
        txCantidad.setSize(220,25);
        txCantidad.setFont(fuente3);
        txCantidad.setVisible(true);


        lPrecio= new JLabel("Precio");
        lPrecio.setSize(dEtiquetas);
        lPrecio.setLocation(panelFormProd.getWidth()/2-160,160);
        lPrecio.setFont(fuenteTxt);
        lPrecio.setVisible(true);
        txPrecio=new JTextField();
        txPrecio.setLocation(panelFormProd.getWidth()/2-50,160);
        txPrecio.setSize(220,25);
        txPrecio.setFont(fuente3);
        txPrecio.setVisible(true);


        lRegistrador= new JLabel("ID Empleado");
        lRegistrador.setSize(dEtiquetas);
        lRegistrador.setLocation(panelFormProd.getWidth()/2-160,230);
        lRegistrador.setFont(fuenteTxt);
        lRegistrador.setVisible(true);
        txRegistrador=new JTextField();
        txRegistrador.setLocation(panelFormProd.getWidth()/2-50,230);
        txRegistrador.setSize(220,25);
        txRegistrador.setFont(fuente3);
        txRegistrador.setVisible(true);

        //AGREGAR PRODUCTO
        btAgregarPro= new JButton("Agregar");
        btAgregarPro.setSize(dBtn);
        btAgregarPro.setLocation(panelFormProd.getWidth()/2-120,350);
        btAgregarPro.setFont(fuenteTxt);
        btAgregarPro.setFocusable(false);

        btEliminarPro= new JButton("Eliminar");
        btEliminarPro.setSize(dBtn);
        btEliminarPro.setLocation(panelFormProd.getWidth()/2+20,350);
        btEliminarPro.setFont(fuenteTxt);
        btEliminarPro.setFocusable(false);


        btEditarPro= new JButton("Editar");
        btEditarPro.setSize(dBtn);
        btEditarPro.setLocation(panelFormProd.getWidth()/2-120,400);
        btEditarPro.setFont(fuenteTxt);
        btEditarPro.setFocusable(false);

        btBuscarPro= new JButton("Actualizar");
        btBuscarPro.setSize(dBtn);
        btBuscarPro.setLocation(panelFormProd.getWidth()/2+20,400);
        btBuscarPro.setFont(fuenteTxt);
        btBuscarPro.setFocusable(false);


        panelFormProd.add(lNombre);
        panelFormProd.add(txNombrePro);
        panelFormProd.add(lCantidad);
        panelFormProd.add(txCantidad);
        panelFormProd.add(lPrecio);
        panelFormProd.add(txPrecio);
        panelFormProd.add(lRegistrador);
        panelFormProd.add(txRegistrador);
        panelFormProd.add(btAgregarPro);
        panelFormProd.add(btEliminarPro);
        panelFormProd.add(btEditarPro);
        panelFormProd.add(btBuscarPro);

        panelFormProd.setVisible(true);
        panelProductos.add(panelFormProd);
    }
    public boolean verificarUsuario(String k){
        boolean ok= false;

        try {
            k = txRegistrador.getText();
            String sql="SELECT COUNT(id) FROM usuario WHERE id ="+Integer.parseInt(k);
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                ok=true;

            } else {
                ok=false;
                JOptionPane.showMessageDialog(null,"El ID de usuario seleccionado no existe");


            }

    } catch (SQLException xd) {
            JOptionPane.showMessageDialog(null,"El usuario a elegir no existe");
        xd.printStackTrace();
    }
     return ok;
    }

    public boolean UserExists(String sql){
        boolean ok= false;
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            resultSet.next();

            int count = resultSet.getInt(1);
            if(count>0) {
                ok = true;
            } // usuario no existe
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ok;
    }

    public boolean UserExistsLOG(String sql, JTextField log){
        boolean ok= false;
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            resultSet.next();

            String count = resultSet.getString(1);
            if(count.equals(log.getText())) {
                ok = true;

            } // usuario no existe
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ok;
    }



    @Override
    public void actionPerformed(ActionEvent e) {



    }

    @Override
    public void rowSetChanged(RowSetEvent event) {

    }

    @Override
    public void rowChanged(RowSetEvent event) {


    }

    @Override
    public void cursorMoved(RowSetEvent event) {

    }
}
