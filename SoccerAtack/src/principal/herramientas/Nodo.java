/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.herramientas;

/**
 *
 * @author usuario
 */
public class Nodo {

    public Vector2d tile;
    public Nodo parent;
    public double fCost, gCost, hCost;

    public Nodo(Vector2d tile, Nodo parent, double gCost, double hCost) {
        this.tile = tile;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = this.gCost + this.hCost;
    }

}
