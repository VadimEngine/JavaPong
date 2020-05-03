import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Ai Paddle that moves  at "speed" rate towards ball object to bounce the ball back.
 * @author user
 *
 */
public class AIPaddle {

	private int x;
	private int y;
	private int width = 16;
	private int height = 16*3;
	private int speed = 4;
	private Rectangle rect;
	
	private Handler handler;

	/**
	 * 
	 * @param x The x position of the Paddle
	 * @param y The y position of the Paddle
	 * @param handler The handler that manages the game
	 */
	public AIPaddle(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		rect = new Rectangle(x, y, width, height);
	}

	/**
	 * sets the rectangle object position which is used for collision detection. Moves towards the handler's ball
	 * when the ball is in the right half of the game
	 */
	public void tick() {
		rect.x = x;
		rect.y = y;
		final int gameWidth = handler.getWidth();
		final int gameHeight = handler.getHeight();
		
		if (handler.getBall().getX() > gameWidth/2) {
			if (handler.getBall().getY() < y && y >= 0) {
				y -= (speed);
			}
			if (handler.getBall().getY() > y && y + height <= gameHeight) {
				y += (speed);
			}
		}
	}

	/**
	 * Renders a white rectangle in the x, y position
	 * @param g
	 */
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}
	
	//Getters-----------------------------------------------------------------------------------------------------------
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
		
	public Rectangle getRectangle() {
		return rect;
	}
}