package Helper;

import java.awt.Point;

/**
 * Diese Klasse bietet dient der Darstellung von 2d-Koodinaten als Vektor und
 * verschiedene Vektor-Rechenoperationen. Statt mit Integer wie die
 * Point-Klasse, arbeitet diese Klasse mit Double-Werten.
 * 
 * @author florianneumeister
 * 
 */
public class Vector2d {

	/** die X-Koordinate */
	private float x;
	/** die Y-Koordinate */
	private float y;

	/**
	 * Erzeugt einen neuen Vektor mit der Länge 0
	 */
	public Vector2d() {
		this.zero();
	}

	/**
	 * Erzeugt einen Vektor aus einem Point-Objekt
	 * 
	 * @param p
	 *            das Point-Objekt
	 */
	public Vector2d(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Erzeugt einen neuen Vektor mit den gegebenen Koordinaten
	 * 
	 * @param x
	 *            die X-Koordinate
	 * @param y
	 *            die Y-Koordinate
	 */
	public Vector2d(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d(Vector2d vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}

	/**
	 * setzt x und y Wert auf 0
	 */
	public void zero() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * berechnet die Länge des Vektors
	 * 
	 * @return die Länge des Vektors
	 */
	public float length() {
		return (float) Math.sqrt(this.lengthSquare());
	}

	/**
	 * Gibt die Länge des Vektors im Qudrat an. Nützlich, wenn nicht die exakte
	 * Länge nötig ist, sondern nur Längen verschiedener Vektoren verglichen
	 * werden, da durch das Auslassen der Wurzel-Rechnung ein wenig Zeit gespart
	 * wird.
	 * 
	 * @return die Länge des Vektors zum Qudrat
	 */
	public float lengthSquare() {
		return (x * x + y * y);
	}

	/**
	 * Addiert einen zweiten Vektor auf den Vektor
	 * 
	 * @param v2
	 *            der zu addierende Vektor
	 */
	public void add(Vector2d v2) {
		this.x += v2.getX();
		this.y += v2.getY();
	}

	/**
	 * Subtrahiert einen zweiten Vektor von diesem Vektor
	 * 
	 * @param v2
	 *            der abzuziehende Vektor
	 * @return
	 */
	public void sub(Vector2d v2) {
		this.x -= v2.getX(); 
		this.y -= v2.getY();
	}

	/**
	 * Multipliziert den Vektor mit einem Skalar
	 * 
	 * @param ska
	 *            der Skalar
	 */
	public void multiply(float ska) {
		this.x *= ska;
		this.y *= ska;
	}

	/**
	 * Macht den Vektor zu einem Einheitsvektor. (Vektor der Länge 1)
	 */
	public void normalize() {
		float len = this.length();
		if(len != 0){
			this.x = this.x / len;
			this.y = this.y / len;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	/**
	 * berechnet das Skalar-Produkt zweier Vektoren Vektor1 * Vektor2
	 */
	public float dot(Vector2d v2) {
		return (this.x * v2.getX() + this.y * v2.getY());
	}

	/**
	 * Gibt an ob der Vektor sich im oder gegen den Uhrzeigersinn zum zweiten
	 * Vektor befindet
	 * 
	 * @param v2
	 *            der zweite Vektor
	 * @return wahr, wenn sich der Vektor im Uhrzeigersinn befindet.
	 */
	public boolean isClockwise(Vector2d v2) {
		// TODO zu ende Implementieren
		return false;
	}

	/**
	 * Liefert den Vektor, der senkrecht zu diesem Vektor steht
	 * 
	 * @return senkrechter Vektor
	 */
	public Vector2d perp() {
		return new Vector2d(this.y, this.x);
	}

	/**
	 * Veringert den Vektor, so das seine Länge nicht den gegebenen Wert
	 * übersteigt
	 * 
	 * @param max
	 *            die maximale Länge des Vektors
	 */
	public void truncate(float max) {
		if (this.lengthSquare() > max * max) {
			this.normalize();
			this.multiply(max);
		}
	}

	/**
	 * Legt die Länge des Vektors neu fest
	 * 
	 * @param len
	 *            die neue Länge
	 */
	public void setLength(float len) {
		this.normalize();
		this.multiply(len);
	}

	/**
	 * Liefert den entgegengesetzten Vektor zurück.
	 * 
	 * @return der entgegengesetzte Vektor
	 */
	public Vector2d getReverse() {
		return new Vector2d(this.x * -1, this.y * -1);
	}

	/**
	 * Gibt den Winkel in Rad (2*PI) zwischen zwei Winkel an
	 * 
	 * @param v2
	 *            der zweite Vektor
	 * @return der Winkel in Rad
	 */
	public double getAngle(Vector2d v2) {
		if (!v2.isZero()) {
			// berechne das Skalar-Produkt und teile es durch das Produkt der
			// beiden Vektor-Längen
			double dot = this.dot(v2) / (this.length() * v2.length());
			// nehme den Arkus-Cosinus
			return Math.acos(dot);
		} else {
			return 0;
		}
	}

	/**
	 * prüft, ob der Vektor die Länge 0 hat
	 * 
	 * @return wahr, wenn 0
	 */
	public boolean isZero() {
		return (this.x == 0 && this.y == 0);
	}

	/**
	 * Erzeugt einen Einheitsvektor von einem gegebenen Vektor
	 * 
	 * @param v
	 *            der Vektor
	 * @return der Einheitsvektor des übergebenen Vektors
	 */
	public Vector2d getNormalizedVector() {
		Vector2d norm = new Vector2d(this.getX(), this.getY());
		norm.normalize();
		return norm;
	}

	/**
	 * Dividiert den Vektor durch einen Skalar. Überprüft, ob der Skalar auch nicht 0 ist.
	 * 
	 * @param scalar
	 *            der Scalar durch den geteilt wird
	 */
	public void divide(float scalar) {
		if(scalar != 0){
			this.x /= scalar;
			this.y /= scalar;
		}
	}

	/**
	 * berechne die Distanz im Quadrat zu einem anderen Vektor
	 * @param vec die Position, zu der der Abstand bestimmt werden soll
	 * @return der Abstand im Quadrat
	 */
	public float distanceToSq(Vector2d vec) {
		float a = this.x - vec.getX();
		float b = this.y - vec.getY();
		return (a*a + b*b);
	}
	
	/**
	 * bereche die Distanz zu einem anderen Vektor
	 * @param vec die Position zu der die Distanz bestimmt werden soll
	 * @return die Distanz 
	 */
	public float distanceTo(Vector2d vec){
		return (float) Math.sqrt(distanceToSq(vec));
	}
	
}
