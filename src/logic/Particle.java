package logic;


import Helper.Vector2d;

public abstract class Particle {
	protected Vector2d position;
	protected float radius;
	
	/**
	 * Erzeugt einen neuen Partikel
	 * @param x die X-Koordinate des Partikels
	 * @param y die Y-Koordinate des Partikels
	 * @param radius der Radius des Partikels
	 */
	public Particle(float x, float y, float radius) {
		this.position = new Vector2d(x, y);
		this.radius = radius;
	}
	
	public Vector2d getPosition() {
		return position;
	}
	public float getRadius() {
		return radius;
	}
	/**
	 * Diese Methode wird zu Beginn jedes Zeit-Frames aufgerufen, um die Situation des Objekts
	 * neu zu berechnen (zB. neue Position berechnen, neue Bewegungsrichtung festlegen etc.)
	 * 
	 */
	public abstract void Update();
}