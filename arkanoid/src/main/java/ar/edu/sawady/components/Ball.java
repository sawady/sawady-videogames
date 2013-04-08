package ar.edu.sawady.components;

import java.awt.Color;

import ar.edu.sawady.scenes.ArkanoidLevelScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Circle;

public class Ball extends GameComponent<ArkanoidLevelScene> {
	private int i = 200;
	private int j = 200;
	private int diameter = 50;
	private Color color = Color.RED;

	public Ball(int x, int y) {
		super(x, y);
		this.setWidth(this.diameter);
		this.setHeight(this.diameter);
		this.setAppearance(new Circle(this.color, this.diameter));
	}

	@Override
	public void update(DeltaState deltaState) {
		super.update(deltaState);
		
		this.insideMove(deltaState.getDelta() * this.i,
				deltaState.getDelta() * this.j);
	
		if (this.getTopBorder() <= this.getGame().getTopBorder()
				|| this.getScene().checkBlockCollide(this)
				|| this.getScene().checkPlayerCollide(this)
				|| this.getBottomBorder() >= this.getGame().getBottomBorder()) {
			this.j *= -1;
		}
		
		if (this.getRightBorder() >= this.getGame().getRightBorder()
				|| this.getLeftBorder() <= this.getGame().getLeftBorder()) {
			this.i *= -1;
		}
		
//		if(this.getBottomBorder() >= this.getGame().getBottomBorder()) {
//			this.getScene().liveDown();
//		}
		
	}
}