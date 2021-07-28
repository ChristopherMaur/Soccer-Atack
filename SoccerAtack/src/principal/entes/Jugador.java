/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

/**
 *
 * @author usuario
 */
public class Jugador {

    private double posicionX;
    private double posicionY;
    private double velocidad = 4;
    private int direccion;

    private int animacion;
    private int estado;

    private HojaSprites hs;
    private BufferedImage imagenActual;
    private boolean enMovimiento;
    
    private int ANCHO_ALTO_JUGADOR = 32;

    private final Rectangle LIMITE_ARRIBA
            = new Rectangle(Constantes.CENTRO_VENTANA_X + 45,
                    Constantes.CENTRO_VENTANA_Y + 100, ANCHO_ALTO_JUGADOR, 1);

    private final Rectangle LIMITE_ABAJO
            = new Rectangle(Constantes.CENTRO_VENTANA_X + 45,
                    Constantes.CENTRO_VENTANA_Y + ANCHO_ALTO_JUGADOR + 100, ANCHO_ALTO_JUGADOR, 1);

    private final Rectangle LIMITE_IZQUIERDA
            = new Rectangle(Constantes.CENTRO_VENTANA_X + 45,
                    Constantes.CENTRO_VENTANA_Y + 100, 1, ANCHO_ALTO_JUGADOR);

