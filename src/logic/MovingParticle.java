package logic;

import Helper.Vector2d;

public abstract class MovingParticle extends Particle {
	

	protected Vector2d velocity;
	protected float mass;
	protected float maxSpeed;
	protected float maxForce;
	protected float maxTurnRate;
	/** Referenz auf die gesammte Welt, in der sich der Vogel bewegt */
	protected ParticleSystem world;
	
	public MovingParticle(float x, float y, float radius, float mass, float maxSpeed, float maxForce, float maxTurnRate, Vector2d startVelocity, ParticleSystem world) {
		super(x, y, radius);
		this.mass = mass;
		this.maxSpeed = maxSpeed;
		this.maxTurnRate = maxTurnRate;
		velocity = startVelocity;
		this.maxForce = maxForce;
		this.world = world;
	}
	
	public float getMass(){
		return mass;
	}
	public Vector2d getVelocity() {
		return velocity;
	}
	public Vector2d getHeading() {
		return velocity.getNormalizedVector();
	}
	public void setHeading(Vector2d heading) {
		heading.normalize();
		heading.multiply(velocity.length());
		this.velocity = heading;
	}
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public float getMaxForce() {
		return maxForce;
	}
	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}
	public float getMaxTurnRate() {
		return maxTurnRate;
	}
	public void setMaxTurnRate(float maxTurnRate) {
		this.maxTurnRate = maxTurnRate;
	}
	public boolean isAtMaximumSpeed(){
		return (maxSpeed*maxSpeed <= velocity.lengthSquare());
	}
	public float getSpeed(){
		return velocity.length();
	}
	
	public void setVelocity(Vector2d vec){
		this.velocity = vec;
	}
	
	/**
	 * 
	 * @return die Welt, in der der das Objekt lebt
	 */
	public ParticleSystem getWorld() {
		return world;
	}

	/**
	 * berechnet die neue Position auf der gegenŸberliegenden Seite, wenn der Vogel ans Bildschirm-Ende kommt
	 */
	protected void wrapAroundBorders() {
		if(this.position.getX() <= 0){
			this.position.setX(this.world.getWidth()-1 );
		}
		if(this.position.getX() >= this.world.getWidth()){
			this.position.setX(0);
		}
		if(this.position.getY() <= 0){
			this.position.setY(this.world.getHeight()-1);
		}
		if(this.position.getY() >= this.world.getHeight()){
			this.position.setY(0);
		}
	}
}
