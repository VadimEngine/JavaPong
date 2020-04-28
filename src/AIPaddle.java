import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AIPaddle {

	int x;
	int y;
	int width = 15;
	int height = 40;
	int speed = 2;

	Rectangle boundingBox;

	boolean goingUp = false;
	boolean goingDown = false;

	public AIPaddle(int x, int y) {
		this.x = x;
		this.y = y;

		boundingBox = new Rectangle(x, y, width, height);
		boundingBox.setBounds(x, y, width, height);
	}

	public void tick(Game game) {
		boundingBox.setBounds(x, y, width, height);

		if (Game.ball.x > game.getWidth() / 2 && Game.ball.vx == 2) {

			if (Game.ball.y < y && y >= 0) {
				y -= (speed);
			}
			if (Game.ball.y > y && y + height <= game.getHeight()) {
				y += (speed);
			}
		}

		if (Game.ball.vx == -2) {

			if (y < game.getHeight() / 2 && y > 0) {
				y += (speed);
			}
			if (y > game.getHeight() / 2 && y < game.getHeight()) {
				y -= (speed);
			}
		}

	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);

	}
}



