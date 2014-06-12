import java.awt.*;
import java.awt.geom.*;

public class BlockView {
	   private double width;
	   private double height;
	   private double xView;
	   private double yView;
	   private Color color = Color.YELLOW;
	   private Rectangle2D.Double shape = null;
	   private Block block;


		public BlockView(Block block) {
			this.block = block;
			width = block.getWidth();
			height = width; 
			//OBS: La idea es que quede centrado en la posicion
			xView = block.getPosition() - width / 2;
			yView = - height / 2;
			this.shape = new Rectangle2D.Double(xView,yView,width,height);
		}

		public void updateView(Graphics2D g){
			xView = block.getPosition() - width / 2;
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
			color = Color.YELLOW;
		}
}
