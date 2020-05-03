import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Ball object that bounces off the top and bottom border and the Player/AI paddles. If it reaches the left of right
 * border then it respawns in the middle of the game. Plays a sound on bouncing if handler sound is enabled
 * @author user
 *
 */
public class Ball {
	
	private static enum DIRECTION{LEFT, RIGHT};
	
	private int x;
	private int y;
	private int radius = 16;
	private int speed = 6;
	/**
	 * X velocity
	 */
	private int vx;
	/**
	 * Y velocity
	 */
	private int vy;
	
	private Rectangle rect;
	private Handler handler;
	
	/**
	 * Constructor that sets the position of the ball to the center of the game
	 * @param x The x position
	 * @param y The y position
	 * @param handler the game handler
	 */
	public Ball(Handler handler){
		this.x = handler.getWidth()/2;
		this.y = handler.getWidth()/2;
		this.handler = handler;
		vx = speed;
		vy = speed;
		rect = new Rectangle(x, y, radius, radius);
	}
	
	/**
	 * Ball moves with vx and vy velocities. Bounces off the top and bottom borders and player/ai paddles. Sets the
	 * Rectangle position for collision detection
	 */
	public void tick() {
		rect.x = x;
		rect.y = y;
		PlayerPaddle p = handler.getPlayer();
		AIPaddle ai = handler.getAi();
		
		if (x < 0){
			handler.addP2Score();
			x = handler.getWidth()/2;
			y = handler.getHeight()/2;
			bounceX(DIRECTION.RIGHT);
		} else if (x + radius*2  > handler.getWidth()) {
			handler.addP1Score();
			x = handler.getWidth()/2;
			y = handler.getHeight()/2;
			bounceX(DIRECTION.LEFT);
		}
		
		//top and bottom bounce
		if (y <= 0) {
			vy = speed;
		} else if (y + radius >= handler.getHeight()) {
			vy = - speed;
		}
					
		if (collideRect(p.getRectangle())) {
			bounceX(DIRECTION.RIGHT);
		}
		
		if (collideRect(ai.getRectangle())) {
			bounceX(DIRECTION.LEFT);
		}

		//move with its velocities	
		x += vx;
		y += vy;
	}
	
	/**
	 * Renders a white circle at x, y position
	 * @param g Graphics object that renders the game
	 */
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x,  y,  radius, radius);	
	}
		
	/**
	 * Switch the x direction and plays sound if handler sound is enabled.
	 */
	private void bounceX(DIRECTION dir) {
		if (dir == DIRECTION.LEFT) {
			vx = -speed;
		} else if (dir == DIRECTION.RIGHT) {
			vx = speed;
		}
		
		if (handler.soundEnabled()) {			
			SoundPlayer.playBeep();
		}
	}
		
	/**
	 * Uses Rectangle object for collision detection
	 * @param other
	 * @return
	 */
	private boolean collideRect(Rectangle other) {
		return rect.intersects(other);
	}
	
	//Getters=----------------------------------------------------------------------------------------------------------
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the rectangle that is used for collision detection
	 * @return
	 */
	public Rectangle getRectangle() {
		return rect;
	}
}