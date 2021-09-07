/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 * @author Ivan
 */
public class Vect {

    private double x;
    private double y;

    public Vect(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    void add(Vect v) {
        x += v.x;
        y += v.y;
    }

    void sub(Vect v) {
        x -= v.x;
        y -= v.y;
    }

    void div(double val) {
        x /= val;
        y /= val;
    }

    void mult(double val) {
        x *= val;
        y *= val;
    }

    double mag() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }
}
