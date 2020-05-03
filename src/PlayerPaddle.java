import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Player paddle that is controlled with W and S key. Moves up and down and stays within the game borders.
 * 
 * @author Vadim
 *
 */
public class PlayerPaddle {
	private int x;
	private int y;
	private int width = 16;
	private int height = 16*3;
	private int speed = 4;
	private Rectangle rect;
	
	private Handler handler;
	
	private boolean goingUp;
	private boolean goingDown;

	/**
	 * Constructor that sets the position and sets the Rectangle for collision detection.
	 * @param x X position
	 * @param y Y position
	 * @param handler Game handler
	 */
	public PlayerPaddle(int x, int y, Handler handler){
		rect = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.handler = handler;
	}
	
	/**
	 * Update the Rectangle for collision detection. Moves up up and down if goingUp and/or goingDown is set.
	 */
	public void tick() {
		rect.x = x;
		rect.y = y;
		if (goingUp && y > 0) {
			y -= speed;
		}
		if (goingDown && y  < handler.getHeight() - height) {
			y += speed;
		}
	}
	
	/**
	 * Renders a rectangle at x, y position
	 * @param g The game graphics object
	 */
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}
	
	//Getters and Setters-----------------------------------------------------------------------------------------------

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * Used for Collision detections
	 * @return the rectangle representing the PlayerPaddle position and shape
	 */
	public Rectangle getRectangle() {
		return rect;
	}
	
	public void setGoingUp(boolean goingUp) {
		this.goingUp = goingUp;
	}
	
	public void setGoingDown(boolean goingDown) {
		this.goingDown = goingDown;
	}
}