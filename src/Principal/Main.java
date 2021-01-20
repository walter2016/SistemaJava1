/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;
import controlador.Controlador;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.Modelo;
import vista.Vista;
/**
 *
 * @author walte
 */
public class Main {
    
    public static void main(String[] args){
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        Modelo m = new Modelo();
        Vista v = new Vista();
        Controlador c = new Controlador(m, v);
        c.Iniciar();
        
    }
    
}
