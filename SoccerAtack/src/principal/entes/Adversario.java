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
import java.util.List;
import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.herramientas.Nodo;
import principal.sprites.HojaSprites;

/**
 *
 * @author usuario
 */
public class Adversario {

    private int idAdversario;
    private int direccion;

    private double posicionX;
    private double posicionY;

    private String nombre;
    private int velocidad = 4;

    private int animacion;
    private int estado;
    private boolean enMovimiento;

    private HojaSprites hs;
    private BufferedImage imagenActual;

    private int ANCHO_ALTO_JUGADOR = 32;

    private Rectangle LIMITE_ARRIBA;
    private Rectangle LIMITE_ABAJO;
    private Rectangle LIMITE_IZQUIERDA;
    private Rectangle LIMITE_DERECHA;

    private int xa = 0;
    private int ya = 0;
    private List<Nodo> path = null;
    private int time = 0;
    private boolean walking;

    //public Rectangle X;
    public Adversario(final int idAdversrio, final String nombre) {
        this.idAdversario = idAdversrio;
        this.posicionX = ElementosPrincipales.mapa.obtenerPocisionInicialAdversario().x;
        this.posicionY = ElementosPrincipales.mapa.obtenerPocisionInicialAdversario().y;
        this.nombre = nombre;
        hs = new HojaSprites("/imagenes/HojasPersonajes/jugador1.png", 150, false);
        imagenActual = hs.obtenerSprite(0, 3).obtenerImagen();
    }

    public void actualizar() {
        time++;

        LIMITE_ARRIBA = new Rectangle(obtenerPuntoX(), obtenerPuntoY(), ANCHO_ALTO_JUGADOR, 1);
        LIMITE_ABAJO = new Rectangle(obtenerPuntoX(), obtenerPuntoY() + ANCHO_ALTO_JUGADOR, ANCHO_ALTO_JUGADOR, 1);
        LIMITE_IZQUIERDA = new Rectangle(obtenerPuntoX(), obtenerPuntoY(), 1, ANCHO_ALTO_JUGADOR);
        LIMITE_DERECHA = new Rectangle(obtenerPuntoX() + ANCHO_ALTO_JUGADOR, obtenerPuntoY(), 1, ANCHO_ALTO_JUGADOR);

        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion('R');
        animar();
    }
    int xe = 0;
    int ze = 1;
/*
    public void move() {

        int px = (int) ElementosPrincipales.jugador.obtenerPosicionX();
        int py = (int) ElementosPrincipales.jugador.obtenerPosicionY();

        Vector2d Start = new Vector2d((int)this.posicionX / 16, (int)this.posicionY / 16);
        Vector2d Destination = new Vector2d(px / 16, py / 16);

        if (time % 3 == 0) {
            path = ElementosPrincipales.mapa.buscarRuta(Start, Destination);
        }
        if (path != null) {
            if (path.size() > 0) {
                Vector2d vec = path.get(path.size() - 1).tile;
                if (posicionX < vec.getX() * 16) {
                    xa++;
                }
                if (posicionX > vec.getX() * 16) {
                    xa--;
                }
                if (posicionY < vec.getY() * 16) {
                    ya++;
                }
                if (posicionY > vec.getY() * 16) {
                    ya--;
                }

            }
        }

        if (xa != 0 || ya != 0) {
            mover(xa, ya);
            walking = true;
        } else {
            walking = false;
        }

    }
*/
    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
        DibujoDebug.dibujarImagen(g, imagenActual, puntoX, puntoY);
        //DibujoDebug.dibujarRectanguloContorno(g, X, Color.red);

