package com.uqbar.demo.components;

import java.awt.Color;
import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Circle;
import com.uqbar.vainilla.colissions.Bounds;

public class Bolita extends GameComponent<GameScene> {
	private int i = 200;
	private int j = 200;
	private int diameter = 100;
	private Color color = Color.RED;

	public Bolita(int x, int y, int aDiameter) {
		super(x, y, new Bounds(x, y, aDiameter, aDiameter));
		this.diameter = aDiameter;
		this.setAppearance(new Circle(this.color, aDiameter));
	}
	
	@Override
	public void update(DeltaState deltaState) {
		super.update(deltaState);
		
		this.move(deltaState.getDelta() * this.i, deltaState.getDelta() * this.j);
		
		this.correctPos(this.getScene().getBounds());
	
		if (this.getBounds().getBottom() >= this.getScene().getBounds().getBottom()
				|| this.getBounds().getTop() <= this.getScene().getBounds().getTop()) {
			this.j *= -1;
		}
		
		if (this.getBounds().getRight() >= this.getScene().getBounds().getRight()
				|| this.getBounds().getLeft() <= this.getScene().getBounds().getLeft()) {
			this.i *= -1;
		}
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
}