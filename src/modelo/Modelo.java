/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import javax.swing.JOptionPane;
import vista.Vista;
import static vista.Vista.Combo;

public class Modelo {

    Vista v;
    Controlador c;
    Connection cc;
    Connection cn = Conexion();

    public void CargarMetodos() {
        MostrarTabla("");
    }

    public Connection Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cc = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            System.out.println("Conexion Exitosa");
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return cc;
    }

    public void Actualizar() {
        String id = v.txtId.getText();
        if (v.txtNombre.getText().equals("") || v.txtApellido.getText().equals("") || v.txtEdad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Faltan campos");
        } else {
            try {
                PreparedStatement ppt = cn.prepareStatement("UPDATE usuario SET "
                        + "Nombre = '" + v.txtNombre.getText() + "',"
                        + "Apellido = '" + v.txtApellido.getText() + "',"
                        + "Edad = '" + v.txtEdad.getText() + "',"
                        + "Sexo = '" + Combo.getSelectedItem() + "',"
                        + "Fecha = '" + v.txtFecha.getText() + "'"
                        + "WHERE id = '" + id + "'");
                System.out.println(ppt.toString());

                ppt.executeUpdate();
                MostrarTabla("");
                JOptionPane.showMessageDialog(null, "Datos Actualizados");
            } catch (SQLException e) {
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    public void Eliminar() {
        int fila = v.Tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                String id = v.Tabla.getValueAt(fila, 0).toString();
                PreparedStatement ppt = cn.prepareStatement("DELETE FROM usuario "
                        + "where id = '" + id + "'");
                System.out.println(ppt.toString());
                ppt.executeUpdate();
                MostrarTabla("");
                JOptionPane.showMessageDialog(null, "Dato Eliminado");
            } catch (SQLException e) {
                System.out.println("Error" + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }

    public void Consultar() {
        int fila = v.Tabla.getSelectedRow();
        if (fila >= 0) {
            v.txtId.setText(v.Tabla.getValueAt(fila, 0).toString());
            v.txtNombre.setText(v.Tabla.getValueAt(fila, 1).toString());
            v.txtApellido.setText(v.Tabla.getValueAt(fila, 2).toString());
            v.txtEdad.setText(v.Tabla.getValueAt(fila, 3).toString());
            v.Combo.setSelectedItem(v.Tabla.getValueAt(fila, 4).toString());
            v.txtFecha.setText(v.Tabla.getValueAt(fila, 5).toString());
            v.Fecha.setDateFormatString(v.Tabla.getValueAt(fila, 5).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }

    public void Buscar() {
        if (v.txtId.equals("")) {
            JOptionPane.showMessageDialog(null, "Campo Vacio");
        } else {
            MostrarTabla(v.txtId.getText());
        }
    }

    void MostrarTabla(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha");
        v.Tabla.setModel(modelo);

        v.Combo.addItem("--Selecciona--");
        v.Combo.addItem("Hombre");
        v.Combo.addItem("Mujer");
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT * FROM usuario";
        } else {
            sql = "SELECT * FROM usuario where id = '" + valor + "'";
        }

        String datos[] = new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            v.Tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no se pudo mostrar datos" + e);
        }

    }

    public void EnviarDatos() {
        /*botones.add(mujer);
        botones.add(hombre);
         */
        String dia = Integer.toString(v.Fecha.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(v.Fecha.getCalendar().get(Calendar.MONTH) + 1);
        String year = Integer.toString(v.Fecha.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + "/" + mes + "/" + year);
        if (v.txtNombre.getText().equals("") || v.txtApellido.getText().equals("") || v.txtEdad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Faltan campos");
        } else {
            try {
                PreparedStatement ppt = cn.prepareStatement("INSERT INTO usuario(Nombre,Apellido,Edad,Sexo,Fecha)"
                        + "VALUES(?,?,?,?,?)");
                ppt.setString(1, v.txtNombre.getText());
                ppt.setString(2, v.txtApellido.getText());
                ppt.setString(3, v.txtEdad.getText());

                if (v.Combo.getSelectedItem().toString().equals("--Selecciona--")) {
                    JOptionPane.showMessageDialog(null, "Selecciona un valor en Sexo");
                } else {
                    ppt.setString(4, v.Combo.getSelectedItem().toString());

                    v.txtNombre.setText("");
                    v.txtApellido.setText("");
                    v.txtEdad.setText("");
                    ppt.setString(5, fecha);

                    ppt.executeUpdate();
                    MostrarTabla("");
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Datos Guardados");
            }
        }


        /*
        if(mujer.isSelected()){
            Datos[3] = "Mujer";
        }
        else if(hombre.isSelected()){
            Datos[3] = "Hombre";
        }else{
            Datos[3] = "No se especifico Sexo";
        }
         */
    }
}
