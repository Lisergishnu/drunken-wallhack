import javax.swing.JApplet;
import javax.swing.JSplitPane;


public class PhysicsLabApplet extends JApplet {

	private PhysicsLab phyLab;
	private MyWorld world;
	private JSplitPane splitPane;
	
	public PhysicsLabApplet() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		phyLab = new PhysicsLab();
		world = phyLab.getWorld();
		splitPane = phyLab.initialize();
		LabMenuListener menuListener = new LabMenuListener(world);
		setJMenuBar(phyLab.createLabMenuBar(menuListener));
		getContentPane().add(splitPane);
		
		//Cargar parametros desde HTML
		world.setTitle(getParameter("title"));
		world.setDelta_t(Double.parseDouble(getParameter("deltaTime")));
		world.setRefreshPeriod(Double.parseDouble(getParameter("refreshTime")));
		//TODO: Set max plotting time
		world.setPlottingRange(Double.parseDouble(getParameter("maxPlotTime")));

		//Ver cuantos objetos hay descritos y agregarlos a MyWorld
		//Bolas
		int nBolas = Integer.parseInt(getParameter("ballNum"));
		for (int i = 0; i < nBolas; i++) {
			//Obtener parametros
			String param = getParameter("ball." + (i+1) );
			String[] paramList = param.split(";");
			world.addElement(new Ball(Double.parseDouble(paramList[0]),
					Double.parseDouble(paramList[1]),
					Double.parseDouble(paramList[2]),
					Double.parseDouble(paramList[3])));
		}

		//FixedHooks
		int nHooks = Integer.parseInt(getParameter("fixedHookNum"));
		for (int i = 0; i < nHooks; i++) {
			//Obtener parametros
			String param = getParameter("fixedHook." + (i+1) );
			String[] paramList = param.split(";");
			world.addElement(new FixedHook(Double.parseDouble(paramList[0]),
					Double.parseDouble(paramList[1])));
		}

		//Osciladores
		int nOsc = Integer.parseInt(getParameter("oscillatorNum"));
		for (int i = 0; i < nOsc; i++) {
			//Obtener parametros
			String param = getParameter("oscillator." + (i+1) );
			String[] paramList = param.split(";");
			world.addElement(new FixedHook(Double.parseDouble(paramList[0]),
					Double.parseDouble(paramList[1])));
		}
	}
	
	public void start() {
		splitPane.setDividerLocation(0.65);
	}
}