package logic;

import Helper.Vector2d;

public class PreyBirdBehaviors extends SteeringBehaviors {

	@Override
	public Vector2d calculate() {
		steeringForce.zero();
		steeringForce = seek(this.agent.getWorld().getPreyBirdTarget());
		steeringForce.truncate(this.agent.getMaxForce());
		return steeringForce;
	}

}
