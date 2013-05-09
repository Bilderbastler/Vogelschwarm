package Helper;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>Diese Klasse lädt die Settings des Programms bzw. legt die Standardwerte fest. Dazu wird eine 
 * Instanz der Properties-Klasse benutzt. Um eine Mehrfachinstanzierung zu verhindert, ist diese 
 * Klasse als Singleton-Klasse ausgelegt.</p>
 * 
 * <p>Die Klasse stellt eine Wraper-Klasse für die Properties-Klasse dar und lässt sich genau wie
 * diese benutzen, nur werden schon automatisch die Einstellungen des Programms geladen</p>
 * 
 * <p>Die Standard-Werte werden in der Mehtode {@code loadDefault()} festgelegt.</p>
 * 
 * @author florianneumeister
 *
 */
public class Settings extends Properties{
	private static final long serialVersionUID = -8048281533651734851L;
	/** Der Name der Properties-Datei" */
	private static final String fileName = "vogelschwarm.properties";
	/**
	 * Die Instanz der Properties-Klasse
	 */
	private static Settings instance = new Settings();
	
	/**
	 * Der private-Konstruktor zur Erzeugung der Instanz
	 */
	private Settings() {
		super();
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			this.load(inputStream);
		} catch (Exception e) {
			loadDefault();
		}
	}
	
	/**
	 * Hier werden alle Standardwerte des Programms festgelegt
	 */
	private void loadDefault() {
	// Globale Einstellungen
		this.setProperty("FrameRate", "30");
		//in soviele Zellen wird die Welt unterteilt:
		this.setProperty("VerticalCellNumber", "2");
		this.setProperty("HorizontalCellNumber", "3");
	// Die Settings für die Vogelobjekte
		this.setProperty("BirdMaxSpeed", "5");
		this.setProperty("BirdSize", "2");
		this.setProperty("AmountOfBirds", "200");
		this.setProperty("BirdRadius","1.0");
		this.setProperty("BirdMass","1.0");
		this.setProperty("MaxBirdSpeed","8");
		this.setProperty("BirdMaxForce","0.5");
		this.setProperty("BirdMaxTurnRate","1.0");
		this.setProperty("BirdSeparationWeight","5.0");
		this.setProperty("BirdAlignmentWeight", "0.3");
		this.setProperty("BirdCohesionWeight", "0.3");
		this.setProperty("FleeDistance", "70");
		this.setProperty("FleeWeight", "1.0");
		
	// Die Settings für das Partikelsystem
		this.setProperty("NumberOfBirds", "200");
		//wie weit werden die Zellen der Welt noch einmal extra unterteilt im Partikelsystem
		this.setProperty("Subdivsion", "3");
		
	// Die Settings für das Grafik-Modul
		this.setProperty("WindowWidth", "1280");
		this.setProperty("WindowHeight", "720");
		this.setProperty("BirdImageSize", "10");
		this.setProperty("BirdColor",""+Color.GRAY.getRGB());	
		this.setProperty("PreyBirdColor",""+Color.BLACK.getRGB());
		
	// Die Settings für das Sound-Modul
		
	}
	
	/**
	 * liefert die Instanz des Settings-Objekts
	 * @return Instanz der Settings
	 */
	public static Settings getInstance(){
		return instance;
	}
	
	/**
	 * Sucht einen Wert heraus und wandelt ihn automatisch nach Float
	 * 
	 * @param key the property key
	 * @return den Wert als Float
	 */
	public float getPropertyAsFloat(String key){
		return Float.parseFloat(this.getProperty(key));
	}
	
	/**
	 * Sucht einen Wert heraus und wandelt ihn automatisch nach Integer
	 * 
	 * @param key the property key
	 * @return den Wert als Integer
	 */
	public int getPropertyAsInteger(String key){
		return Integer.parseInt(this.getProperty(key));
	}
	
	/**
	 * Sucht einen Wert heraus und wandelt ihn automatisch nach Boolean
	 * 
	 * @param key the property key
	 * @return den Wert als Boolean
	 */
	public boolean getPropertyAsBoolean(String key){
		return Boolean.parseBoolean(this.getProperty(key));
	}
	
	/**
	 * Speichere die Einstellungen in die Datei zurück.
	 */
	public static void saveSettings(){
		try {
			FileOutputStream output = new FileOutputStream(fileName);
			instance.store(output, "vogelschwarm-settings");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
