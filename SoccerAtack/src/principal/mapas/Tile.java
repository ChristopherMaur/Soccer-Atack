/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.mapas;

import java.awt.Rectangle;
import principal.sprites.Sprite;

/**
 *
 * @author usuario
 */
public class Tile {

    private final Sprite sprite;
    private boolean solido;

    public Tile(final Sprite sprite) {
        this.sprite = sprite;
        solido = false;
    }

    public Tile(final Sprite sprite, final boolean solido) {
        this.sprite = sprite;
        this.solido = solido;
    }
    //public Tile (final double x, final double y){
    //}

    public Sprite obtenerSprite() {
        return sprite;
    }

  
    public boolean solid() {
        return solido;
    }

    public void establecerSolido(final boolean solido) {
        this.solido = solido;
    }

    public Rectangle obtenerLimites(final int x, final int y) {
        return new Rectangle(x, y, sprite.obtenerAncho(), sprite.obtenerAlto());
    }
}
