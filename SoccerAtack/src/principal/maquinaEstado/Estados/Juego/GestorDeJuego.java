/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.maquinaEstado.Estados.Juego;

import java.awt.Color;
import java.awt.Graphics;
import principal.Constantes;
import principal.ElementosPrincipales;
import principal.herramientas.Cronometro;
import principal.herramientas.DibujoDebug;
import principal.interfaz_usuario.InterfazUsuario;
import principal.maquinaEstado.EstadoJuego;

/**
 *
 * @author usuario
 */
public class GestorDeJuego implements EstadoJuego {

    public GestorDeJuego() {
    }

    private void recargarJuego() {

        if (ElementosPrincipales.mapa.obtenerZonaArcos().intersects(ElementosPrincipales.jugador.obtener_LIMITE_IZQUIERDA())) {

            pausa3();
            ElementosPrincipales.jugador.establecerPosicionX(ElementosPrincipales.mapa.obtenerPocisionInicial().getX());
            ElementosPrincipales.jugador.establecerPosicionY(ElementosPrincipales.mapa.obtenerPocisionInicial().getY());

        }

    }

    public static void pausa3() {

        Cronometro time = new Cronometro();
        time.Strat();
        while (time.get_segundos() < 4) {
            System.out.println(time.get_segundos());
        }
        time.Parar();
    }

    @Override
    public void actualiar() {
        
        ElementosPrincipales.jugador.actualizar();
        ElementosPrincipales.mapa.actualizar();
        ElementosPrincipales.delantero.actualizar();
        recargarJuego();
    }

    @Override
    public void dibujar(Graphics g) {
        ElementosPrincipales.mapa.dibujar(g);
        ElementosPrincipales.jugador.dibujar(g);
        ElementosPrincipales.delantero.dibujar(g,
                (int)ElementosPrincipales.delantero.obtenerPosicionX()-ElementosPrincipales.jugador.obtenerPosicionXint() + Constantes.MARGEN_X,
                (int)ElementosPrincipales.delantero.obtenerPosicionY()-ElementosPrincipales.jugador.obtenerPosicionYint() + Constantes.MARGEN_Y);
        DibujoDebug.dibujarString(g, "X = " + ElementosPrincipales.jugador.obtenerPosicionX() + "  =" + (int) ElementosPrincipales.jugador.obtenerPosicionX() / 32, 20, 20, Color.GREEN);
        //g.drawString("X = " + jugador.obtenerPosicionX(), 20, 20);
        DibujoDebug.dibujarString(g, "Y = " + ElementosPrincipales.jugador.obtenerPosicionY() + "  =" + (int) ElementosPrincipales.jugador.obtenerPosicionY() / 32, 20, 30, Color.GREEN);
        //g.drawString("Y = " + jugador.obtenerPosicionY(), 20, 30);

        DibujoDebug.dibujarString(g, "arcos coordenadas: X = " + ElementosPrincipales.mapa.obtenerArcos().x + "Y = " + ElementosPrincipales.mapa.obtenerArcos().y,
                20, 80, Color.GREEN);
        ///dibujar zona de salida ARCOS
        DibujoDebug.dibujarRectanguloContorno(g, ElementosPrincipales.mapa.obtenerZonaArcos(), Color.GREEN);

        InterfazUsuario.DibujarBarraResistencia(g, ElementosPrincipales.jugador.resistencia);
    }

}
