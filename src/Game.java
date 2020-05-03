import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * 
 * Main class of the game, includes the main method and the main tick and render methods. Uses multi-threading to run
 * a "run" method which controls the whole game. 
 * 
 * Builds a JFrame to hold a Canvas which the game is rendered on.
 * 
 * @author Vadim
 *
 */
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 400;
	private static final String TITLE = "Pong";
	
	private boolean running;
	private Thread thread;
	
	private int FPS;
	private Handler handler;
	
	/**
	 * Constructor, sets size of the canvas/, uses Handler class as the key listener.
	 */
	public Game() {
		JFrame frame = new JFrame();
		final Dimension gameSize = new Dimension(WIDTH, HEIGHT);
		
		setMinimumSize(gameSize);
		setPreferredSize(gameSize);
		setMaximumSize(gameSize);
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setLocationRelativeTo(null);

		handler = new Handler(getWidth(), getHeight());
		this.addKeyListener(handler);
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that runs the entire game, keeps track how often the methods  called and keep them at 60 calls per
	 * second or below.
	 */
	@Override
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) {
				passedTime = 0;
			}
			if (passedTime > 100000000) {
				passedTime = 100000000;
			}

			unprocessedSeconds += passedTime / 1000000000.0;
			boolean ticked = false;
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					FPS = frames;
					lastTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Main tick method, controls the background calculations and object interactions. The calculations are handled
	 * and gathered by the handler class. 
	 */
	public void tick()  {		
		handler.tick();
	}

	/**
	 * Main render method, draws using triple buffering, draws the game information. which is gathered and created
	 * by the Handler class.
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.drawRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		handler.render(g);
		g.drawString("FPS: " + FPS, 0, 32);
		g.dispose();
		bs.show();
	}

	/**
	 * Main method, builds the frame, adds the game, ands starts the game thread.
	 * @param args main method command line arguments.
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}