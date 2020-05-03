import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Handler class that handles key inputs, and manages the game. Key game objects are PlayerPaddle, AIPaddle, and Ball. 
 * 
 * @author Vadim
 *
 */
public class Handler implements KeyListener {
	
	private static final int MAX_SCORE = 10;
		
	private boolean keys[] = new boolean[65536];
	
	private int width;
	private int height;

	private PlayerPaddle player;
	private AIPaddle ai;
	private Ball ball;
	
	private int p1score;
	private int p2score;
	
	private boolean paused;
	private boolean soundOn = true;
	
	/**
	 * Initializes the game objects player, ai and ball. Saves the canvas width and height.
	 * 
	 * @param width Width of the canvas
	 * @param height Height of the canvas
	 */
	public Handler(final int width, final int height) {
		this.width = width;
		this.height = height;

		player = new PlayerPaddle(4, height/2, this);
		ai = new AIPaddle(width - 20, height/2, this);
		ball = new Ball(this);
	}
	
	/**
	 * Updates the game objects, and reacts to key input. If a either player or ai reaches score of MAX_SCORE, then
	 *  game is paused and the winner is announced. User can reset the game with space key.
	 */
	public void tick() {		
		if (p1score == MAX_SCORE || p2score == MAX_SCORE) {
			paused = true;
		}
		if (!paused) {
			player.tick();
			ai.tick();
			ball.tick();
			if (keys[KeyEvent.VK_W]) {
				player.setGoingUp(true);
			} else {
				player.setGoingUp(false);
			}

			if (keys[KeyEvent.VK_S]) {
				player.setGoingDown(true);
			} else {
				player.setGoingDown(false);
			}
		} else {
			if (keys[KeyEvent.VK_SPACE]) {
				p1score = 0;
				p2score = 0;
				paused = false;
			}
		}
	}
	
	/**
	 * Renders the game content such as objects, scores and user instructions.
	 * @param g The Graphics object used to render the game.
	 */
	public void render(Graphics g) {
		player.render(g);
		ai.render(g);
		ball.render(g);
		
		g.setColor(Color.WHITE);

		g.drawString("Player 1: " + p1score, 0, 16);
		g.drawString("Player 2: " + p2score, width - 65, 16);
		String soundInfo = "Sound: " + soundOn + " . Toggle sound with: 1";
		g.drawString(soundInfo, width/2 - soundInfo.length() * 2, 16);
		
		if (paused) {
			if (p1score == MAX_SCORE) {
				g.drawString("Game Over. Player wins. Press Space to restart", width/2 - 60, height/2 - 12);
			} else if (p2score == MAX_SCORE) {
				g.drawString("Game Over. Computer wins. Press Space to restart", width/2 - 60, height/2 - 12);
			}
		}
	}	
	
	//Getters and Setters ----------------------------------------------------------------------------------------------
	
	public Ball getBall() {
		return ball;
	}
	
	public PlayerPaddle getPlayer() {
		return player;
	}
	
	public AIPaddle getAi() {
		return ai;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean soundEnabled() {
		return soundOn;
	}
	
	public void addP1Score() {
		p1score++;
	}
	
	public void addP2Score() {
		p2score++;
	}
	
	//End of Getters and setters ---------------------------------------------------------------------------------------
	
	//Key Listener------------------------------------------------------------------------------------------------------
	
	/**
	 * set keys to true for pressed keys.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys[keyCode] = true;
	}

	/**
	 * set keys to false for released keys. Also used to toggle sound when "1" is pressed.
	 */
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keys[keyCode] = false;
		
		if (keyCode == KeyEvent.VK_1) {
			soundOn = !soundOn;
		}
	}

	public void keyTyped(KeyEvent e) {}
	//End of Key Listener-----------------------------------------------------------------------------------------------
}