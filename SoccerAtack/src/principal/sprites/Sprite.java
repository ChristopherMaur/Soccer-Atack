/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.sprites;

import java.awt.image.BufferedImage;

/**
 *
 * @author usuario
 */
public class Sprite {

    private final BufferedImage imagen;

    private final int ancho;
    private final int alto;

    public Sprite(final BufferedImage imagen) {
        this.imagen = imagen;
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
    }

    public BufferedImage obtenerImagen() {
        return imagen;
    }

    public int obtenerAncho() {
        return ancho;
    }

    public int obtenerAlto() {
        return alto;
    }
}
