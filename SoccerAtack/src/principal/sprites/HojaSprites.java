/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.sprites;

import java.awt.image.BufferedImage;
import principal.herramientas.CargadorDeRecursos;

/**
 *
 * @author usuario
 */
public class HojaSprites {

    final private int anchoHojaEnPixeles;
    final private int altoHojaEnPixeles;

    final private int anchoHojaEnSprites;
    final private int altoHojaEnSprites;

    final private int anchoSprite;
    final private int altoSprite;

    final private Sprite[] sprites;

    public HojaSprites(final String ruta, final int tamanioSprite, final boolean HojaOpaca) {
        final BufferedImage imagen;
        if (HojaOpaca) {
            imagen = CargadorDeRecursos.cargarImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorDeRecursos.cargarImagenCompatibleTraslucida(ruta);
        }

        anchoHojaEnPixeles = imagen.getWidth();
        altoHojaEnPixeles = imagen.getHeight();

        anchoHojaEnSprites = anchoHojaEnPixeles / tamanioSprite;
        altoHojaEnSprites = altoHojaEnPixeles / tamanioSprite;

        anchoSprite = tamanioSprite;
        altoSprite = tamanioSprite;

        sprites = new Sprite[anchoHojaEnSprites * altoHojaEnSprites];

        rellenarSpritesDesdeLaImagen(imagen);
    }

    public HojaSprites(final String ruta, final int anchoSprite,
            final int altoSprite, final boolean HojaOpaca) {

        final BufferedImage imagen;
        if (HojaOpaca) {
            imagen = CargadorDeRecursos.cargarImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorDeRecursos.cargarImagenCompatibleTraslucida(ruta);
        }

        anchoHojaEnPixeles = imagen.getWidth();
        altoHojaEnPixeles = imagen.getHeight();

        anchoHojaEnSprites = anchoHojaEnPixeles / anchoSprite;
        altoHojaEnSprites = altoHojaEnPixeles / altoSprite;

        this.anchoSprite = anchoSprite;
        this.altoSprite = altoSprite;

        sprites = new Sprite[anchoHojaEnSprites * altoHojaEnSprites];

        rellenarSpritesDesdeLaImagen(imagen);
    }

    private void rellenarSpritesDesdeLaImagen(final BufferedImage imagen) {
        for (int y = 0; y < altoHojaEnSprites; y++) {
            for (int x = 0; x < anchoHojaEnSprites; x++) {
                final int posicionX = x * anchoSprite;
                final int posicionY = y * altoSprite;

                sprites[x + y * anchoHojaEnSprites] = new Sprite(imagen.getSubimage(posicionX,
                        posicionY, anchoSprite, altoSprite));
            }
        }
    }

    public Sprite obtenerSprite(final int indice) {
        return sprites[indice];
    }

    public Sprite obtenerSprite(final int x, final int y) {
        return sprites[x + y * anchoHojaEnSprites];
    }
    public Sprite obtenerSprite(final int x, final int y,final int lado ) {
        return sprites[x + y * lado];
    }
}
