package org.example;

import Datos.ProductosDatos;
import Datos.UsuariosDatos;
import Vista.Pantalla;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Pantalla p=new Pantalla();
        ProductosDatos p2= new ProductosDatos();
        UsuariosDatos ud= new UsuariosDatos();
        System.out.println(ud.listar());
        System.out.println(p2.listar());




    }
}