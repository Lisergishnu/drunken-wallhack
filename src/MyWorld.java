import java.util.*;
import java.io.*;

import javax.sound.sampled.Clip;
import javax.swing.Timer;

import de.progra.charting.render.PlotChartRenderer;

import java.awt.Color;
import java.awt.event.*;

/**
 * Esta clase define un mundo virtual en el cual objetos definidos por PhysicsElement pueden interactuar.
 * La simulacion usa un modelo de integracion para determinar el estado siguiente de los objetos, considerando una diferencia de tiempo finita para calcularlos.
 * @version     0.4                 
 */

public class MyWorld implements ActionListener {
	private PrintStream out;

	private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.
	private MyWorldView view;   // NEW
	private Timer passingTime;   // NEW
	private double t;        // simulation time
	private double delta_t;        // in seconds
	private double refreshPeriod;  // in seconds
	final private double refreshPlotPeriod = .01; //tambien in seconds
	private double gravity=10; // in m/s^2

	//Arrays for graphics
	private int arrayStep;
	private int arrayStepAnterior;

	private GraphView mPlotHandle;



	/**
	 * Constructor.
	 * Genera un mundo con la salida de datos hacia System.out.
	 **/
	public MyWorld(){
		this(System.out);  // delta_t= 0.1[ms] and refreshPeriod=200 [ms]
	}
	/**
	 * Constructor.
	 * Genera un mundo con la salida de datos hacia la definida en los argumentos.
	 * @param output PrintStream hacia donde se van a escribir los datos
	 **/
	public MyWorld(PrintStream output){
		out = output;
		t = 0;
		refreshPeriod = 0.01;      // 60 [F.P.S.]
		delta_t = 0.00001;          // 0.01 [ms]
		elements = new ArrayList<PhysicsElement>();
		view = null;
		passingTime = new Timer((int)(refreshPeriod*1000), this); 

		arrayStep = 0;
		arrayStepAnterior = -1;

	}
	/**
	 * Devuelve el valor de la gravedad en este mundo
	 * @return gravedad en [m/s^2]
	 */
	public double getGravity(){
		return gravity;
	}
	/**
	 * Añade un elemento al mundo.
	 * @param e Elemento a agregar
	 * @see PhysicsElement
	 **/
	public void addElement(PhysicsElement e) {
		elements.add(e);
		view.repaintView();
	}
	/**
	 * Establecer la referencia hacia el MyWorldView que dibujara el estado del mundo.
	 * @param view Referencia del panel que mostrara el mundo
	 **/
	public void setView(MyWorldView view) {
		this.view = view;
	}
	/**
	 * Establecer tiempo delta.
	 * @param delta Tiempo de integracion en [s]
	 **/
	public void setDelta_t(double delta) {
		delta_t = delta;
	}
	/**
	 * Establecer periodo de refresco para la pantalla.
	 * @param rp Periodo de refresco en [s]
	 **/
	public void setRefreshPeriod (double rp) {
		refreshPeriod = rp;
		passingTime.setDelay((int)(refreshPeriod*1000)); // convert from [s] to [ms]
	}
	/**
	 * Iniciar/reanudar la simulacion.
	 **/
	public void start() {
		if(passingTime.isRunning()) return;
		passingTime.start();
		view.disableMouseListener();
	}
	/**
	 * Parar/pausar la simulacion.
	 **/
	public void stop(){
		if(passingTime.isRunning()){
			passingTime.stop();
			view.enableMouseListener();
		}         
		else
			return;
	}

