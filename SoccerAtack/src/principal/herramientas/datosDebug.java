/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.herramientas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class datosDebug {
    private static ArrayList<String> datos = new ArrayList<String>();
    public static void enviarDatos(final String dato){
        datos.add(dato);
    } 
    public static void dibujarDatos(final Graphics g){
        g.setColor(Color.GREEN);
        
        for (int i = 0; i < datos.size(); i++) {
            
        }
    
    }
}
