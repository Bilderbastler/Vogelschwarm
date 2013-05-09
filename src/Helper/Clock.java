package Helper;
import java.util.ArrayList;
import java.util.Iterator;

import Interfaces.Updater;

/**
 * Die Clock-Klasse fungiert als Taktgeber für das Programm
 * 
 * @author florianneumeister
 *
 */
public class Clock implements Runnable {
	
	/** Länge eines Frame. Kehrwert der Framerate */
	private int frameDuration;
	/** Liste aller registrierten Updater-Objekte */
	private ArrayList<Job> list;
	
	/**
	 * Konstruktor initialisiert die Liste der Updater-Objekte
	 */
	public Clock() {
		Settings settings = Settings.getInstance();
		
		int frameRate = settings.getPropertyAsInteger("FrameRate");
		this.frameDuration = 1000 / frameRate;
		
		this.list = new ArrayList<Job>();
	}
	
	/**
	 * Wenn der Thread gestartet wird, wird in einer 
	 * Schleife immer wieder die loop()-Methode aufgerufen
	 * und danach der Thread schlafen geschickt für die Dauer
	 * eines Frames.
	 */
	public void run() {
		while(true){
			loop();
			// TODO Frequenz aus Config-Klasse beziehen.
			try {
				Thread.sleep(frameDuration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Diese Methode wird in jedem Durchgang der Methode run()
	 * aufgerufen, um in allen registrierten Objekten die Enter-Frame
	 * Methode aufgerufen. 
	 */
	private void loop() {
		for (Iterator<Job> iterator = list.iterator(); iterator.hasNext();) {
			Job job = (Job) iterator.next();
			if (job.curFrame == 1) {
				job.obj.enterFrame();
				job.curFrame = job.rate;
			}else{
				job.curFrame--;
			}
		}
	}
	
	/**
	 * Registriert ein Objekt an der Clock und legt fest nach wie
	 * vielen Frames das Objekt erneut aktuallisiert werden soll. 
	 * 
	 * @param obj das zu registeriende Updater-Objekt
	 * @param rate frequenz der Zeitabstand in Frames zwischen zwei Aktuallisierungen
	 */
	public void register(Updater obj, int rate) {
		Job job = new Job(obj, rate);
		list.add(job);
	}
	
	/**
	 * Registriert ein Updater-Objekt an der Clock, um jeden Frame erneut 
	 * aktuallisert werden soll
	 * 
	 * @param obj das zu registerende Updater-Objekt
	 */
	public void register(Updater obj){
		register(obj, 1);
	}
	
	/**
	 * Wrapper-Klasse für die Auflistung aller Updater-Objekte und ihrer Frame-Raten
	 * 
	 * @author florianneumeister
	 *
	 */
	private class Job{
		public Updater obj;
		public final int rate;
		public int curFrame;

		public Job(Updater obj, int rate) {
			this.obj = obj;
			this.rate = rate;
			this.curFrame = rate;
		}
	}

}
