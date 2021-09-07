package boidsivan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ivanc
 */
public class Boid implements Runnable {

    private Vect pos;
    private Vect mov;
    private boolean isAlive;
    private Color[] colour;
    private BoidFlock flock;

    // static means variable changes are applied to all instances of boid object
    public static int WORLD_WIDTH, WORLD_HEIGHT;
    public static int BOID_SIZE;

    public static float MAX_SPEED = 5;

    public static int RADIUS_DETECTION = 10; // boids wont be drawn if this isnt intiiated????
    public static float COHESION_WEIGHT;
    public static float SEPARATION_WEIGHT;
    public static float ALIGNMENT_WEIGHT;

    // constructor
    public Boid(BoidFlock flock) {

        this.flock = flock;  // Correct?

        Random rand = new Random();

        pos = new Vect((WORLD_WIDTH / 2), (WORLD_HEIGHT / 2));
        mov = new Vect(-rand.nextDouble() + 1, -rand.nextDouble() + 1);

        this.BOID_SIZE = 15;

        this.colour = new Color[3];
        for (int i = 0; i < 3; i++) {
            int rValue = rand.nextInt(255 - 2);
            int gValue = rand.nextInt(255 - 2);
            int bValue = rand.nextInt(255 - 2);

            this.colour[i] = new Color(rValue, gValue, bValue);

        }

    }

    public Vect alignment() {
        Vect vA; // behaviour vectr
        Vect vAvg; // average movement vector
        double vAvgX = 0; // average movement vector x component
        double vAvgY = 0; // average movement vector y component

        List<Boid> neighbours = flock.getNeighbours(this); // get boids neighbours

        for (Boid neighbour : neighbours) // iterate through neighbours
        {
            vAvgX += neighbour.getMovementX(); // add all x components of movement
            vAvgY += neighbour.getMovementY(); // add all y components of movement
        }
        // set movement vector using x and y components
        vAvg = new Vect(vAvgX / neighbours.size(), vAvgY / neighbours.size());

        vA = Vect.sub(vAvg, this.mov); // get behaviour vector as per pdf formula
        //  System.out.println(vA); // for testing
        vA.mult(Boid.ALIGNMENT_WEIGHT);
        return vA;
    }

    public Vect cohesion() {
        Vect vC; // behaviour vectr
        Vect pCentre; // average centre of mass (CoM) vector
        double pCentreX = 0; // average centre of mass vector x component
        double pCentreY = 0; // average centre of mass vector y component

        List<Boid> neighbours = flock.getNeighbours(this); // get boids neighbours

        for (Boid neighbour : neighbours) // iterate through neighbours
        {
            pCentreX += neighbour.getPositionX(); // add all x components of CoM
            pCentreY += neighbour.getPositionY(); // add all y components of CoM
        }
        // set CoM vector using x and y components
        pCentre = new Vect(pCentreX / neighbours.size(), pCentreY / neighbours.size());

        vC = Vect.sub(pCentre, this.pos); // get behaviour vector as per pdf formula
        //  System.out.println(vA); // for testing
        vC.mult(Boid.COHESION_WEIGHT *0.1); // HOW ELSE DO I GET FLOAT VALUES IF SLIDER IS INT????????
        return vC;
    }

    public void updateMov() {
        Vect vA = alignment();
        Vect vC = cohesion();

        if(vA.mag() > Boid.MAX_SPEED)
        {
            vA.normalize(); // convert to unit vector
            vA.mult(MAX_SPEED); // scale
        }
        else if(vC.mag() > Boid.MAX_SPEED)
        {
            vC.normalize();
            vA.mult(MAX_SPEED);
        }
        //  SCALE SEPARATION VECTOR!!!!!!!!!!
        
        
        
        //// update movement with behaviour vector
        this.mov.setX(this.mov.getX() + vA.getX() + vC.getX()); //xcomponent of vectors
        this.mov.setY(this.mov.getY() + vA.getY() + vC.getY()); //ycomponent of vectors
    }

    public void requestStop() {
        isAlive = false;
    }

    // thread run
    public void run() {

        isAlive = true;
        while (isAlive) {

            // change position of boid
            this.pos.setX(this.pos.getX() + this.mov.getX());
            this.pos.setY(this.pos.getY() + this.mov.getY());


            updateMov();

            if (this.pos.getX() >= WORLD_WIDTH - BOID_SIZE || this.pos.getX() <= 0) {

                this.mov.setX(-this.mov.getX());
            }
            if (this.pos.getY() >= WORLD_HEIGHT - BOID_SIZE || this.pos.getY() <= 0) {
                this.mov.setY(-this.mov.getY());
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }

        }
    }

    //getters
    public double getPositionX() {

        return this.pos.getX();
    }

    public double getPositionY() {

        return this.pos.getY();
    }

    public double getMovementX() {

        return this.mov.getX();

    }

    public double getMovementY() {

        return this.mov.getY();

    }

    // setters
    public void setPostionX(double x) {
        this.pos.setX(x);
    }

    public void setPostionY(double y) {
        this.pos.setY(y);
    }

    public void setMovementX(double dx) {
        this.mov.setX(dx);
    }

    public void setMovementY(double dy) {
        this.mov.setY(dy);
    }

    // draw method
    public void draw(Graphics g) {
        double speed = sqrt(pow(getMovementX(), 2) + (pow(getMovementY(), 2)));
        double velX = ((BOID_SIZE * getMovementX()) / (2 * speed));
        double velY = ((BOID_SIZE * getMovementY()) / (2 * speed));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(colour[0]);
        g2d.drawLine((int) getPositionX(), (int) getPositionY(), (int) (getPositionX() - 2 * velX), (int) (getPositionY() - 2 * velY)); // draws long centre line
        g2d.setColor(colour[1]);
        g2d.drawLine((int) getPositionX(), (int) getPositionY(), (int) ((getPositionX() - velX + velY)), (int) ((getPositionY() - velX - velY))); // draws left arrowhead
        g2d.setColor(colour[2]);
        g2d.drawLine((int) getPositionX(), (int) getPositionY(), (int) ((getPositionX() - velX - velY)), (int) ((getPositionY() + velX - velY))); // draws right arrowhead
    }

}
