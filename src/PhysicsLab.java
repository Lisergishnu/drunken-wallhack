import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.sound.sampled.*;
import javax.swing.*;

import org.math.plot.Plot2DPanel;

import java.awt.Container;
import java.io.IOException;
import java.net.URL;

public class PhysicsLab {

	private MyWorld world;
	public MyWorld getWorld() {return world;}

	public PhysicsLab() {
		world = new MyWorld();
	}
	
	/*
	 * @returns JSplitPane Panel listo para anadir al contexto requerido (Applet o JPanel)
	 */
	public JSplitPane initialize() {
		MyWorldView  worldView = new MyWorldView(world);
		world.setView(worldView);
		worldView.setTitle("Physics Lab 3");
		Plot2DPanel plot = new Plot2DPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, worldView, plot);
		int pEHandle = plot.addLinePlot("Potential Energy", world.getTimeArray(), world.getPotentialEnergy());
		int kEHandle = plot.addLinePlot("Kinetic Energy", world.getTimeArray(), world.getKineticEnergy());
		int mEHandle = plot.addLinePlot("Mechanical Energy", world.getTimeArray(), world.getMechanicalEnergy());
		world.setCurrentPlot(plot, pEHandle, kEHandle, mEHandle);
		//Cargar sonido
		URL url = this.getClass().getClassLoader().getResource("collision.wav");
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
		return splitPane;
	}

	public static void main(String[] args) {
		PhysicsLab yo = new PhysicsLab();
		PhysicsLab_GUI lab_gui = new PhysicsLab_GUI();
		JSplitPane splitPane = yo.initialize();
		LabMenuListener menuListener = new LabMenuListener(yo.getWorld());
		lab_gui.setJMenuBar(yo.createLabMenuBar(menuListener));
		lab_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lab_gui.add(splitPane);
		lab_gui.setVisible(true);
		splitPane.setDividerLocation(0.75);
		
	}

	public JMenuBar createLabMenuBar(LabMenuListener menu_l) {
		JMenuBar mb = new JMenuBar();

		JMenu menu = new JMenu ("Configuration");
		mb.add(menu);
		JMenu subMenu = new JMenu("Insert");  
		menu.add(subMenu);

		JMenuItem menuItem = new JMenuItem("Ball");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);
		menuItem = new JMenuItem("Spring");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);
		menuItem = new JMenuItem("Fixed Hook");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);
		menuItem = new JMenuItem("Block");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);
		menuItem = new JMenuItem("My scenario");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		menu = new JMenu("MyWorld");
		mb.add(menu);
		menuItem = new JMenuItem("Start");
		menuItem.addActionListener(menu_l);
		menu.add(menuItem);
		menuItem = new JMenuItem("Stop");
		menuItem.addActionListener(menu_l);
		menu.add(menuItem);
		//Submenu Simulator
		subMenu = new JMenu("Simulator");
		menu.add(subMenu);
		menuItem = new JMenuItem("Delta time");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);
		menuItem = new JMenuItem("View Refresh time");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		return mb;          
	} 
}

class PhysicsLab_GUI extends JFrame {
	public PhysicsLab_GUI() {
		setTitle("My Small and Nice Physics Laboratory");
		setSize(MyWorldView.WIDTH+100, MyWorldView.HEIGHT+150);  // height+50 to account for menu height
	}   
}
