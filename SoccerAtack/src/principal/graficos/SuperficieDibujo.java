/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.herramientas.DibujoDebug;
import principal.maquinaEstado.GestorDeEstados;

public class SuperficieDibujo extends Canvas {

    //Atributos
    private int ancho;
    private int alto;
    private Raton raton;

    //Constructor
    public SuperficieDibujo(final int ancho, final int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.raton = new Raton();

        setIgnoreRepaint(true);
        setCursor(raton.obtenerCursor());
        setPreferredSize(new Dimension(ancho, alto));
        addKeyListener(GestorControles.teclado);
        setFocusable(true);
        requestFocus();
    }

    //Metodos
    public void dibujar(final GestorDeEstados ge) {
        BufferStrategy buffer = getBufferStrategy();

        if (buffer == null) {
            createBufferStrategy(4);
            return;
        }

        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        DibujoDebug.reiniciarContadorOPS();

        DibujoDebug.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA, Color.BLACK);
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);

        if (Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {
            g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
        }

        ge.dibujar(g);

        g.setColor(Color.GREEN);
        DibujoDebug.dibujarString(g, "FPS: " + GestorPrincipal.obtenerFPS(), 20, 60, Color.GREEN);
        DibujoDebug.dibujarString(g, "APS: " + GestorPrincipal.obtenerAPS(), 20, 50, Color.GREEN);
        DibujoDebug.dibujarString(g, "OPF: " + DibujoDebug.obtenerOPS(), 20, 70, Color.GREEN);
        //g.drawString("FPS: " + GestorPrincipal.obtenerFPS(), 20, 60);
        //g.drawString("APS: " + GestorPrincipal.obtenerAPS(), 20, 50);
        
        Toolkit.getDefaultToolkit().sync();

        g.dispose();

        buffer.show();
    }

    public int obtenerAncho() {
        return ancho;
    }

    public int obtenerAlto() {
        return alto;
    }

}
