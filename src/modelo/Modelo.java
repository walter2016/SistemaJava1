/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import java.util.Calendar;
import javax.swing.JOptionPane;
import vista.Vista;
import static vista.Vista.Combo;


public class Modelo {
     DefaultTableModel modelo = new DefaultTableModel();
     Vista v;
     Controlador c;
     
    public void CargarMetodos(){
         MostrarTabla();
     }
     
      void MostrarTabla() {
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha");
        v.Tabla.setModel(modelo);

        v.Combo.addItem("--Selecciona--");
        v.Combo.addItem("Hombre");
        v.Combo.addItem("Mujer");

    }
      
    public  void EnviarDatos(){
            /*botones.add(mujer);
        botones.add(hombre);
         */
        String dia = Integer.toString(v.Fecha.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(v.Fecha.getCalendar().get(Calendar.MONTH)+1);
        String year = Integer.toString(v.Fecha.getCalendar().get(Calendar.YEAR));
        String fecha = (dia+"/"+mes+"/"+year);

        String[] Datos = new String[5];
        Datos[0] = v.txtNombre.getText();
        Datos[1] = v.txtApellido.getText();
        Datos[2] = v.txtEdad.getText();
        if (v.Combo.getSelectedItem().toString().equals("--Selecciona--")) {
            JOptionPane.showMessageDialog(null, "Selecciona un valor en Sexo");
        } else {
            Datos[3] = v.Combo.getSelectedItem().toString();
            v.txtNombre.setText("");
            v.txtApellido.setText("");
            v.txtEdad.setText("");
            Datos[4] = fecha;
            modelo.addRow(Datos);
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
