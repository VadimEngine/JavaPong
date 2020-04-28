import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W) {
			Game.player.goingUp = true;
		}
		
		if (keyCode == KeyEvent.VK_S) {
			Game.player.goingDown = true;
		}
	
	}

	public void keyReleased(KeyEvent e) {
int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W) {
			Game.player.goingUp = false;
		}
		
		if (keyCode == KeyEvent.VK_S) {
			Game.player.goingDown = false;
		
		}
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	
	

}



