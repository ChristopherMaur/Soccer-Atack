/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.entes;

import java.awt.Graphics;

/**
 *
 * @author usuario
 */
public class Delantero extends Adversario{
    
    
    public Delantero(int idAdversario, String nombre) {
        super(idAdversario, nombre);
    }
    
    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
        super.dibujar(g, puntoX, puntoY);
        
    }
    
}
