package logic;

import java.awt.Color;

import visual.BirdShape;
import Helper.Settings;
import Helper.Vector2d;
/**
 * Diese Klasse enthält die gesamte Logik, nach der sich ein Vogel verhält, zB. 
 * zu benachbarten Vögeln hinsteuern und Kollisionen mit ihnen verhindern.
 * 
 * @author florianneumeister
 *
 */
public class Bird extends MovingParticle {
	/** Klasse für die Steuerungslogik */
	private SteeringBehaviors steering;
	/** die Zelle der Welt, in dem sich der Vogel gerade befindet */
	private Cell<Bird> currentCell;
	private BirdShape image; 
	
	/**
	 * Erzeugt einen neuen Vogel-Partikel
	 * 
	 * @param x die X Koordinate an der der Vogel geboren wird
	 * @param y die Y Koordinate an der der Vogel geboren wird
	 * @param world Referenz auf das Partikelsystem, dass alle Vögel-Partikel enthält.
	 * @param radius der Umfang des Vogels
	 * @param mass die Masse des Vogels
	 * @param maxSpeed die Höchstgeschwindigkeit des Vogels
	 * @param maxForce die das Maximum an Bewegungskraft, die der Vogel aufbringen kann
	 * @param maxTurnRate die maximale Geschwindigkeit, mit der sich der Vogel drehen kann
	 * @param startVelocity die Startgeschwindigketi
	 * @param behavior das Steuerungsverhalten des Vogels
	 * @param color  Die Farbe des Vogels
	 */
	public Bird(float x, float y, ParticleSystem world, float radius, float mass, float maxSpeed, float maxForce, float maxTurnRate, Vector2d startVelocity, SteeringBehaviors behavior, Color color ) {
		super(x, y, radius, mass, maxSpeed, maxForce, maxTurnRate, startVelocity, world);
		this.steering = behavior;
		this.steering.setAgent(this);
		
		Settings set = Settings.getInstance();
		float sepWeight = set.getPropertyAsFloat("BirdSeparationWeight");
		float alignWeight =  set.getPropertyAsFloat("BirdAlignmentWeight");
		float cohWeight =  set.getPropertyAsFloat("BirdCohesionWeight");
		float fleeDistance = set.getPropertyAsFloat("FleeDistance");
		float fleeWeight = set.getPropertyAsFloat("FleeWeight");
		steering.setFleeWeight(fleeWeight);
		steering.setCohesionWeight(cohWeight);
		steering.setAlignmentWeight(alignWeight);
		steering.setSeparationWeight(sepWeight);
		steering.setFleeDistance(fleeDistance);
		
		image = new BirdShape(color);
	}
	
	/**
	 * wird ausgeführt, um die neue Position des Vogels zu berechnen 
	 * und die Verhaltensstrategie des Vogels durchlaufen zu lassen.
	 */
	@Override
	public void Update() {

		
		// berechne die neue Beschleunigung
		Vector2d acceleration = steering.calculate();
		acceleration.divide(this.mass);
		
		
		this.velocity.add(acceleration);
		this.velocity.truncate(maxSpeed);
		
		this.position.add(velocity);
		wrapAroundBorders();
	}
	
	/**
	 * Legt den Bereich fest, in dem sich der Vogel gerade aufhält.
	 * @param c die Zelle in der sich der Vogel befindet
	 */
	public void setCell(Cell<Bird> c){
		this.currentCell = c;
	}
	
	/**
	 * liefert die Zelle der Welt, in der sich der Vogel gerade befindet
	 * @return die Zelle in der sich der Vogel befindet
	 */
	public Cell<Bird> getCurrentCell(){
		return this.currentCell;
	}
	
	public BirdShape getBirdShape(){
		return this.image;
	}	
}
