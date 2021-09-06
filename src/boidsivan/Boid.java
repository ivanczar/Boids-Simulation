
package boidsivan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Random;

/**
 *
 * @author ivanc
 */
public class Boid implements Runnable {

    private double x ,y;
    private double dx, dy;
    private boolean isAlive;
    private Color[] colour;
    private BoidFlock flock;
    
    // static means variable changes are applied to all instances of boid object
    public static int WORLD_WIDTH, WORLD_HEIGHT;
    public static int BOID_SIZE;
    public static int RADIUS_DETECTION = 10; // DO WE INITIALIZE THESE VARIABLES??
    public static float COHESION_WEIGHT;
    public static float SEPARATION_WEIGHT;
    public static float ALIGNMENT_WEIGHT;
    public static float MAX_SPEED;
    
    // constructor
    public Boid(BoidFlock flock) 
    {
        
        this.flock = new BoidFlock();  // Correct?
        
        Random rand = new Random();
        
        x =(WORLD_WIDTH / 2); 
        y =(WORLD_HEIGHT / 2);
        setMovementX(rand.nextDouble() + 1);// +1 so boid cant be stationary
        setMovementY(rand.nextDouble() + 1);
        this.BOID_SIZE = 15;
        
        this.colour = new Color[3];
        for (int i =0; i < 3; i++)
        {
            int rValue = rand.nextInt(255 - 2);
            int gValue = rand.nextInt(255 - 2);
            int bValue = rand.nextInt(255 - 2);
         

            this.colour[i] = new Color(rValue, gValue, bValue);

            
        }

       
    }
    
    public void requestStop()
    {
        //??? is_alive = false???
    }
    
    // thread run
    public void run()
    {
        
        isAlive = true;
        while (isAlive) {
            x += dx; // GENERATE RANDOM NOISE????
            y += dy;

            if (x >= WORLD_WIDTH - BOID_SIZE || x <= 0) {
                
                dx = -dx;
            }
            if (y >= WORLD_HEIGHT - BOID_SIZE || y <= 0) {
                dy = -dy;
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }
    
    //getters
    public double getPositionX(){
        
        return this.x;
    }
    
    public double getPositionY(){
        
        return this.y;
    }
        
    public double getMovementX(){
        
        return this.dx;
        
    }
    
    public double getMovementY(){
        
        return this.dy;
        
    }
    
    // setters
    public void setPostionX(double x)
    {
        this.x = x;
    }
    
    public void setPostionY(double y)
    {
        this.y = y;
    }
    
    public void setMovementX(double dx)
    {
        this.dx = dx;
    }
    
    public void setMovementY(double dy)
    {
        this.dy=dy;
    }
    
    // draw method
    public void draw(Graphics g)
    {
        double speed = sqrt(pow(getMovementX(),2) + (pow(getMovementY(),2)));
        double velX = ((BOID_SIZE*getMovementX())/(2*speed));
        double velY = ((BOID_SIZE*getMovementY())/(2*speed));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(colour[0]);
        g2d.drawLine((int)getPositionX(), (int)getPositionY(), (int)(getPositionX()-2*velX), (int)(getPositionY() - 2*velY)); // draws long centre line
        g2d.setColor(colour[1]);
        g2d.drawLine((int)getPositionX(), (int)getPositionY(), (int)((getPositionX()-velX+velY)), (int)((getPositionY() - velX - velY))); // draws left arrowhead
        g2d.setColor(colour[2]);
        g2d.drawLine((int)getPositionX(), (int)getPositionY(), (int)((getPositionX()-velX-velY)), (int)((getPositionY() + velX - velY))); // draws right arrowhead
    }
    
    
    
    
    
    
}
