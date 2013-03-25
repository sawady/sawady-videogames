package com.uqbar.demo.components;

import java.awt.Color;
import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Circle;

public class Bolita extends GameComponent<GameScene> {
	private int speedx = 200;
	private int speedy = 200;
	private int diameter = 30;
	private Color color = Color.RED;

	public Bolita(int x, int y) {
		super(x, y);
		this.setAppearance(new Circle(this.color, this.diameter));
	}

	@Override
	public void update(DeltaState deltaState) {
		super.update(deltaState);

		this.move(deltaState.getDelta() * this.speedx,
				deltaState.getDelta() * this.speedy);

		if (this.getY() >= this.getGame().getDisplayHeight() - this.diameter) {
			this.speedy = this.speedy * -1;
		}

		if (this.getY() <= 0) {
			this.speedy = this.speedy * -1;
		}

		if (this.getX() <= 0) {
			this.speedx = this.speedx * -1;
		}

		if (this.getX() >= this.getGame().getDisplayWidth() - this.diameter) {
			this.speedx = this.speedx * -1;
		}
	}
}