import java.util.*;
import java.awt.*;

/**
 * Esta clase representa una bola solida y describe la logica 
 * para su simulacion. Permite aplicarle fuerzas y engancharse a 
 * resortes. 
 *
 * @version     0.4                 
 */

public class Ball extends PhysicsElement implements Simulateable,SpringAttachable {
   private static int id=0;  // Ball identification number
   private final double mass;
   private final double radius;
   private double pos_t;     // current position at time t
   private double pos_tPlusDelta;  // next position in delta time in future
   private double speed_t;   // speed at time t
   private double speed_tPlusDelta;   // speed in delta time in future
   private BallView view;  // Ball view of Model-View-Controller design pattern
   private Spring attachedSpring;

   private double extF;
   
   /**
   * Constructor privado.
   */
   private Ball(){   // nobody can create a block without state
     this(1.0,0.1,0,0);
   }
  /**
   * Constructor.
   *
   * Inicializa el objeto, creando adicionalmente una vista para poder
   * desplegarla en pantalla. 
   * @see BallView
   *
   * @param mass        Masa de la bola a simular [kg]
   * @param radius      Radio de la bola a simular
   * @param position    Lugar donde se ubicara en la recta inicialmente [m]
   * @param speed       Velocidad inicial [m/s]
   */
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      this.radius = radius;
      pos_t = position;
      speed_t = speed;
      view = new BallView(this);
      extF = 0;
      attachedSpring = null;
   }
   /**
   * Retorna la masa de la bola.
   * @return Masa actual en [kg]
   */
   public double getMass() {
      return mass;
   }
   /**
   * Retorna el radio de la bola.
   * @return Radio
   */
   public double getRadius() {
      return radius;
   }
   /**
   * Retorna la velocidad actual de la bola.
   * @return Velocidad en [m/s]
   */
   public double getSpeed() {
      return speed_t;
   }
   /**
   * Establece una fuerza externa sobre la bola.
   * @param ext Fuerza para aplicar, signo positivo demarca hacia la derecha
   */
   public void setForce(double ext){
	   extF = ext;
   }
   /**
   * Establece la posicion abosluta de la bola la recta
   * @param x posicion en [m]
   */
   public void setPosition(double x) {
	   pos_t = x;
   }
   /**
   * Calcula si dos bolas estan colisionando.
   * @param b Objeto Simulateable(bola o bloque) con el que se quiere comparar
   * @return True si la bola desde la cual se llama el metodo colisiona a el Simulateable b. Falso en caso contrario o si la bola es colisionada por el Simulateable b.
   */
   public boolean collide(Simulateable b) {//revisar
     if (this == b) return false;
     boolean closeEnougth = Math.abs(getPosition()-b.getPosition()) < (getRadius()+b.getRadius());
     boolean approaching = getSpeed() > b.getSpeed();
     if (b.getPosition() < getPosition())
        approaching = getSpeed() < b.getSpeed();
     return closeEnougth && approaching;
   }
   
   /**
   * Redibuja la bola. En particular el metodo solo llama a que la vista (View) se refresque.
   * @param g Objeto Graphics2D para manejar la vista
   * @see MyWorldView
   */
   public void updateView (Graphics2D g) {   // NEW
     view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern.   
   }
   /**
   * Verifica si un punto en el plano xy se encuentra dentro de la bola
   * @param x Punto en el eje X
   * @param y Punto en el eje Y
   * @return True si true si el punto (x,y) esta en la bola. En particular se verifica que en el componente de vista (View) este sea el caso.
   * @see BallView
   */
   public boolean contains(double x, double y) {
      return view.contains(x,y);
   }
   /**
   * Marca bola como seleccionada para manipulacion.
   * @see MouseListener
   **/
   public void setSelected(){
      view.setSelected();
   }
   /**
   * Libera bola como seleccionada para manipulacion.
   * @see MouseListener
   **/
   public void setReleased(){
      view.setReleased();
   }
   /**
   * Establece la posicion abosluta de la bola la recta
   * @param x posicion en [m]
   */
   public void dragTo(double x){
      pos_t=x;
   }
   /**
   * Obtiene descripcion de la bola con su ID
   * @return String de la forma "Ball_"+ getId()+":x"
   */
   public String getDescription() {
     return "Ball_" + getId()+":x";
   }
   /**
   * Obtiene estado actual de la bola en la simulacion
   * @return String de la forma getPosition()+""
   **/
   public String getState() {
     return getPosition()+"";
   }

   //**************************************
   // METODOS OBLIGADOS POR INTERFAZ
   //**************************************
   /**
   * Agrega referencia del Spring enganchado a la bola.
   * @param s Spring que se engancho a la bola
   **/
   public void attachSpring(Spring s){
     attachedSpring = s;
   }
   /**
   * Denota que se desengancho el Spring actual. Anula referencia del Spring previamente enganchado a la bola, 
   * setea la fuerza externa a cero, y para evitar que la bola escape, tambien se setea la velocidad a cero.
   * @param s Spring que se desengancho de la bola
   **/
   public void detachSpring(Spring s){
     attachedSpring = null;
     extF = 0;
     speed_t = 0;
   }
   /**
   * Retorna la posicion actual de la bola en la recta
   * @return Posicion en [m]
   **/
   public double getPosition() {
      return pos_t;
   }
   /**
   * Actualiza el estado de la bola segun los calculos realizados
   * @see Simulateable
   **/
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
   /**
   * Calcula el estado siguiente de la bola con respecto al mundo simulado.
   * @param delta_t Diferencia de tiempo entre el cuadro de simulacion actual y el futuro en [s].
   * @param world   Referencia al mundo MyWorld que contiene la bola
   * @see Simulateable
   * @see MyWorld
   **/
   public void computeNextState(double delta_t, MyWorld world) {
     Simulateable b;  // Assumption: on collision we only change speed.   
     if ((b=world.findCollidingElement(this))!= null){ /* elastic collision */
        speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
        pos_tPlusDelta = pos_t;
     } else {
        speed_tPlusDelta = speed_t + extF*delta_t/mass;
        pos_tPlusDelta = pos_t + speed_t*delta_t;
     }
   }
}
