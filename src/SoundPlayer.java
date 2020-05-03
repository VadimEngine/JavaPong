import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Used to play sounds on a separate thread. Plays a custom sound using AudioFormat and SourceDataLine
 * @author Vadim
 *
 */
public class SoundPlayer {

	/**
	 * Used to prevent multiple sound from playing at the same time
	 */
	private static boolean running = false;

	public static float SAMPLE_RATE = 8000f;

	/**
	 * Method to create a thread and play a sound
	 */
	public static void playBeep() {
		new Thread(new SoundThread()).start();
	}

	/**
	 * Thread object that is generated when a sound needs to be played.
	 * @author user
	 *
	 */
	private static class SoundThread implements Runnable {

		@Override
		public void run() {
			if (!running) {
				running = true;
				int hz = 400;
				int msecs = 100;
				double vol = 1.0;
				try {
					byte[] buf = new byte[1];
					AudioFormat af = 
							new AudioFormat(
									SAMPLE_RATE, // sampleRate
									8,           // sampleSizeInBits
									1,           // channels
									true,        // signed
									false);      // bigEndian
					SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
					sdl.open(af);
					sdl.start();
					for (int i=0; i < msecs*8; i++) {
						double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
						buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
						sdl.write(buf,0,1);
					}
					sdl.drain();
					sdl.stop();
					sdl.close();
					running = false;
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}

	}

}