/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.interfaz_usuario;

import java.awt.Color;
import java.awt.Graphics;
import principal.herramientas.DibujoDebug;

/**
 *
 * @author usuario
 */
public class InterfazUsuario {

    public static void DibujarBarraResistencia(Graphics g, int resistencia) {
        int ancho = 300 * resistencia / 600;
        Color verdeOscuro = new Color(51, 174, 15);
        Color verdeClaro = new Color(81, 255, 30);

        DibujoDebug.dibujarRectangulContorno(g, 19, 549, 301, 17, Color.WHITE);
        //g.setColor(Color.WHITE);
        //g.drawRect(19, 549, 301, 17);

        DibujoDebug.dibujarRectanguloRelleno(g, 20, 550, ancho, 5, verdeClaro);
        //g.setColor(verdeClaro);
        //g.fillRect(20, 550, ancho, 5);
        DibujoDebug.dibujarRectanguloRelleno(g, 20, 555, ancho, 10, verdeOscuro);
        //g.setColor(verdeOscuro);
        //g.fillRect(20, 555, ancho, 10);

    }

}
