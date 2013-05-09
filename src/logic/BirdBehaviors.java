package logic;

import Helper.Vector2d;

public class BirdBehaviors extends SteeringBehaviors {




	@Override
	public Vector2d calculate() {
		// Steuerkraft zur�cksetzten
		steeringForce.zero();
		
		//bestimme Nachbarn des Partikels
		neighbors = this.agent.getCurrentCell().getNeighborContent();
		
		// berechne die einzelnen Bewegungskr�fte
		align = alignment(neighbors);
		sep = separation(neighbors);
		coh = cohesion(neighbors);
		flee = flee(this.agent.getWorld().getBirdOfPrey().getPosition(), fleeDistance );
		
		// gewichte die einzelnen Bewegungskr�fte
		align.multiply(alignmentWeight);
		sep.multiply(separationWeight);
		coh.multiply(cohesionWeight);
		flee.multiply(fleeWeight);
		
		//addiere die Kr�fte zusammen
		steeringForce.add(align);
		steeringForce.add(coh);
		steeringForce.add(sep);
		steeringForce.add(flee);
		
		// stelle sicher, dass sie die maximale Kraft nicht �bersteigen
		steeringForce.truncate(this.agent.getMaxForce());
		
		return steeringForce;
	}

}
