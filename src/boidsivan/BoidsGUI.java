
package boidsivan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 *
 * @author ivanc
 */


public class BoidsGUI extends JPanel implements ActionListener { // create frame in main and put ballGUI(extends jpanel) into it

    
    private BoidFlock flock;
    
    
    
    private JButton addBoid;
    private JButton removeBoid;
    private JLabel boidCount;
    private JSlider sepWeight;
    private JSlider alignWeight;
    private JSlider cohesionWeight;
    private JSlider radDetect;
    private JLabel boidCounter;
    private DrawPanel drawPanel;
    private Timer timer;

    public BoidsGUI() {
        super(new BorderLayout());

        flock = new BoidFlock();
        
        drawPanel = new DrawPanel();

        addBoid = new JButton("ADD BOID");
        addBoid.addActionListener(this);
        

        removeBoid = new JButton("REMOVE BOID");
        removeBoid.addActionListener(this);
        
        
        boidCount = new JLabel("Boid Count: 0" + this.flock.size());
        
        sepWeight = new JSlider();
        alignWeight = new JSlider();
        cohesionWeight = new JSlider();
        radDetect = new JSlider();
        sepWeight = new JSlider();
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeBoid);
        buttonPanel.add(addBoid);  
        buttonPanel.add(boidCount);
        buttonPanel.add(sepWeight);
        
        
        
        add(drawPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(20, this);
        timer.start();

        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == addBoid) {
            System.out.println("Add a Boid");
            Boid boid = new Boid(flock);
            Thread thread = new Thread(boid);
            thread.start(); // tells thread class to run() the ball class (ball is runable i.e implements runnable)
            flock.addBoid(boid);
        }
        if (source == removeBoid && flock.size() !=0)
        {
            System.out.println("Remove Boid");
            flock.removeBoid();
        }
            
        
        boidCount.setText("Boid Count: " + this.flock.size());
        drawPanel.repaint();
    }
 
    private class DrawPanel extends JPanel {

        public DrawPanel() {
            setPreferredSize(new Dimension(1000, 600));
            setBackground(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); 
            Boid.WORLD_WIDTH = getWidth();
            Boid.WORLD_HEIGHT = getHeight();                                   
            if (flock.size() != 0)
            {
                flock.drawBoids(g);
            }
            
            
        }
    }


public static void main(String[] args){
    JFrame frame = new JFrame("BOIDSGUI");
      // kill all threads when frame closes
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new BoidsGUI());
      frame.pack(); // resizes
      // position the frame in the middle of the screen

      Toolkit tk = Toolkit.getDefaultToolkit(); // gets toolkit for this OS
      Dimension screenDimension = tk.getScreenSize();
      Dimension frameDimension = frame.getSize();
      frame.setLocation((screenDimension.width-frameDimension.width)/2, 
    (screenDimension.height-frameDimension.height)/2);
      frame.setVisible(true); 

    }
}


