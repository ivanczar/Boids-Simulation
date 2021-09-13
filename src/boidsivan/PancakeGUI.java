/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boidsivan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ivan
 */
public class PancakeGUI extends JPanel implements ActionListener, MouseListener {

    private PancakeStack stack;
    private JButton resetButton;
    private DrawPanel drawPanel;
    private JPanel buttons;
    private JButton resolveButton;
    Timer timer;

    public PancakeGUI() {
        super(new BorderLayout());
        stack = new PancakeStack();
        resetButton = new JButton("Reset");
        resolveButton = new JButton("Resolve");
        drawPanel = new DrawPanel();
        buttons = new JPanel();

        buttons.add(resetButton);
        buttons.add(resolveButton);
        add(buttons, BorderLayout.SOUTH);
        add(drawPanel, BorderLayout.CENTER);

        resetButton.addActionListener(this);
        resolveButton.addActionListener(this);
        addMouseListener(this);

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == resetButton) {
            System.out.println("reset");
            stack = new PancakeStack();

        }
        if (source == resolveButton)
        {
//            System.out.println("Resolving");
            stack.pancakeSort();
        }
        drawPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY()); //testing
        // add extra fields to pancake to store and set position and width and the use p.contains(e.getx,e.gety)? 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    private class DrawPanel extends JPanel {

        public DrawPanel() {
            setPreferredSize(new Dimension(700, 500));
            setBackground(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Pancake p;
            final int height = getHeight()/stack.size();      
            int y = getHeight() - height;

            int width = 0;
            int x = getWidth() / 2;

            if (stack.size() != 0) {

                for (int i = 0; i < stack.size(); i++) {

                    p = stack.getPancake(i);
                    
                    

                    width = (p.getSize() * getWidth()) / stack.size();
//                    
                    p.draw(g, x - width / 2, y, width, height);

                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(p.getSize()), (x -width / 2), (height+y));

                    width -= width;
                    y -= height;
                }
            }

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PANCAKEGUI");
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PancakeGUI());
        frame.pack(); // resizes
        // position the frame in the middle of the screen

        Toolkit tk = Toolkit.getDefaultToolkit(); // gets toolkit for this OS
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        frame.setVisible(true);

    }
}
