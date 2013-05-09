package sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import logic.Bird;

public class SoundMixer implements Observer {
	private int count = 0;
	
	public SoundMixer() {
		
	}
	
	/**
	 * wird aufgerufen, wenn das Partikelsystem(Model) sich geändert hat,
	 * um auf die Änderungen zu reagieren, in dem die Abmischung der Sound-Loops
	 * neu berechnet wird.
	 * 
	 * @param  o Referenz auf das Partikelsystem(Model)
	 * 
	 * @param arg Die Liste aller Vogel-Partikel (ArrayList<MovingParticle>)
	 */
	@SuppressWarnings("unchecked")
	public void update(Observable o, Object arg) {
		ArrayList<Bird> birds = (ArrayList<Bird>) arg;
		// so wird auf die Position jedes einzelnen Vogels zugegriffen:
		for (Iterator<Bird> iterator = birds.iterator(); iterator.hasNext();) {
			Bird bird = iterator.next();
			float xPos = bird.getPosition().getX();
			float yPos = bird.getPosition().getY();
			if(xPos >= 100f && yPos >= 100f && xPos <= 200 && yPos <= 200){
				count++;
			}
			if(count == 10){
				playSound();
				count = 0;
			}
		}
		
		// dieser Aufruf darf gerne entfernt werden! ist nur zu Testzwecken da
//		System.out.println("Die Sounds werden abgemischt");
	}
	
	private void playSound(){
//		soundThread.start();
	}
}