package sound;

import java.io.*;
import javax.sound.sampled.*;

public class SoundLoader implements Runnable{
	
	public void run(){
		try{
			File file = new File("Sound.wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			Clip soundClip = AudioSystem.getClip();
			
			soundClip.open(ais);
			soundClip.start();
			
			long millisecs = soundClip.getMicrosecondLength()/1000;
			Thread.sleep(millisecs);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}