package ar.edu.sawady.application;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import ar.edu.sawady.components.Block;
import ar.edu.sawady.components.Bolita;

import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class ArkanoidGame extends Game {

	private List<Block> bloques;

	@Override
	public Dimension getDisplaySize() {
		return new Dimension(640, 480);
	}

	@Override
	public String getTitle() {
		return "Arkanoid";
	}

	@Override
	protected void initializeResources() {
		
	}

	@Override
	protected void setUpScenes() {
		// Abstraer a un objeto colleccion de bloques
		bloques = new ArrayList<Block>();
		int separation_x = 5;
		int separation_y = 5;
		int cantBlocksX = this.getRightBorder() / (separation_x + Block.BLOCK_WIDTH);
		int offsetX = this.getRightBorder() % (separation_x + Block.BLOCK_WIDTH);
		
		for (int i = 0; i < cantBlocksX; i++) {
			for (int j = 0; j < 5; j++) {
				 Block b = new Block(i * (separation_x + Block.BLOCK_WIDTH) + offsetX / 2, 
						 			 j * (separation_y + Block.BLOCK_HEIGHT) + 10, 
						 			 Color.BLUE);
				 bloques.add(b);
				 this.getCurrentScene().addComponent(b);
			}
		}
	}

	public static void main(String[] args) {
		new DesktopGameLauncher(new ArkanoidGame()).launch();
	}
}