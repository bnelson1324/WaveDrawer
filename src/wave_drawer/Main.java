package wave_drawer;
import java.awt.EventQueue;

import ui.AppWindow;

public class Main {

	/**
	 * This app lets the user draw waves and hear them at different frequencies 
	 * The drawing in the window is 1 sample of the wave
	 * */
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow frame = new AppWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		new Thread(() -> {
			WavePlayer.start();
		}).start();
		
		/*// plays a sin wave at 440hz
		double[] wave = new double[StdAudio.SAMPLE_RATE];
		for(int i = 0; i < StdAudio.SAMPLE_RATE; i++) {
			wave[i] = Math.sin(i*2*Math.PI/StdAudio.SAMPLE_RATE*440);
			System.out.println(wave[i]);
			StdAudio.play(wave[i]);
		}
		*/
		
	}
	
	

	
}
