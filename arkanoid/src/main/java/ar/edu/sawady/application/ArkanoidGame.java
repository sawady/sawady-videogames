package ar.edu.sawady.application;

import java.awt.Dimension;

import ar.edu.sawady.scenes.ArkanoidLevelScene;

import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class ArkanoidGame extends Game {
	
	public ArkanoidGame() {
		super();
		this.setCurrentScene(new ArkanoidLevelScene());
	}

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
		
	}

	public static void main(String[] args) {
		new DesktopGameLauncher(new ArkanoidGame()).launch();
	}
}