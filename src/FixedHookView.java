import java.awt.*;
import java.awt.geom.*;

public class FixedHookView {
   private double width;
   private double height;
   private double xView;
   private double yView;
   private Color color = Color.GREEN;
   private Rectangle2D.Double shape = null;
   private FixedHook hook;


	public FixedHookView(FixedHook hook) {
		this.hook = hook;
		width = hook.getWidth();
		height = width; 
		//OBS: La idea es que quede centrado en la posicion
		xView = hook.getPosition() - width / 2;
		yView = - height / 2;
		this.shape = new Rectangle2D.Double(xView,yView,width,height);
	}

	public void updateView(Graphics2D g){
		xView = hook.getPosition() - width / 2;
		yView = - height / 2;
		shape.setFrame(xView,yView,width,height);
		
		g.setColor(color);
		g.fill(shape);
	}

	public boolean contains(double x, double y) {
		return shape.contains(x, y);
	}

	public void setSelected() {
		color = Color.BLUE;
	}
	public void setReleased() {
		color = Color.GREEN;
	}
}