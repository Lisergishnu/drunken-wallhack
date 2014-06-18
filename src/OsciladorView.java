import java.awt.*;
import java.awt.geom.*;

public class OsciladorView {
   private double width;
   private double height;
   private double xView;
   private double yView;
   private double radius;
   private Color color = Color.YELLOW;
   private Color colorI = Color.BLACK;
   private Rectangle2D.Double shape = null;
   private Ellipse2D.Double shapeI = null;
   private Oscilador osc;


	public OsciladorView(Oscilador oscilador) {
		this.osc = oscilador;
		width = oscilador.getWidth();
		height = width; 
		radius = width /2;
		//OBS: La idea es que quede centrado en la posicion
		xView = oscilador.getPosition() - width / 2;
		yView = - height / 2;
		this.shape = new Rectangle2D.Double(xView,yView,width,height);
		this.shapeI = new Ellipse2D.Double(oscilador.getPosition(), 0, width, height);
	}

	public void updateView(Graphics2D g){
		xView = osc.getPosition() - width / 2;
		yView = - height / 2;
		shape.setFrame(xView,yView,width,height);		
		g.setColor(color);
		g.fill(shape);
		shapeI.setFrame(xView, yView, 2*radius, 2*radius);
		g.setColor(colorI);
		g.fill(shapeI);
	}

	public boolean contains(double x, double y) {
		return shape.contains(x, y);
	}

	public void setSelected() {
		color = Color.RED;
	}
	public void setReleased() {
		color = Color.YELLOW;
	}
}