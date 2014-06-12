import java.util.*;
import java.awt.*;

public class Block extends PhysicsElement implements SpringAttachable, Simulateable {

	
		private static int id=0;  // Ball identification number
		private final double mass;
		private final double width;
		private double pos_t;     // current position at time t		   
		private double pos_tPlusDelta;  // next position in delta time in future
		private double speed_t;   // speed at time t
		private double speed_tPlusDelta;   // speed in delta time in future
		private BlockView view;  // Ball view of Model-View-Controller design pattern		   
		private Spring attachedSpring;
		private double extF;
		private double intF;
		private static MyWorld world;
		private final double friction = 0.4f;
	
	public Block(){
		this(1,.5,1,0,world);
	}
	
	public Block(double mass, double width, double position,double speed, MyWorld world){
		super(id++);
		this.mass = mass;
		this.width = width;
	    pos_t = position;
	    speed_t = speed;
	    view = new BlockView(this);
	    extF = 0;
	    attachedSpring = null;
	    intF=this.mass*world.getGravity()*friction;
	}
	

	public double getMass() {
		return mass;
	}
	public double getWidth() {
		return width;
	}
	public double getRadius() {//para evitar rediseñar choques en ball
		return width/2;
	}
	public double getSpeed() {
		return speed_t;
	}
	public void setForce(double ext){
		extF = ext;
	}
	public void setPosition(double x) {
		pos_t = x;
	}
	public double getFriction(){
		return friction;
	}

	public boolean collide(Block b) {
		if (this == b) return false;
		boolean closeEnougth = Math.abs(getPosition()-b.getPosition()) < (getRadius()+b.getWidth());//revisar radius
		boolean approaching = getSpeed() > b.getSpeed();
		if (b.getPosition() < getPosition())
			approaching = getSpeed() < b.getSpeed();
		return closeEnougth && approaching;
	}
	
	/*private void set_intF(){
		intF = -1*Math.signum(speed_t)*mass*world.getGravity()*friction;
	}*/

    //**************************************
    // METODOS IMPLEMENTADOS DE SUPERCLASE
    //**************************************
    
    public String getDescription(){
        return "block" + getId();
    }
    public String getState(){
        return df.format(pos_t);
    }
    public void updateView(Graphics2D g) {
        view.updateView(g);
    }
    public boolean contains(double x, double y) {
        return view.contains(x,y);
    }
    public void setSelected() {
        view.setSelected();
    }
    public void setReleased() {
        view.setReleased();
    }
    public void dragTo(double x){

    }
    
    //**************************************
    // METODOS OBLIGADOS POR INTERFAZ
    //**************************************
    
    public void attachSpring(Spring spring) {
    	attachedSpring = spring;
    }

    public void detachSpring(Spring s) {
    	attachedSpring = null;
    }
    
    public double getPosition() {
        return pos_t;
    }

    public void computeNextState(double delta_t, MyWorld world) {
        Simulateable b;  // Assumption: on collision we only change speed.   
        if ((b=world.findCollidingElement(this))!= null){ /* elastic collision */
           speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
           pos_tPlusDelta = pos_t;
        } else if (Math.abs(speed_t) > 0.3) {
           speed_tPlusDelta = speed_t + (extF-Math.signum(speed_t)*intF)*delta_t/mass;
           pos_tPlusDelta = pos_t + speed_t*delta_t;
        } else {
        	speed_tPlusDelta = 0;
        	pos_tPlusDelta = pos_t;
        }
      }
    public void updateState(){
        pos_t = pos_tPlusDelta;
        speed_t = speed_tPlusDelta;
    }

	public boolean collide(Simulateable b) {
		if (this == b) return false;
		boolean closeEnougth = Math.abs(getPosition()-b.getPosition()) < (getRadius()+b.getRadius());
		boolean approaching = getSpeed() > b.getSpeed();
		if (b.getPosition() < getPosition())
			approaching = getSpeed() < b.getSpeed();
		return closeEnougth && approaching;
	}

}
