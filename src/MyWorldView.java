import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*; 
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MyWorldView extends JPanel { 
// BEGIN declarations to use metric coordinate system (not pixels)
   public static int WIDTH = 900;  // in pixels
   public static int HEIGHT = 150; // in pixels
   public static int X_ORIGEN = (int)(WIDTH*0.1); // MyWorld space origen (x,y) will be on
   public static int Y_ORIGEN = (int)(HEIGHT*0.9); // (X_ORIGEN,Y_ORIGEN) of the panel space.
   public static AffineTransform SPACE_TRANSFORM;  // transforms (x,y) in (X,Y) of panel.
   public static AffineTransform SPACE_INVERSE_TRANSFORM; // transforms (X,Y) in (x,y) of my world.
   public static Line2D.Double X_AXIS;  // lines to draw my world axes (singular axis, plural axes).
   public static Line2D.Double Y_AXIS;
   private static double AXES_SCALE = 200.0;  // 1 [m] equals 100 [pixels]
   
   private Clip mCollisionClip = null;
   
   static {
       SPACE_TRANSFORM = AffineTransform.getTranslateInstance(X_ORIGEN, Y_ORIGEN);
       SPACE_TRANSFORM.scale(AXES_SCALE,-AXES_SCALE);  // it also inverts direction of y-coordinate 
       try {
          SPACE_INVERSE_TRANSFORM = SPACE_TRANSFORM.createInverse();
       }catch (NoninvertibleTransformException e){ 
          System.exit(0);
       }
       X_AXIS = new Line2D.Double(-0.2,0, 4.0,0);  // each axis length is 3 [m] from origen.
       Y_AXIS = new Line2D.Double(0, -0.1, 0, 0.1);       
   }
// END declarations to use metric coordinate system (not pixels)

   private MyWorld world;

   private MouseListener mListener;
   private String mTitle;

   public MyWorldView(MyWorld w){
      world = w;
      mListener = new MouseListener(w);
      mTitle = new String("Not initialized");
      addMouseMotionListener(mListener);
      addMouseListener(mListener);
      //Vemos si se gatilla un switching de elementos bajo el mouse
      //Sin ser focusable no capta eventos de teclado
      setFocusable(true);
      requestFocusInWindow();
      addKeyListener(new KeyAdapter() {
    	@Override  
        public void keyPressed(KeyEvent e)
        {
          if(e.getKeyCode() == KeyEvent.VK_N || e.getKeyCode() == KeyEvent.VK_SPACE)
              mListener.switchSelection();
        }
      });
   }

   public void elementAdded(PhysicsElement e) {
     
   }

   public void repaintView(){
     repaint();
   }
   public void paintComponent(Graphics g){
      Graphics2D g2 = (Graphics2D)g;
      super.paintComponent(g); // it paints the background
      g2.drawString(mTitle, WIDTH/4, 30);
      g2.transform(SPACE_TRANSFORM);
      g2.setStroke(new BasicStroke(0.02f));
      g2.draw(X_AXIS);
      g2.draw(Y_AXIS);

      ArrayList<PhysicsElement> l = world.getPhysicsElements();   
      for (PhysicsElement e : l) {
        e.updateView(g2);
      }
   }

// other methods.

   public void enableMouseListener(){
      addMouseMotionListener(mListener);
      addMouseListener(mListener);         
   }
   public void disableMouseListener(){
      removeMouseMotionListener(mListener);
      removeMouseListener(mListener);
   }

public void setCollisionClip(Clip clipCollision) {
	mCollisionClip = clipCollision;
}

public void playCollisionClip() {
	if (mCollisionClip != null) {
		mCollisionClip.start();
	}
}

public void setTitle(String title) {
	mTitle = title;
}

}