import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class MouseListener extends MouseAdapter {
   private MyWorld world;
   private ArrayList<PhysicsElement> elementsAtPos;
   private PhysicsElement currentElement;
   private int currentElementIndex;
   private final double TOLERANCE = 0.05;

   private void setCurrentElement(PhysicsElement e) {
      if (currentElement != null) {
         currentElement.setReleased();
         currentElement = null;
      }
      if (e != null) { 
         currentElement = e;
         currentElement.setSelected();
         
         if (currentElement instanceof Ball) {
            System.out.println("BallSelected");
         }    
         else if (currentElement instanceof FixedHook){
           System.out.println("FixedHook Selected");
         }
         else if (currentElement instanceof Spring){     
            System.out.println("Spring Selected");
         }
      }
   }

   public MouseListener (MyWorld w){
      world = w;
      currentElementIndex = 0;
   } 
   public void mouseMoved(MouseEvent e) {
      Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
      elementsAtPos = world.find(p.getX(), p.getY());
      if (elementsAtPos.size() == 0) return;
      currentElementIndex = (currentElementIndex >= elementsAtPos.size()) ? 0 : currentElementIndex;
      PhysicsElement newElement = elementsAtPos.get(currentElementIndex); 
      if (newElement == currentElement) return;
      setCurrentElement(newElement);
      world.repaintView();
   }
   
   public void mouseDragged(MouseEvent e) {
	      Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
	      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
	   if (currentElement instanceof Ball) {
		      System.out.println("Dragging Ball");
		      ((Ball)currentElement).setPosition(p.getX());
		   }    
		   else if (currentElement instanceof FixedHook){
			  System.out.println("Dragging FixedHook");
		      ((FixedHook)currentElement).setPosition(p.getX());
		   }
		   else if (currentElement instanceof Block){
			  System.out.println("Dragging Block");
		      ((Block)currentElement).setPosition(p.getX());
		   }
		   else if (currentElement instanceof Spring){	   
			  ((Spring)currentElement).detachAend();  
			  ((Spring)currentElement).detachBend();

		      ((Spring)currentElement).setPosition(p.getX());
		      System.out.println("Dragging Spring");
		   }

	      world.repaintView();
   }

   public void switchSelection() {
      if (elementsAtPos.size() < 2) return;

      if (currentElementIndex+1 < elementsAtPos.size()) {
         currentElementIndex += 1;
      } else {
         currentElementIndex = 0;
      }

      PhysicsElement newElement = elementsAtPos.get(currentElementIndex); 
      if (newElement == currentElement) return;
      setCurrentElement(newElement);
      world.repaintView();
   }

   public void mouseReleased(MouseEvent e) {
      if (currentElement == null) return;
      else if (currentElement instanceof Spring) {
         Point2D.Double p= new Point2D.Double(0,0);
         MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);

         Spring spring = (Spring) currentElement;
          // we dragged a spring, so we look for and attachable element near by  
         SpringAttachable elementA = world.findSpringAttachable(spring.getAendPosition(), p.getY());
         SpringAttachable elementB = world.findSpringAttachable(spring.getBendPosition(), p.getY());
         
         if (elementA != null) {
               spring.attachBend(elementA);
         }
         if (elementB != null) {
               spring.attachAend(elementB);
         }
      }   
	  System.out.println("Mouse released.");
      currentElement.setReleased();
      currentElement = null;
      world.repaintView();
   }
}