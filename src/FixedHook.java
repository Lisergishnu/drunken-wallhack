import java.util.*;
import java.awt.*;

public class FixedHook extends PhysicsElement implements SpringAttachable {

//Codigo extendido de la tarea 1

	//***********
    // VARIABLES
    //***********
    private static int id=0;
    private double position;
    private double width;
    private FixedHookView view;
    private Spring attachedSpring;

    //**************
    // CONSTRUCTORES
    //**************
    private FixedHook() {  // nobody can create a block without state
        this(0,1);
    }
    
    public FixedHook(double _x, double width) {
        super(id++);
        this.position = _x;
        this.width = width;
        view = new FixedHookView(this);
    }
    
    //*************************
    // METODOS PRIVADOS
    //*************************
    

    //*************************
    // METODOS PUBLICOS
    //*************************
    
    public double getPosition() {
        return position;
    }

    public double getWidth() {
    	return width;
    }

    //**************************************
    // METODOS IMPLEMENTADOS DE SUPERCLASE
    //**************************************
    
    public String getDescription(){
        return "f" + getId();
    }
    public String getState(){
        return df.format(position);
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

    public void setPosition(double x) {
 	   position = x;
    }
    
    public void attachSpring(Spring spring) {
    	attachedSpring = spring;
    }

    public void detachSpring(Spring s) {
    	attachedSpring = null;
    }



}