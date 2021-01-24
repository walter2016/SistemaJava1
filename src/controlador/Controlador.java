/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.Vista;
import vista.Login;
import modelo.Modelo;
/**
 *
 * @author walte
 */
public class Controlador implements ActionListener{
    
    private Modelo m;
    private Vista v;
    private Login l;
    
    public Controlador(Modelo m, Vista v){
        this.m = m;
        this.v = v;
        this.v.EnviarDatos.addActionListener(this);
        this.v.bconsultar.addActionListener(this);
        this.v.bActualizar.addActionListener(this);
        this.v.bEliminar.addActionListener(this);
        this.v.bBuscar.addActionListener(this);
    }
    
    public void Iniciar(){
        v.setTitle("Sistema MVC");
        v.pack();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
        m.CargarMetodos();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(v.EnviarDatos == e.getSource()){
            try{
                m.EnviarDatos();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }else  if(v.bconsultar == e.getSource()){
            try{
                m.Consultar();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
                System.out.println(e.toString());
            }
        }
        else  if(v.bActualizar == e.getSource()){
            try{
                m.Actualizar();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
                System.out.println(e.toString());
            }
        }
         else  if(v.bEliminar == e.getSource()){
            try{
                m.Eliminar();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
                System.out.println(e.toString());
            }
        } else  if(v.bBuscar == e.getSource()){
            try{
                m.Buscar();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
                System.out.println(e.toString());
            }
        }
    }
    
}
