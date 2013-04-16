package com.uqbar.demo;

import java.awt.Dimension;
import com.uqbar.demo.components.Bolita;
import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class DemoGame extends Game {
	@Override
	public Dimension getDisplaySize() {
		return new Dimension(640, 480);
	}

	@Override
	public String getTitle() {
		return "Demo de Vainilla";
	}

	@Override
	protected void initializeResources() {
	}

	@Override
	protected void setUpScenes() {
		this.getCurrentScene().addComponent(new Bolita(200, 200, 100));
	}

	public static void main(String[] args) {
		new DesktopGameLauncher(new DemoGame()).launch();
	}
}