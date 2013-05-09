package logic;

import java.util.ArrayList;

import Helper.Vector2d;

/**
 * 
 * @author florianneumeister
 *
 */
public abstract class SteeringBehaviors {
	/* Instanz des zu steuernden Agenten */
	protected Bird agent;
	
	/* die zu berechnende Bewegungskraft */
	protected Vector2d steeringForce;

	/* die Nachbarn des Agenten */
	protected ArrayList<Bird> neighbors;

	protected float fleeDistance;
	
	protected Vector2d align;
	protected Vector2d sep;
	protected Vector2d coh;
	protected Vector2d flee;

	protected float cohesionWeight;
	protected float alignmentWeight;
	protected float separationWeight;
	protected float seekWeight;
	protected float fleeWeight = 1.0f;
	
	public void setFleeWeight(float fleeWeight) {
		this.fleeWeight = fleeWeight;
	}

	public void setFleeDistance(float fleeDistance) {
		this.fleeDistance = fleeDistance;
	}

	public void setSeekWeight(float seekWeight) {
		this.seekWeight = seekWeight;
	}

	public SteeringBehaviors() {
		this.steeringForce = new Vector2d();
	}
	
	/**
	 * Berechnet die neue Bewegungsrichtung des Vogels
	 * @return die neue Bewegungskraft
	 */
	public abstract Vector2d calculate();
	/**
	 * Berechne die Bewegung, die den Agenten möglichst nahe an andere Agenten in der Nähe bringt, um eine Herde zu bilden.
	 * 
	 * @return Bewegungsvektor zum Herdenzentrum
	 */
	protected Vector2d cohesion(ArrayList<Bird> flock) {
		Vector2d force = new Vector2d();
		Vector2d flockCenter = new Vector2d();
		int neighborNumber = 0;
		for (MovingParticle bird : flock) {
			if(bird == this.agent || bird.getPosition().distanceToSq(this.agent.getPosition()) >  2500){
				continue;
			}
			// addiere alle Positionen der Nachbar-Agenten zusammen
			flockCenter.add(bird.getPosition());
			neighborNumber++;
			 
		}
		if(neighborNumber > 0){
			flockCenter.divide(neighborNumber);
			force = seek(flockCenter);
		}
		
		return force;
	}
	
	/**
	 * berechnet die Bewegungskraft, um auf einen gegebenen Punkt zuzusteuern
	 * @param position die angepeilte Position
	 * @return die Bewegungkraft, die benötigt wird, um auf den Punkt zuzusteuern
	 */
	protected Vector2d seek(Vector2d position) {
		Vector2d velocity = new Vector2d(position);
		velocity.sub(this.agent.getPosition());
		velocity.normalize();
		velocity.multiply(this.agent.getMaxSpeed());
		velocity.sub(this.agent.getVelocity());
		velocity.truncate(this.agent.getMaxForce());
		return velocity;
	}
	
	protected Vector2d separation(ArrayList<Bird> flock) {
		Vector2d force = new Vector2d();
		Vector2d toNeighbor;
		int count = 0;
		for (MovingParticle bird : flock) {
			if(bird == this.agent || bird.getPosition().distanceToSq(this.agent.getPosition()) >  625){
				continue;
			}
			float len = this.agent.getPosition().distanceTo(bird.getPosition());
			// skaliere entsprechend der Entfernung
			if(len > 0){
				// berchene den Vektor zwischen Agent und seinem Nachbarn
				toNeighbor = new Vector2d(this.agent.getPosition());
				toNeighbor.sub(bird.getPosition());
				toNeighbor.normalize();
				toNeighbor.divide(len);
				force.add(toNeighbor);
				count++;
			}
		}
		if(count > 0){
			force.divide(count);
		}
		return force;
	}
	
	/**
	 * Richte die Bewegungsrichtung nach der durchschnittlichen Bewegungsrichtung des Schwarms aus
	 * @param flock der Schwarm
	 * @return der Bewegungsvektor
	 */
	protected Vector2d alignment(ArrayList<Bird> flock) {
		Vector2d force = new Vector2d();
		int neighborNumber = 0;
		for (MovingParticle bird : flock) {
			if(bird == this.agent || bird.getPosition().distanceToSq(this.agent.getPosition()) >  2500){
				continue;
			}
			// adiere alle Richtungsvektoren zusammen
			force.add(bird.getVelocity());
			neighborNumber++;
		}
		if (neighborNumber > 0) {
			force.divide(neighborNumber);
			force.truncate(agent.getMaxForce());
		}
		
		return force;
		
	}
	
	/**
	 * weiche einem anderen Agenten aus
	 * @param pursuer der zu vermeidene Agent
	 * @return der Fluchtvektor
	 */
	public Vector2d evade(MovingParticle pursuer){
		//berechne den Vektor zum 'feindlichen' Agenten
		Vector2d toPursuer = new Vector2d(pursuer.getPosition());
		toPursuer.sub(this.agent.getPosition());
		// berechne die Zeit, die gebraucht würde um die Distanz zwischen den zwei Agenten zurück zulegen
		float lookAheadTime = toPursuer.length() /this.agent.getMaxSpeed() + pursuer.getSpeed();
		//berechne den Punkt, an dem sich die Agenten treffen würden.
		Vector2d vec = new Vector2d(pursuer.getVelocity());
		vec.multiply(lookAheadTime);
		Vector2d fleePosition = new Vector2d(pursuer.getPosition());
		fleePosition.add(vec);
		// berechne die Bewegung, die weg von dem potentiellen Treffpunkt führt.
		return flee(fleePosition);
	}
	
	/**
	 * Fliehe zu einer gegebenen Position
	 * @param fleePosition
	 * @return der Flucht-Bewegungsvektor
	 */
	protected Vector2d flee(Vector2d fleePosition) {
		return flee(fleePosition, Float.MAX_VALUE);
	}

	/**
	 * Fliehe von der angegebenen Position
	 * @param fleePosition die zu flüchtende Position
	 * @param panicDistance maximale Distanz ab der das Fluchtverhalten einsetzt
	 * @return die Fluchtbewegung
	 */
	protected Vector2d flee(Vector2d fleePosition, float panicDistance) {
		float panicDistanceSq = panicDistance * panicDistance;
		//wenn die entfernung groß genug ist, bewege dich nicht
		if(this.agent.getPosition().distanceToSq(fleePosition) > panicDistanceSq){
			return new Vector2d();
		}
		Vector2d force = new Vector2d(this.agent.getPosition());
		force.sub(fleePosition);
		force.normalize();
		force.multiply(this.agent.getMaxSpeed());
		
		force.sub(this.agent.getVelocity());
		return force;
		
	}

	/**
	 * legt die Gewichtung für das Zusammenstreben der Vögel fest
	 * @param cohWeight
	 */
	public void setCohesionWeight(float cohWeight) {
		this.cohesionWeight = cohWeight;
	}

	/**
	 * legt die Gewichtung für das Ausrichten an den anderen Vögeln fest
	 * @param alignWeight
	 */
	public void setAlignmentWeight(float alignWeight) {
		this.alignmentWeight = alignWeight;
	}

	/**
	 * legt die Gewichtung für das Ausweichen von anderen Vögeln fest
	 * @param sepWeight
	 */
	public void setSeparationWeight(float sepWeight) {
		this.separationWeight = sepWeight;
	}

	public void setAgent(Bird bird) {
		this.agent = bird;
	}
	
}