        if (GestorControles.teclado.debug) {
            //DibujoDebug.dibujarRectanguloContorno(g, obtenerArea(), Color.red);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height, Color.GREEN);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height, Color.GREEN);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height, Color.GREEN);
            DibujoDebug.dibujarRectangulContorno(g, LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height, Color.GREEN);
        }

    }

    private void determinarDireccion(char Camino) {
        final int velocidadX = evaluarVelocidadX(Camino);
        final int velocidadY = evaluarVelocidadY(Camino);
        if (velocidadX == 0 && velocidadY == 0) {
            return;
        }
        if (velocidadX == -1 || velocidadX == 1) {
            mover(velocidadX, 0);
        }

        if (velocidadY == -1 || velocidadY == 1) {
            mover(0, velocidadY);
        }

    }

    private int evaluarVelocidadX(char Camino) {
        int velociadX = 0;
        if (Camino == 'L') {
            velociadX = -1;
        } else if (Camino == 'R') {
            velociadX = 1;
        }
        return velociadX;
    }

    private int evaluarVelocidadY(char Camino) {
        int velociadY = 0;
        if (Camino == 'U') {
            velociadY = -1;
        } else if (Camino == 'D') {
            velociadY = 1;
        }
        return velociadY;
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

    public void mover(final int velocidadX, final int velocidadY) {
        enMovimiento = true;

        cambiarDireccion(velocidadX, velocidadY);

        if (velocidadX == 1 && !enColisionDerecha(velocidad)) {

            posicionX += velocidadX * velocidad;

        }
        if (velocidadX == -1 && !enColisionIzquierda(velocidad)) {

            posicionX -= velocidadX * velocidad;

        }
        if (velocidadY == 1 && !enColisionAbajo(velocidad)) {

            posicionY -= velocidadY * velocidad;

        }
        if (velocidadY == -1 && !enColisionArriba(velocidad)) {

            posicionY += velocidadY * velocidad;

        }
    }

    private boolean enColisionArriba(int velocidadY) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) velocidad - 2 * (int) velocidad;

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
            int origenY = area.y + velocidadY * (int) velocidad - 5 * (int) velocidad;

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

            int origenX = area.x + velocidadX * (int) velocidad + 5 * (int) velocidad;
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

            int origenX = area.x + velocidadX * (int) velocidad - 5 * (int) velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
            if (LIMITE_DERECHA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean fueraMapa(final int velocidadX, final int velocidadY) {
        int nextX = (int) posicionX + velocidadX * (int) velocidad;
        int nextY = (int) posicionY + velocidadY * (int) velocidad;
        Rectangle bordesMapa = obtenerBordesAdversario(nextX, nextY);

        final boolean fuera;
        if (LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_ARRIBA.intersects(bordesMapa)
                || LIMITE_DERECHA.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa)) {
            fuera = false;
        } else {
            fuera = true;
        }
        return fuera;
    }

    public Rectangle obtenerBordesAdversario(final int posicionX, final int posicionY) {

        int x = (int) posicionX - (int) ElementosPrincipales.jugador.obtenerPosicionX() + Constantes.MARGEN_X + 65;
        int y = (int) posicionY - (int) ElementosPrincipales.jugador.obtenerPosicionY() + Constantes.MARGEN_Y + 115;
        int ancho = ElementosPrincipales.mapa.obtenerAncho() * 32;
        int alto = ElementosPrincipales.mapa.obtenerAlto() * 32;
        return new Rectangle(x, y, ancho, alto);
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

    public void establecerPosicion(final double posicionX, final double posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public double obtenerPosicionX() {
        return posicionX;
    }

    public double obtenerPosicionY() {
        return posicionY;
    }

    public double obtenerPosicionXint() {
        return (int) posicionX;
    }

    public double obtenerPosicionYint() {
        return (int) posicionY;
    }

    public int obtenerPuntoX() {
        return (int) posicionX - (int) ElementosPrincipales.jugador.obtenerPosicionX() + Constantes.MARGEN_X + 65;
    }

    public int obtenerPuntoY() {
        return (int) posicionY - (int) ElementosPrincipales.jugador.obtenerPosicionY() + Constantes.MARGEN_Y + 115;
    }

    public double obtenerIdAdversario() {
        return idAdversario;
    }

    public Rectangle obtenerArea() {
        final int puntoX = (int) posicionX - (int) ElementosPrincipales.jugador.obtenerPosicionX() + Constantes.MARGEN_X + 65;
        final int puntoY = (int) posicionY - (int) ElementosPrincipales.jugador.obtenerPosicionY() + Constantes.MARGEN_Y + 115;

        return new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
    }

}
