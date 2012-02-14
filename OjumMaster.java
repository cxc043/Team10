/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ojummaster;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JApplet;
import java.awt.event.*;
import java.util.*;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;

/**
 *
 * @author txj014
 */
public class OjumMaster extends JApplet implements Runnable, MouseMotionListener, MouseListener
{
	private int speed; //speed of thread
        private int mousex;
        private int mousey;
        private int radius;
        private boolean mousemoved;
        Graphics G;
        
        private Shape shape1, shape2;
        private Area areaOne, areaTwo, test;
       
        static final int X = 380, Y = 250;
        static BufferedImage I;

        
        private Dimension size; //used to set the size of applet
        
	Thread th; // thread
        Random randomGenerator = new Random(); //generator for color, position, & speed

    @Override
	public void init ()
	{
            setBackground(Color.black);
            setSize(380, 600); //set size for testing, not needed for html
            size = getSize(); //size for testing
            speed = 30; //sets delay of thread, higher number is easier
            I = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
            mousemoved = false;
            
            shape1 = new Ellipse2D.Double(25, 125, 350, 425);
            shape2 = new Ellipse2D.Double(50, 150, 300, 375);
            
           areaOne = new Area(shape1);
           test = new Area(shape1);
           areaTwo = new Area(shape2);

           // player = new GameStats (); //give player lives, and initialize clicked cubes to 0
//        for (int i = 0; i<size.width; ++i) for(int j=0; j<size.height; ++j)
//           I.setRGB(i, j, 0); // Black
//        for (int i = 190; i<250; ++i) for(int j=300; j<360; ++j)
//           I.setRGB(i, j, 0x10FFFF); // Blue
//        WritableRaster wr = I.getRaster();
        //I.setRGB(20, 20, 0xffc068);
           // newObjects(); //create 2 random objects for start of game
                
            addMouseMotionListener(this); //add listener
            addMouseListener(this);
            if (th == null)
            {
                th = new Thread(this);
                th.start (); //start thread running
            }
	}

        
        Color getRandColor() {
            return new Color(randomGenerator.nextInt(256), randomGenerator.nextInt(256), randomGenerator.nextInt(256));
        }
        
        public void updateColor() {
            int x = getMouseX(); //get x if clicked
            int y = getMouseY(); //get y if clicked
//            int color;
//            if (mousemoved)
//            {
//                for (int i = 0; i<20; ++i) for(int j=0; j<20; ++j)
//                {
//                    color = I.getRGB(x+i, y+j);
//                    I.setRGB(x+i, y+j, color-100);
//                }
//            }
        if (mousemoved)
            {
            if (radius < 150)
                radius++;
            else
                radius = 0;
            
            repaint();
            }
            
        }

        
        public void setMouseX(int x){
            mousex = x;
        }
        
        public void setMouseY(int y){
            mousey = y;
        }
        
        public int getMouseX(){
            return mousex;
        }
        
        public int getMouseY(){
            return mousey;
        }

    @Override
        public void mouseClicked(MouseEvent e)
        {

        }
	public void mouseMoved(MouseEvent e)
	{
            setMouseX(e.getX());
            setMouseY(e.getY()); 
            mousemoved = true;
            if (radius > 8)
                radius--;
	}

	public void mouseDragged(MouseEvent e)
	{
            
	}

    @Override
	public void run ()
	{
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

            while (true)
            {
                
		//repaint();
		try
		{
                    // Stop 30 based on init set of 30 for speed
                    Thread.sleep (speed);
                    updateColor();
		}
		catch (InterruptedException ex)
		{
                    //caught ya
                }

		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            }
	}

    @Override
	public void paint (Graphics g)
	{
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.YELLOW);
            
            
            test.subtract(areaTwo);
            if(!test.contains(mousex, mousey))
            {
            if(radius %2 == 1)
            g.fillOval(getMouseX()-radius/2, getMouseY()-radius/2, radius, radius);
            }
            
            g2.draw(areaOne);
            g2.draw(areaTwo);
            g2.setColor(Color.white);
            g2.fill(test);
            //g.drawImage(I, 0, 0, Color.red, null);
	}

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}