package ar.edu.sawady.components;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Rectangle;
import com.uqbar.vainilla.colissions.Bounds;
import com.uqbar.vainilla.events.constants.Key;

public class Player extends GameComponent<GameScene> {
	
	public static final int DEFAULT_WIDTH = 40;
	public static final int DEFAULT_HEIGHT = 15;
	
	public Player(double x, double y) {
		super(x, y, new Bounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT));
		this.setAppearance(new Rectangle(Color.GREEN, DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
	
	@Override
	public void update(DeltaState deltaState) {
		super.update(deltaState);
		
		Point2D.Double mouse_pos = deltaState.getCurrentMousePosition();
		
		this.changePos(mouse_pos.getX(), this.getY());
		
		if(deltaState.isKeyPressed(Key.LEFT)) {
			
		} else if(deltaState.isKeyPressed(Key.RIGHT)) {
			
		} else if(deltaState.isKeyPressed(Key.UP)) {
			
		}
		
	}

}
