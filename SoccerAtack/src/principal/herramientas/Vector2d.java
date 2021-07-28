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
public class Vector2d {

    double x, y;

    public Vector2d() {
        set(0, 0);

    }

    public Vector2d(Vector2d vector) {
        set(vector.x, vector.y);

    }

    public Vector2d(double x, double y) {
        set(x, y);

    }

    public Vector2d add(Vector2d vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }
    public Vector2d sustraer(Vector2d vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2d setY(double y) {
        this.y = y;
        return this;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean equals(Object object){
        if (!(object instanceof Vector2d)) {
            return false;
        }
        
        Vector2d vec =(Vector2d) object;
        if (vec.getX()==this.getX() && vec.getY()==this.getY()) {
            return true;
        }
        
        return false;
    }
}
