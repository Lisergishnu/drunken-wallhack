import java.awt.*;
import java.awt.geom.*;

/**
 * Esta clase permite el despliegue en pantalla de un objeto Ball previamente inicializado.
 * Por defecto las bolas son de color azul y representadas por un circulo.
 * @version     0.4                 
 */

public class BallView {
   private Color color = Color.BLUE;
   private Ellipse2D.Double shape = null;
   private Ball ball;
   private float width = 64;
   private float height = 64;
   
   /**
   * Constructor.
   * @param b Objeto bola asociado a esta vista (View)
   **/
   public BallView (Ball b){
      ball = b;
      shape = new Ellipse2D.Double(b.getPosition(), 0, width, height);
   }
   /**
   * Verifica si un punto en el plano xy se encuentra dentro de la bola.
   * @param x Punto en el eje X
   * @param y Punto en el eje Y
   * @return true si true si el punto (x,y) esta en el circulo de la bola.
   */
   public boolean contains (double x, double y){
      return shape.contains(x, y);
   }
   /**
   * Marca bola como seleccionada para manipulacion.
   * @see MouseListener
   **/
   public void setSelected (){
      color = Color.RED;
   }
   /**
   * Libera bola como seleccionada para manipulacion.
   * @see MouseListener
   **/
   public void setReleased() {
      color = Color.BLUE;
   }
   /**
   * Redibuja la bola.
   * @param g Objeto Graphics2D para manejar la vista
   */
   public void updateView(Graphics2D g) {
      double radius = ball.getRadius();
      shape.setFrame(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
      g.setColor(color);
      g.fill(shape);
   }
}