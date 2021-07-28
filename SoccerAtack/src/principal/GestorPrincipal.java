/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import principal.graficos.SuperficieDibujo;
import principal.graficos.Ventana;
import principal.maquinaEstado.GestorDeEstados;

/**
 *
 * @author usuario
 */
public class GestorPrincipal {

    private boolean enfuncionamiento = false;
    private String titulo;
    private int ancho;
    private int alto;

    private SuperficieDibujo sd;
    private Ventana ventana;
    private GestorDeEstados ge;

    private static int aps = 0;
    private static int fps = 0;

    private GestorPrincipal(final String titulo, final int alto, final int ancho) {
        this.alto = alto;
        this.ancho = ancho;
        this.titulo = titulo;
    }

    public static void main(String[] args) {
        GestorPrincipal gp = new GestorPrincipal("Soccer - Atack", Constantes.ALTO_PANTALLA_COMPLETA, Constantes.ANCHO_PANTALLA_COMPLETA);
        //Constantes.ANCHO_PANTALLA = 800;
        //Constantes.ALTO_PANTALLA = 600;
        gp.iniciarJuego();
        gp.iniciarBuclePrincipal();

    }

    private void iniciarJuego() {
        enfuncionamiento = true;
        inicializar();
    }

    private void iniciarBuclePrincipal() {
        int actualizacionesAcumuladas = 0;
        int framesAcumulados = 0;

        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO = 60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

        long referenciaActualizacion = System.nanoTime();

        long referenciaContador = System.nanoTime();
        double tiempoTranscurrido;
        double delta = 0;

        while (enfuncionamiento) {
            final long inicioBucle = System.nanoTime();

            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;

            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

            while (delta >= 1) {
                actualizar();
                actualizacionesAcumuladas++;
                delta--;
            }

            dibujar();
            framesAcumulados++;

            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
                fps = framesAcumulados;
                aps = actualizacionesAcumuladas;
                //ventana.setTitle(TITULO + " || APS: " + APS + " || FPS " + FPS);
                //CONTADOR_APS = "APS " + APS;
                //CONTADOR_FPS = "FPS " + FPS;
                System.out.println("FPS:" + framesAcumulados + "  APS: " + actualizacionesAcumuladas);
                actualizacionesAcumuladas = 0;
                framesAcumulados = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }

    public static int obtenerFPS() {
        return fps;
    }

    public static int obtenerAPS() {
        return aps;
    }

    private void inicializar() {
        sd = new SuperficieDibujo(ancho, alto);
        ventana = new Ventana(titulo, sd);
        ge = new GestorDeEstados();

    }

    private void actualizar() {

        ge.actualizar();
    }

    private void dibujar() {
        sd.dibujar(ge);
    }

}
