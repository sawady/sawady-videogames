package ar.edu.sawady.components;

import java.awt.Color;
import java.awt.Font;

import ar.edu.sawady.scenes.ArkanoidLevelScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.appearances.Label;

public class GameText extends GameComponent<ArkanoidLevelScene> {

	private String text;

	public GameText(String text) {
		super();
		this.text = text;
		this.setAppearance(new Label(new Font(Font.SANS_SERIF, Font.BOLD, 32), Color.black, text));
	}

	@Override
	public void update(DeltaState deltaState) {
		super.update(deltaState);
		this.changePos(this.getScene().getBounds().getCenterX(), this.getScene().getBounds().getCenterY());
		this.alignHorizontalCenterTo(this.getScene().getBounds().getCenterX());
	}

}
