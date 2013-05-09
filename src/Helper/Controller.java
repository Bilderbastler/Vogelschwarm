package Helper;

import java.awt.Point;

import logic.ParticleSystem;
import sound.SoundMixer;
import visual.GraphicEngine;

/**
 * Die Controller-Klasse entsprechend dem MVC-Pattern,
 * die zwischen Sound- und Grafikdarstellung einerseits
 * und der Partikellogik der Všgel andererseits 
 * vermittelt.
 * 
 * @author florianneumeister
 *
 */
public class Controller {
	/** Das Basis-Objekt der Sound-Darstellung */
	private SoundMixer sound;
	/** Das Basis-Objekt der Grafik-Darstellung */
	private GraphicEngine vision;
	/** Das Basis-Objekt der Vogelschwarm-Logik */
	private ParticleSystem model;
	/** gibt die Framerate des Programms vor */
	private Clock clock;
	private Thread clockThread;

	/**
	 * Der Konstruktor der Controllerklasse
	 * @param model Referenz auf die Vogelschwarmlogik, die durch 
	 * Partikelsystem dargestellt wird
	 */
	public Controller(ParticleSystem model) {
		this.model = model;
		this.sound = new SoundMixer();
		this.vision = new GraphicEngine(this);
		this.clock = new Clock();
		// registriere Audio- und Grafikengine am 
		// Partikelsystem, um Ÿber €nderungen informiert
		// zu werden
		model.addObserver(sound);
		model.addObserver(vision);
		
		// registriere das Partikelsystem am Taktgeber
		clock.register(model);
		
		vision.setBirdFlock(model.getBirds());
		vision.setPreyBird(model.getBirdOfPrey());
		// das Herz unserer Anwendung beginnt zu schlagen:
		clockThread = new Thread(clock);
		clockThread.start();
		// und los gehts É 
		
		Thread t = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		t.start();
	//	t.sleep(3000);
	}
	
	/**
	 * Diese Methode wird aufgerufen, wenn das Programmfenster geschlossen wird,
	 * um das Programm zu beenden.
	 */
	public void closeProgramm(){
		Settings.saveSettings();
		// Auf Wiedersehen !!
		System.exit(1);
	}
	
	/**
	 * Legt die Position der Maus auf der Karte fest, um das Ziel des Raubvogels festzulegen.
	 * 
	 * @param point Die Position der Maus
	 */
	public void setMousePosition(Point point) {
		this.model.setPreyBirdTarget(point);
	}
}
