/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Ivan
 */
public class Pancake implements Comparable<Pancake> {

    private Color color;
    private int size;
    private boolean selected;

    public Pancake(int size, Color color) {
        Random rand = new Random();
        
        
        
        this.size = rand.nextInt(20 -1);
        
        for (int i = 0; i < 3; i++) {
            int rValue = rand.nextInt(255 - 2);
            int gValue = rand.nextInt(255 - 2);
            int bValue = rand.nextInt(255 - 2);

            this.color = new Color(rValue, gValue, bValue);

        }

    }

    @Override
    public int compareTo(Pancake other) {
        if (this.size == other.size) {
            return 0;
        }
        if (this.size < other.size) {
            return -1;
        } else // i.e this.size > other.size
        {
            return 1;
        }

    }

    public int getSize() {
        return this.size;
    }

    public void highlight(boolean selected) {
        this.selected = selected;
    }

    public void draw(Graphics g) {

        //if selected drwa a black rectangle around to simulate select
    }

}
