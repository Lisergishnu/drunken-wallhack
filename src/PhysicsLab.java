import javax.swing.JFrame;
import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.Container;
import java.io.IOException;
import java.net.URL;

//OBS: Esta clase hace la funcion de la clase PhysicsLab Y PhysicsLabApplet, segun la tarea
public class PhysicsLab extends JApplet {

   private MyWorld world;
   public MyWorld getWorld() {return world;}

   public PhysicsLab() {
      world = new MyWorld();
   }

   public void init() {
      MyWorldView  worldView = new MyWorldView(world);
      world.setView(worldView);
      getContentPane().add(worldView);

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
      
      //Cargar parametros desde HTML
      world.setTitle(getParameter("title"));
      world.setDelta_t(Double.parseDouble(getParameter("deltaTime")));
      world.setRefreshPeriod(Double.parseDouble(getParameter("refreshTime")));
      //TODO: Set max plotting time
      //something something (Double.parseDouble(getParameter("maxPlotTime")))
      
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
      
   }

   public static void main(String[] args) {
      PhysicsLab yo = new PhysicsLab();
      PhysicsLab_GUI lab_gui = new PhysicsLab_GUI(yo.getWorld());
      lab_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      lab_gui.setVisible(true);
   }
}

class PhysicsLab_GUI extends JFrame {
   public PhysicsLab_GUI(MyWorld world) {
      setTitle("My Small and Nice Physics Laboratory");
      setSize(MyWorldView.WIDTH+100, MyWorldView.HEIGHT+150);  // height+50 to account for menu height
      LabMenuListener menuListener = new LabMenuListener(world);
      setJMenuBar(createLabMenuBar(menuListener));
      MyWorldView  worldView = new MyWorldView(world);
      world.setView(worldView);
      getContentPane().add(worldView);
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
