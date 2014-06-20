import java.awt.Graphics2D;


public class Oscilador extends PhysicsElement implements Simulateable, SpringAttachable {

	private static int id=0;
	private double amplitud;
	private double frecuencia;
	private OsciladorView view;
	
	private double posInicial,pos,nextPos;
	private double width;
	private double personalTime;
	private double mass;
	private double speed_t,nextSpeed_t;
	
	private Spring attachedSpring;
	
	public Oscilador(double _x, double mass, double width, double amplitud, double frecuencia) {
		super(id++);
		this.pos = _x;
		this.posInicial = _x;
		this.width = width;
		this.amplitud = amplitud;
		this.frecuencia = frecuencia;
		this.attachedSpring = null;
		this.mass = mass;
		this.personalTime = 0;
		view = new OsciladorView(this);
	}


	public void attachSpring(Spring s) {
		attachedSpring = s;
	}


	public void detachSpring(Spring s) {
		attachedSpring = null;
	}

	
	public void computeNextState(double delta_t, MyWorld w) {
		personalTime += delta_t;
		nextPos = posInicial + amplitud * Math.sin(2 * Math.PI * frecuencia * personalTime);
		nextSpeed_t = amplitud * 2 * Math.PI * frecuencia * Math.cos(2 * Math.PI * frecuencia * personalTime);
	}

	
	public void updateState() {
		// TODO Auto-generated method stub
		pos = nextPos;
	}

	@Override
	public boolean collide(Simulateable me) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public double getMass() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public double getPosition() {
		// TODO Auto-generated method stub
		return pos;
	}

	
	public double getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setForce(double force) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPosition(double x) {
		this.pos = x;
	}

	public String getDescription(){
        return "o" + getId();
    }
    public String getState(){
        return df.format(pos);
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


	public double getWidth() {
		return width;
	}
	
	public double getKineticEnergy() {
		return 0.5 * mass * speed_t * speed_t;
	}
}
