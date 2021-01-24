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
        MostrarTabla();
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

  public void Consultar(){
      int fila= v.Tabla.getSelectedRow();
      if(fila>=0){
          v.txtNombre.setText(v.Tabla.getValueAt(fila, 1).toString());
          v.txtApellido.setText(v.Tabla.getValueAt(fila, 2).toString());
          v.txtEdad.setText(v.Tabla.getValueAt(fila, 3).toString());
          v.Combo.setSelectedItem(v.Tabla.getValueAt(fila, 4).toString());
          v.txtFecha.setText(v.Tabla.getValueAt(fila, 5).toString());
         v.Fecha.setDateFormatString(v.Tabla.getValueAt(fila, 5).toString());
      }else{
          JOptionPane.showMessageDialog(null,   "Seleccione una fila");
      }
  }

    void MostrarTabla() {
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
        
        String sql = "SELECT * FROM usuario";
        
        String datos[] = new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
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
            JOptionPane.showMessageDialog(null,"no se pudo mostrar datos" + e);
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
               MostrarTabla();
                JOptionPane.showMessageDialog(null, "Datos Guardados");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Datos Guardados");
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