	/**
	 * Definido desde la interfaz actionPerformed avisa cuando ocurre un evento en el contexto grafico.
	 * @inheritDoc
	 **/
	public void actionPerformed (ActionEvent event) {  // like simulate method of Assignment 1,
		//Intervenimos para extraer de los elementos simulables la energia cinetica
		//y de los resortes la potencial
		double kE = 0,pE = 0;
		double nextStop=t+refreshPeriod;                // the arguments are attributes here.
		for (; t<nextStop; t+=delta_t){
			kE = pE = 0; //nos quedamos con la ultima vuelta
			for (PhysicsElement e: elements){
				if (e instanceof Simulateable) {
					Simulateable s = (Simulateable) e;
					s.computeNextState(delta_t,this); // compute each element next state based on current global state
					s.updateState();
					kE += s.getKineticEnergy();
				}
				if (e instanceof Spring) {
					Spring s = (Spring) e;
					s.computeNextState(delta_t,this);
					pE += s.getPotentialEnergy();
				}
			}
		}
		//Guardamos los valores
		arrayStep = (int) ((int) t / refreshPlotPeriod);

		//Si se actualizaron los datos (cada refreshPlotTime segundos)
		//debemos refrescar el grafico
		if(arrayStep != arrayStepAnterior) {
			mPlotHandle.addDataPoint(0, kE, t);
			mPlotHandle.addDataPoint(1, pE, t);
			mPlotHandle.addDataPoint(2, pE + kE, t);
			arrayStepAnterior = arrayStep;
		}
		//Despues de haber simulado todo es necesario redibujar la vista
		repaintView();
	}
	/**
	 * Redibuja la pantalla.
	 **/
	public void repaintView(){
		view.repaintView();
	}

	/**
	 * Encuentra un objeto Ball que colisiona con otro.
	 * @param me Objeto "Simulateable"(Ball o Block) de referencia
	 * @return Referencia a elemento "Simulateable"(Ball o Block) que colisiona con me
	 **/
	public Simulateable findCollidingElement(Simulateable me) {
		for (PhysicsElement e: elements)
			if ( e instanceof Simulateable) {
				Simulateable b = (Simulateable) e;
				if ((b!=me) && b.collide(me)) {
					view.playCollisionClip();
					return b;
				}
			}
		return null;
	}

	/**
	 * Retorna la lista de elementos fisicos en el mundo.
	 * @return ArrayList<PhysicsElement> con los elementos del mundo. En el caso que no existan, la lista tendra size() cero
	 **/
	public ArrayList<PhysicsElement> getPhysicsElements(){
		return elements;
	}

	//MBT: Ahora en vez de entregar un solo elemento, entrega una lista
	//     con todos los que estan en esa posicion
	/**
	 * Retorna la lista de elementos fisicos en el mundo que contienen al punto en el plano xy.
	 * @param x Punto en el plano X
	 * @param y Punto en el plano Y
	 * @return ArrayList<PhysicsElement> con los elementos correspondientes. En el caso que no existan, la lista tendra size() cero
	 **/
	public ArrayList<PhysicsElement> find(double x, double y) {
		ArrayList<PhysicsElement> l = new ArrayList<PhysicsElement>();
		for (PhysicsElement e: elements)
			if (e.contains(x,y)) l.add(e);
		return l;
	}  

	/**
	 * Regresa el primer elemento SpringAttachable que se encuentre en la posicion (x,y).
	 * @param x Punto en el plano X
	 * @param y Punto en el plano Y
	 * @return Referencia al elemento encontrado. Null si no se encuentra elemento alguno 
	 **/
	public SpringAttachable findSpringAttachable(double x, double y) {
		for (PhysicsElement e: elements){
			if(e instanceof SpringAttachable){
				if (e.contains(x, y)) 
					return (SpringAttachable)e;
			}
		}
		return null;
	}

	/**
	 * Permite registrar el clip de sonido que se usara en la colision hacia MyWorldView.
	 * @param clipCollision Clip de sonido ya inicializado. 
	 **/ 
	public void setCollisionClip(Clip clipCollision) {
		view.setCollisionClip(clipCollision);
	}

	public void setTitle(String title) {
		view.setTitle(title);	
	}

	public void setPlot(GraphView plot) {
		mPlotHandle = plot;

	}


} 
