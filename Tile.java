package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//class that is extended by all tiles
public class Tile {
	Window owner;
	
	int x, y, xpix, ypix;
	
	BufferedImage sprite;
	
	public void paint(Graphics g){
		g.drawImage(sprite, xpix, ypix, null);
	}
	
	public Tile(Window frame, int x, int y, BufferedImage img){
		owner = frame;
		
		this.x = x;
		this.y = y;
		xpix = x*32;
		ypix = y*32;
		
		sprite = img;
	}
}
