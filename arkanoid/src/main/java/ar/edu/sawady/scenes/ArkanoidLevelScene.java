package ar.edu.sawady.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.edu.sawady.components.Ball;
import ar.edu.sawady.components.Block;
import ar.edu.sawady.components.FinishedGameText;
import ar.edu.sawady.components.Player;
import ar.edu.sawady.components.Live;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.colissions.CollisionDetector;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.exceptions.GameException;

public class ArkanoidLevelScene extends GameScene {

	private List<Block> bloques;
	private Ball ball;
	private Player player;
	private List<Live> lives;

	@Override
	public void onSetAsCurrent() {
		super.onSetAsCurrent();
		initializeComponents();
	}

	@Override
	public void resetScene() {
		super.resetScene();
		this.initializeComponents();
	}

	@Override
	protected void update(DeltaState state) {
		super.update(state);
		if (state.isKeyPressed(Key.SPACE)) {
			this.resetScene();
		}
	}

	private void initializeComponents() {
		initializeBlocks();
		initializeLives();
		createNewBall();
		createPlayer();
	}

	private void initializeBlocks() {
		bloques = new ArrayList<Block>();
		int separation_x = 5;
		int separation_y = 5;
		int cantBlocksX = this.getGame().getRightBorder()
				/ (separation_x + Block.BLOCK_WIDTH);
		int offsetX = this.getGame().getRightBorder()
				% (separation_x + Block.BLOCK_WIDTH);

		for (int i = 0; i < cantBlocksX; i++) {
			for (int j = 0; j < 5; j++) {
				Block b = new Block(i * (separation_x + Block.BLOCK_WIDTH)
						+ offsetX / 2, j * (separation_y + Block.BLOCK_HEIGHT)
						+ 10, Color.BLUE);
				bloques.add(b);
				this.addComponent(b);
			}
		}
	}

	private void initializeLives() {
		this.lives = new ArrayList<Live>();
		Live l = new Live();
		this.addComponent(l);
		this.lives.add(l);
	}

	private void createPlayer() {
		this.player = new Player(15, 450);
		this.addComponent(this.player);
	}

	private void createNewBall() {
		this.ball = new Ball(this.getGame().getDisplayWidth() / 2, this
				.getGame().getDisplayHeight() / 2);
		this.addComponent(this.ball);
	}

	public void blockDown() {
		if (this.bloques.isEmpty()) {
			this.addComponent(new FinishedGameText("You Win!"));
		}
	}

	public void liveDown() {

		Live l = this.lives.get(0);
		this.lives.remove(0);
		l.destroy();
		this.ball.destroy();

		if (!this.lives.isEmpty()) {
			this.createNewBall();
		} else {
			this.player.destroy();
			this.addComponent(new FinishedGameText("You Lose!"));
		}
	}

	public boolean checkBlockCollide(Ball aBall) {
		boolean res = false;
		Iterator<Block> it = this.bloques.iterator();
		while (!res && it.hasNext()) {
			Block b = it.next();
			if (CollisionDetector.INSTANCE.collidesRectAgainstRect(
					aBall.getX(), aBall.getY(), aBall.getWidth(),
					aBall.getHeight(), b.getX(), b.getY(), b.getWidth(),
					b.getHeight())) {
				res = true;
				b.destroy();
				this.bloques.remove(b);
			}
		}
		return res;
	}

	public boolean checkPlayerCollide(Ball aBall) {
		boolean b = CollisionDetector.INSTANCE.collidesRectAgainstRect(
				aBall.getX(), aBall.getY(), aBall.getWidth(),
				aBall.getHeight(), this.player.getX(), this.player.getY(),
				this.player.getWidth(), this.player.getHeight());
		if (b) {
			aBall.correctPos(this.player.getX(), this.player.getY(),
					this.player.getWidth(), this.player.getHeight());
		}
		return b;
	}
}
