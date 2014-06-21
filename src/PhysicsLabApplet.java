import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
			world.addElement(new Oscilador(Double.parseDouble(paramList[0]),
					Double.parseDouble(paramList[1]),
					Double.parseDouble(paramList[2]),
					Double.parseDouble(paramList[3]),
					Double.parseDouble(paramList[4])));
		}
		
		//Cargar sonido
		File url= new File("assets/collision.wav");
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			Clip clipCollision = AudioSystem.getClip();
			clipCollision.open(audio);
			world.setCollisionClip(clipCollision);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Audio file Unsupported.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't open file.");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("Cannot open audio clip line.");
			e.printStackTrace();
		}
	}
	
	public void start() {
		splitPane.setDividerLocation(0.65);
	}
}