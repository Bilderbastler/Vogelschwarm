import Helper.Controller;
import logic.ParticleSystem;

/**
 * Dies ist der Einstiegspunkt fuer das Programm.
 * 
 * @author florianneumeister
 *
 */
public class VogelschwarmMain {

	/**
	 * Erzeugt eine Instanz des Partikelsystems, dass die Bewegung
	 * der Voegel berechnet und uebergibt dieses als Model an die
	 * Controller-Instanz
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//erzeuge das Partikelsystem, dass die Bewegung der V�gel berechnet
		ParticleSystem model = new ParticleSystem();
		//erzeuge einen Kontroller, der auf die Daten des Partikelsystems zugreifen 
		//kann.
		new Controller(model);
	}
}