package logic;


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;


import Helper.Settings;
import Helper.Vector2d;
import Interfaces.Updater;

/**
 * 
 * @author florianneumeister
 *
 */
public class ParticleSystem extends Observable implements Updater{
	
	/** Die Liste aller Vögel, die sich in dem Partikelsystem befinden */
	private ArrayList<Bird> birds;
	/** die Breite der Welt */
	private float worldWidth;
	/** die Höhe der Welt */
	private float worldHeight;
	private Cell<Bird>[][] cells;
	private int cellsX;
	private int cellsY;
	private float birdRadius;
	private Vector2d preyBirdTarget;
	/** der Raubvogel */
	private Bird birdOfPrey;
	
	/**
	 * Erzeugt ein neues Partikelsystem
	 * @param particles die Anzahl der Start-Partikel
	 */
	public ParticleSystem() {
		 // Lade Voreinstellungen
		 Settings settings = Settings.getInstance();
		 this.worldWidth = settings.getPropertyAsFloat("WindowWidth");
		 this.worldHeight = settings.getPropertyAsFloat("WindowHeight");
		 int amountOfBirds = settings.getPropertyAsInteger("AmountOfBirds");
		 birdRadius = settings.getPropertyAsFloat("BirdRadius");
		 float mass = settings.getPropertyAsFloat("BirdMass");
		 float maxSpeed = settings.getPropertyAsFloat("MaxBirdSpeed");
		 float maxForce = settings.getPropertyAsFloat("BirdMaxForce");
		 float maxTurnRate = settings.getPropertyAsFloat("BirdMaxTurnRate");
		 this.birds = new ArrayList<Bird>();
		 
		 // Unterteile die Welt 
		 int subdivision = settings.getPropertyAsInteger("Subdivsion");
		 cellsX = settings.getPropertyAsInteger("VerticalCellNumber") * subdivision;
		 cellsY = settings.getPropertyAsInteger("HorizontalCellNumber") * subdivision;
		 //erzeuge die Zellen-Datenstruktur für die Unterteilung der Welt
		 cells = new Cell<Bird>().createCellGrid(cellsX, cellsY);
		 
		 // erzeuge Vögel zu Testzwecken
		Color color = new Color(settings.getPropertyAsInteger("BirdColor"));
		 for (int i = 0; i < amountOfBirds; i++) {
			 Vector2d startVelocity = new Vector2d((float)Math.random(),(float) Math.random());
			 startVelocity.multiply(maxSpeed);
			 Bird bird = new Bird(this.worldWidth / 2, this.worldHeight / 2, this, birdRadius, mass,
					 maxSpeed, maxForce, maxTurnRate, startVelocity, new BirdBehaviors(), color );
			 birds.add(bird);
		 }
		 
		 //erzeuge den Raubvogel
		 this.preyBirdTarget = new Vector2d();
		 color = new Color(settings.getPropertyAsInteger("PreyBirdColor"));
		 this.birdOfPrey = new Bird(0,0, this, birdRadius, mass,
				 maxSpeed, maxForce, maxTurnRate, new Vector2d(), new PreyBirdBehaviors(), color );
		 
	}
	
	public Bird getBirdOfPrey() {
		return birdOfPrey;
	}

	/**
	 * aktuallisiere den Zustand des Partikelsystems und informiert die 
	 * anderen Objekte, dass es eine Änderung gegeben hat
	 */
	public void updated(){
		
		this.setChanged();
		// TODO übergibt sich selbst, wird später die neuen Positionen der Vögel übergeben
		this.notifyObservers(birds);
	}

	/**
	 * wird vom Taktgeber in der Klasse Clock aufgerufen, wenn ein neuer Zeitframe beginnt.
	 */
	public void enterFrame() {
		
		// als erstes sortiere alle Vögel 
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				// entleere die Zellen 
				cells[i][j].removeContent();
			}
		}
		// dann sortiere alle Vögel neu ein
		for (Bird bird : birds) {
			int x = (int) (((bird.getPosition().getX() + birdRadius) / (birdRadius + this.worldWidth)) * (this.cellsX));
			int y = (int) (((bird.getPosition().getY() + birdRadius) / (birdRadius + this.worldHeight)) * (this.cellsY));
			// TODO entfernen
			try{
				cells[x][y].addContent(bird);
			}catch (Exception e) {
				System.out.println("größe des zellen-arrays: " + cells.length + " " + cells[cells.length-1].length);
				System.out.println("versuchter index: " +x + " " + y);
				System.out.println(bird.getPosition().getX() + " " + bird.getPosition().getY());
			}
			bird.setCell(cells[x][y]);
		}
		
		//aktuallisiere jeden Vogel im Partikelsystem
		for (MovingParticle bird : birds) {
			bird.Update();
		}
		
		this.birdOfPrey.Update();
		
		// informiere alle zuhörenden Module, dass sich das Partikelsystem geändert hat.
		updated();
	}
	
	/**
	 * 
	 * @return die Breite der Welt
	 */
	public float getWidth() {
		return this.worldWidth;
	}
	
	/**
	 * 
	 * @return die Höhe der Welt
	 */
	public float getHeight() {
		return this.worldHeight;
	}

	/**
	 * liefert eine Liste aller Vögel
	 * @return die ArrayList der Vögel-Objekte
	 */
	public ArrayList<Bird> getBirds() {
		return this.birds;
	}

	/**
	 * legt das Ziel des Raubvogels fest
	 */
	public void setPreyBirdTarget(Point p) {
		this.preyBirdTarget = new Vector2d(p);
	}
	
	/**
	 * 
	 * @return die Zielkoordinaten des Raubvogels
	 */
	public Vector2d getPreyBirdTarget(){
		return this.preyBirdTarget;
	}
}
