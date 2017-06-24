package game;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Block extends Tile{
		
	public Block(Window frame, int x, int y) throws IOException{
		super(frame, x, y, ImageIO.read(new File("block.png")));		
	}

}
