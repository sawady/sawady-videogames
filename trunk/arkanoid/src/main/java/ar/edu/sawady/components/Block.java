package ar.edu.sawady.components;

import java.awt.Color;

import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Rectangle;

public class Block extends GameComponent<GameScene> {

	public static final int BLOCK_WIDTH = 50;
	public static final int BLOCK_HEIGHT = 20;

	public Block(double x, double y, Color color) {
		super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
		this.setAppearance(new Rectangle(color, BLOCK_WIDTH, BLOCK_HEIGHT));
	}

}
