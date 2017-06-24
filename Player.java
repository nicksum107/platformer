package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Player {
	Window owner;
	
	double x, y, xvel, yvel, yaccel; 
	
	boolean keyW, keyA, keyS, keyD;
	boolean wUp,  aUp,  sUp,  dUp;
	
	BufferedImage sprite;
	
	public void paint(Graphics g){
		g.drawImage(sprite, (int)x, (int)y, null);
	}
	
	final double SLOW_DOWN_SPD = 0.3;
	final double SPD_UP_SPD = 0.2;
	final double GRAVITY = 0.1; //pixels / frame ^ 2 
	public void resolveMovement(){ //resolve movement
		 //update x and y position
		 x += xvel; //time passed is always 1 frame so let's just make it based on frame
		 y += yvel;
		 
		 
		 //TODO: stop when hit a wall 
		 
		 //horizontal mvt
		 if ((!keyA && !keyD) || (keyA && keyD)) { //slow down
			 if 	  (xvel>SLOW_DOWN_SPD)xvel-= SLOW_DOWN_SPD;
			 else if ((xvel <SLOW_DOWN_SPD&& xvel>=0)|| (xvel >SLOW_DOWN_SPD&& xvel<0)) xvel = 0;
			 else 	                      xvel +=SLOW_DOWN_SPD;
		 } else{ //speed up until hits max speed which is  3 pixels/frame
			 if (keyA && xvel >-5) xvel -= SPD_UP_SPD; //derivatives
			 if (keyD && xvel < 5) xvel += SPD_UP_SPD;
		 }
		 
		 //when they're in the air, push down
		 if (!tileBelow()){
			 yvel += GRAVITY;
			
		 } else if (keyW){
			 yvel = -5; //todo the longer you press jump the higher you go
		 } else {
			 yvel = 0;
		 }
		 
		 
		 
		 //implement gravity except when touching a ground
		 //give player upwards velocity when they hit jump
		 
	}
	
	public boolean tileBelow(){
		return owner.tileBelow(x, y);
	}
	
	public void resetListeners() {
		if (wUp) { keyW = false; wUp = false;} //only reset if it's been released within that frame
		if (aUp) { keyA = false; aUp = false;}
		if (sUp) { keyS = false; sUp = false;}
		if (dUp) { keyD = false; dUp = false;}
	}
	
	public Player(Window frame, int x_start, int y_start) throws IOException{
		sprite = ImageIO.read(new File("player.png"));
		
		x = x_start;
		y = y_start; 
		xvel = 0;
		yvel = 0;
		
		owner = frame;
		owner.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				switch (e.getKeyChar()) {
					case 'w':
						keyW = true;
						break;
					case 'a':
						keyA = true;
						break;
					case 's':
						keyS = true;
						break;
					case 'd':
						keyD = true;
						break;
					default:
						break;
				}
			}
			public void keyReleased(KeyEvent e){
				switch (e.getKeyChar()){
					case 'w':
						wUp = true;
						break;
					case 'a':
						aUp = true;
						break;
					case 's':
						sUp = true;
						break;
					case 'd':
						dUp = true;
						break;
					default:
						break;
				}
			}
		});
	}

	
}
