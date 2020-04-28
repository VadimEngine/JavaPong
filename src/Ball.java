import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class Ball {
	int x, y;
	int size = 16;
	int speed = 2;
	int vx, vy, vxi;
	
	Rectangle boundingBox;
	
	public Ball(int x, int y){
		this.x = x;
		this.y = y;
		
		vx = speed;
		vy = speed;
		
		boundingBox = new Rectangle (x, y, size, size);
		boundingBox.setBounds(x, y, size, size);
	}

	public void tick(Game game) throws LineUnavailableException {
		boundingBox.setBounds(x, y, size, size);
		
		if (x <=0){
			game.p2score ++;
			Game.ball = new Ball(game.getWidth() / 2, game.getHeight() / 2);
		} else if (x + size >= game.getWidth() - size) {
			game.p1score ++;
			Game.ball = new Ball(game.getWidth() / 2, game.getHeight() / 2);
		}
		
		if (y <= 0) {
			vy = speed;
		} else if (y + size >= game.getHeight()) {
			vy = - speed;
		}
		
		x += vx;
		y += vy;
		
		if(vxi!= vx) {
			
			byte[] buf = new byte[1];
			AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
			SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
			sdl.open();
			sdl.start();

			for( int i = 0; i < 100 * (float )44100 / 1000; i++ ) {
				double angle = i / ( (float )44100 / 440 ) * 2.0 * Math.PI;
				buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
				sdl.write( buf, 0, 1 );
			}
			
			vxi=vx;
		}
		
		paddleCollide(game);
	}
	
	private void paddleCollide(Game game) {
		if (boundingBox.intersects(Game.player.boundingBox)) {
			vx = speed;	
		} else if (boundingBox.intersects(Game.ai.boundingBox)) {
			vx = -speed;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x,  y,  size, size);
		
	}
	
	public void setVx(int speed) throws LineUnavailableException {
	
	}

}



