package Interfaces;
/**
 * Muss implementiert werden, damit ein Objekt beim Clock-Thread registriert werden
 * kann.
 * 
 * @author florianneumeister
 *
 */
public interface Updater {
	
	/**
	 * Diese Methode wird vom Clock-Thread aufgerufen, um dem Objekt mitzuteilen
	 * dass wieder ein neuer Zeitframe angebrochen ist und es an der Zeit ist, alle 
	 * Zeitabhängigen Aktionen neu zu berechnen und durchzuführen (zB. Animationen)
	 * 
	 * Wurde keine andere Frequenz angegeben, wird enterFrame entsprechend der FrameRate
	 * pro Sekunde aufgerufen.
	 */
	void enterFrame();
	
}
