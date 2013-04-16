package ar.edu.sawady.components;

import java.awt.Color;

import ar.edu.sawady.scenes.ArkanoidLevelScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Circle;
import com.uqbar.vainilla.colissions.Bounds;

public class Ball extends GameComponent<ArkanoidLevelScene> {
	public static final int DEFAULT_SIZE = 20;
	private int i = 250;
	private int j = 250;
	private int diameter;
	private Color color = Color.RED;

	public Ball(double x, double y, int aDiameter) {
		super(x, y, new Bounds(x, y, aDiameter, aDiameter));
		this.diameter = aDiameter;
		this.setAppearance(new Circle(this.color, aDiameter));
	}

	@Override
	public void update(DeltaState deltaState) {
		super.update(deltaState);
		
		this.move(deltaState.getDelta() * this.i, deltaState.getDelta() * this.j);
		this.correctPos(this.getScene().getBounds());
		
		if (this.getBounds().getTop() <= this.getScene().getBounds().getTop()
				|| this.getScene().checkBlockCollide(this)
				|| this.getScene().checkPlayerCollide(this)
				|| this.getBounds().getBottom() >= this.getScene().getBounds().getBottom()) {
			this.j *= -1;
		}
		
		if (this.getBounds().getRight() >= this.getScene().getBounds().getRight()
				|| this.getBounds().getLeft() <= this.getScene().getBounds().getLeft()) {
			this.i *= -1;
		}
		
		if(this.getBounds().getBottom() >= this.getScene().getBounds().getBottom()) {
			this.getScene().liveDown();
		}
		
	}
}