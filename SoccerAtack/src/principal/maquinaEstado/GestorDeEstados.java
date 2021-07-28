/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.maquinaEstado;

import java.awt.Graphics;
import principal.maquinaEstado.Estados.Juego.GestorDeJuego;

/**
 *
 * @author usuario
 */
public class GestorDeEstados {

    private EstadoJuego[] estados;
    private EstadoJuego estadoActual;

    public GestorDeEstados() {
        iniciarEstados();
        iniciarEstadoActual();
    }

    private void iniciarEstados() {
        estados = new EstadoJuego[1];
        estados[0] = new GestorDeJuego();
        //iniciar mas estados al Crearlos

    }

    private void iniciarEstadoActual() {
        estadoActual = estados[0];
    }

    public void actualizar() {
        estadoActual.actualiar();
    }

    public void dibujar(final Graphics g) {
        estadoActual.dibujar(g);
    }

    public void CambiarEstadoActual(final int nuevoEstado) {
        estadoActual = estados[nuevoEstado];
    }

    public EstadoJuego obtenerEstadoActual() {
        return estadoActual;
    }

}