    private final Rectangle LIMITE_DERECHA
            = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_ALTO_JUGADOR + 45,
                    Constantes.CENTRO_VENTANA_Y + 100, 1, ANCHO_ALTO_JUGADOR);

    public int resistencia = 600;
    private int recuperacion = 0;
    private final int RECUPERACION_MAXIMA = 60;
    private boolean recuperado = true;

    public Jugador() {
        posicionX = ElementosPrincipales.mapa.obtenerPocisionInicial().x;
        posicionY = ElementosPrincipales.mapa.obtenerPocisionInicial().y;
        hs = new HojaSprites("/imagenes/HojasPersonajes/jugador1.png", 150, false);
        imagenActual = hs.obtenerSprite(0, 3).obtenerImagen();
    }

    public void actualizar() {
        gestionarVelocidadResistencia();
        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion();
        animar();
    }

    public void dibujar(Graphics g) {
        final int CentroX = (Constantes.ANCHO_MUNDO_VIRTUAL / 2) - Constantes.LADO_SPRITE / 2;
        final int CentroY = (Constantes.ALTO_MUNDO_VIRTUAL / 2) - Constantes.LADO_SPRITE / 2;

        DibujoDebug.dibujarImagen(g, imagenActual, CentroX, CentroY);
        //g.drawImage(imagenActual, CentroX, CentroY, null);
        DibujoDebug.dibujarString(g, "Resistencia: " + resistencia, 20, 40, Color.green);
        //g.setColor(Color.green);
        //g.drawString("Resistencia: " + resistencia, 20, 40);
        
        
        if (GestorControles.teclado.debug) {
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height, Color.GREEN);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height, Color.GREEN);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height, Color.GREEN);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height, Color.GREEN);
        }
        //dibujar area de colision jugador
        /*
        g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height);
        g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height);
        g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height);
        g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height);
         */
    }

    private void mover(final int velocidadX, final int velocidadY) {
        enMovimiento = true;

        cambiarDireccion(velocidadX, velocidadY);

        if (!fueraMapa(velocidadX, velocidadY)) {

            if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
                posicionX += velocidadX * velocidad;
                restarResistencia();
            }
            if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
                posicionX += velocidadX * velocidad;
                restarResistencia();
            }
            if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
                posicionY += velocidadY * velocidad;
                restarResistencia();
            }
            if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
                posicionY += velocidadY * velocidad;
                restarResistencia();
            }
        }
    }

    public Rectangle obtener_LIMITE_IZQUIERDA() {
        return LIMITE_IZQUIERDA;
    }

    private void restarResistencia() {
        if (GestorControles.teclado.corriendo && resistencia > 0) {
            resistencia--;
        }
    }

    private void gestionarVelocidadResistencia() {
        if (GestorControles.teclado.corriendo && resistencia > 0) {
            velocidad = 8;
            recuperado = false;
            recuperacion = 0;
        } else {
            velocidad = 4;
            if (!recuperado && recuperacion < RECUPERACION_MAXIMA) {
                recuperacion++;
            }
            if (recuperacion == RECUPERACION_MAXIMA && resistencia < 600) {
                resistencia++;
            }
        }

    }

    private boolean fueraMapa(final int velocidadX, final int velocidadY) {
        int nextX = (int) posicionX + velocidadX * (int) velocidad;
        int nextY = (int) posicionY + velocidadY * (int) velocidad;
        Rectangle bordesMapa = ElementosPrincipales.mapa.obtenerBordes(nextX, nextY);

        final boolean fuera;
        if (LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_ARRIBA.intersects(bordesMapa)
                || LIMITE_DERECHA.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa)) {
            fuera = false;
        } else {
            fuera = true;
        }
        return fuera;
    }

    private void cambiarAnimacionEstado() {
        if (animacion < 60) {
            animacion++;
        } else {
            animacion = 0;

        }

        if (animacion < 60 && animacion > 50) {
            estado = 1;
        } else if (animacion < 50 && animacion > 40) {
            estado = 2;
        } else if (animacion < 40 && animacion > 30) {
            estado = 3;
        } else if (animacion < 30 && animacion > 20) {
            estado = 4;
        } else if (animacion < 20 && animacion > 10) {
            estado = 5;
        } else if (animacion < 10 && animacion > 1) {
            estado = 6;
        }
    }

    private void animar() {

        if (!enMovimiento) {
            estado = 0;
            animacion = 0;
            imagenActual = hs.obtenerSprite(direccion, estado).obtenerImagen();
        } else {
            imagenActual = hs.obtenerSprite(direccion, estado).obtenerImagen();
        }
    }

    private void determinarDireccion() {
        final int velocidadX = evaluarVelocidadX();
        final int velocidadY = evaluarVelocidadY();
        if (velocidadX == 0 && velocidadY == 0) {
            return;
        }

        if ((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
            mover(velocidadX, velocidadY);
        } else {
            //izq -arriba
            if (velocidadX == -1 && velocidadY == -1) {
                if (GestorControles.teclado.izquierda.obtenerUltimaPulsasion()
                        > GestorControles.teclado.arriba.obtenerUltimaPulsasion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
            //izq-abajo
            if (velocidadX == -1 && velocidadY == 1) {
                if (GestorControles.teclado.izquierda.obtenerUltimaPulsasion()
                        > GestorControles.teclado.abajo.obtenerUltimaPulsasion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
            //derecha- arriba
            if (velocidadX == 1 && velocidadY == -1) {
                if (GestorControles.teclado.derecha.obtenerUltimaPulsasion()
                        > GestorControles.teclado.arriba.obtenerUltimaPulsasion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
            //derecha-abajo
            if (velocidadX == 1 && velocidadY == 1) {
                if (GestorControles.teclado.derecha.obtenerUltimaPulsasion()
                        > GestorControles.teclado.abajo.obtenerUltimaPulsasion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
        }

    }

    private boolean enColisionArriba(int velocidadY) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
            if (LIMITE_ARRIBA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean enColisionAbajo(int velocidadY) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
            if (LIMITE_ABAJO.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean enColisionIzquierda(int velocidadX) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
            if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean enColisionDerecha(int velocidadX) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
            if (LIMITE_DERECHA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private void cambiarDireccion(final int velocidadX, final int velocidadY) {
        if (velocidadX == -1) {
            direccion = 3;
        } else if (velocidadX == 1) {
            direccion = 2;
        }

        if (velocidadY == -1) {
            direccion = 1;
        } else if (velocidadY == 1) {
            direccion = 0;
        }

    }

    private int evaluarVelocidadX() {
        int velociadX = 0;
        if (GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
            velociadX = -1;
        } else if (GestorControles.teclado.derecha.estaPulsada() && !GestorControles.teclado.izquierda.estaPulsada()) {
            velociadX = 1;
        }
        return velociadX;
    }

    private int evaluarVelocidadY() {
        int velociadY = 0;
        if (GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
            velociadY = -1;
        } else if (GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.arriba.estaPulsada()) {
            velociadY = 1;
        }
        return velociadY;
    }

    public void establecerPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public void establecerPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    public double obtenerPosicionX() {
        return posicionX;
    }

    public double obtenerPosicionY() {
        return posicionY;
    }

    public int obtenerPosicionXint() {
        return (int) posicionX;
    }

    public int obtenerPosicionYint() {
        return (int) posicionY;
    }

    public int obtenerAncho_alto() {
        return ANCHO_ALTO_JUGADOR;
    }

    

}
