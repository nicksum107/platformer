package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Window implements Runnable{
	private JFrame mainFrame;
	private Player player;
	private Tile[][] tiles;
	
	public Window() throws IOException{
		setup();
	}
	private void setup() throws IOException{
		mainFrame = new JFrame("Game!");
		mainFrame.setSize(32*32,24*32);
		mainFrame.setLocation(100, 100);
		
		mainFrame.addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		
		prepareOneScreenLevel();
		
		BufferedImage background = ImageIO.read(new File("background.png"));
		
		JPanel pane = new JPanel() {
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(background, 0,0, null);
				player.paint(g);
				for (int i = 0; i<tiles.length; i++){
					for (int c = 0; c<tiles[0].length;c++){
						if (tiles[i][c] != null){
							tiles[i][c].paint(g);
						}
					}
				}
			}
		};
		mainFrame.add(pane);
		
		
		mainFrame.setVisible(true);
		run();
	}
	private void prepareOneScreenLevel() throws IOException {
		tiles = new Tile[32][24];
		BufferedReader f = new BufferedReader(new FileReader("level.txt"));
		String s = "";
		for (int i = 0;i < 24; i++){
			s = f.readLine();
			for (int c = 0; c < 32; c++){
				switch (s.charAt(c)) {
				case 'p':
					player = new Player(this, c*32,i*32);
					break;
				case 'x':
					tiles[c][i] = new Block(this, c, i);
					break;
					//add more cases for more tiles if needed
				default:
					break;
				}
			}
		}
	}
	
	public void addKeyListener(KeyListener k){
		mainFrame.addKeyListener(k);
	}
	public void repaint(){
		mainFrame.repaint();
	}
	public void resetPlayerState(){
		player.resetListeners();
	}
	public void resolvePlayerMovement(){
		player.resolveMovement();
	}
	public void update(){
		//resolve player movement based on time t
		resolvePlayerMovement();
		
		resetPlayerState();
		repaint();
	}
	
	public boolean tileBelow(double xpix, double ypix){
		//assume player is 32*32
		int xin1 = (int) Math.floor(xpix/32), xin2 = (int) Math.ceil(xpix/32), yin = (int) Math.floor(ypix/32);
		return tiles[xin1][yin+1] != null || tiles[xin2][yin+1] != null;
	}
	
	//units for movement are in pixels/frame
	double interpolation = 0;
	final int TICKS_PER_SECOND = 60;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	
	@Override
	public void run() {
	    while (true) {
			double next_game_tick = System.currentTimeMillis() + SKIP_TICKS;
	        while (System.currentTimeMillis() < next_game_tick) { } //wait
	        update();
	    }
	}
	
	public static void main (String[]args){
		try {
			Window w = new Window();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
