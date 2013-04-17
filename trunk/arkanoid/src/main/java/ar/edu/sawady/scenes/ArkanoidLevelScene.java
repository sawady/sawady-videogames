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
import com.uqbar.vainilla.colissions.Bounds;
import com.uqbar.vainilla.colissions.CollisionDetector;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.exceptions.GameException;

public class ArkanoidLevelScene extends GameScene {

	private static final Color BLOCK_COLOR = Color.GRAY;
	private static final String YOU_LOSE = "You Lose!";
	private static final String YOU_WIN = "You Win!";
	private List<Block> bloques;
	private Ball ball;
	private Player player;
	private List<Live> lives;
	private boolean finish = false;

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
		if (state.isKeyPressed(Key.Q)) {
			for(GameComponent<?> g : this.bloques){
				g.destroy();
			}
			this.bloques.clear();
		}
		if (this.bloques.isEmpty() && !this.finish) {
			this.addComponent(new FinishedGameText(YOU_WIN));
			this.ball.destroy();
			this.finish = true;			
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
		int cantBlocksX = this.getGame().getRight()
				/ (separation_x + Block.BLOCK_WIDTH);
		int offsetX = this.getGame().getRight()
				% (separation_x + Block.BLOCK_WIDTH);

		for (int i = 0; i < cantBlocksX; i++) {
			for (int j = 0; j < 5; j++) {
				Block b = new Block(i * (separation_x + Block.BLOCK_WIDTH)
						+ offsetX / 2, j * (separation_y + Block.BLOCK_HEIGHT)
						+ 10, BLOCK_COLOR);
				bloques.add(b);
				this.addComponent(b);
			}
		}
	}

	private void initializeLives() {
		this.lives = new ArrayList<Live>();
		this.lives.add(new Live());
		this.lives.add(new Live());
		this.lives.add(new Live());
		this.addComponents(this.lives);
	}

	private void createPlayer() {
		this.player = new Player(15, 450);
		this.addComponent(this.player);
	}

	private void createNewBall() {
		this.ball = new Ball(this.getBounds().getCenterX(), this.getBounds().getCenterY(), Ball.DEFAULT_SIZE);
		this.addComponent(this.ball);
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
			this.addComponent(new FinishedGameText(YOU_LOSE));
		}
	}

	public boolean checkBlockCollide(Ball aBall) {
		boolean res = false;
		Iterator<Block> it = this.bloques.iterator();
		while (!res && it.hasNext()) {
			Block b = it.next();
			if (CollisionDetector.INSTANCE.collidesRectAgainstRect(aBall.getBounds(), b.getBounds())) {
				res = true;
				b.destroy();
				this.bloques.remove(b);
			}
		}
		return res;
	}

	public boolean checkPlayerCollide(Ball aBall) {
		boolean b = CollisionDetector.INSTANCE.collidesRectAgainstRect(aBall.getBounds(), this.player.getBounds());
//		if (b) {
//			 aBall.correctPos(new Bounds(this.player.getBounds().getX()+1, this.player.getBounds().getY()-1,
//			 this.player.getBounds().getWidth(),
//			 this.player.getBounds().getHeight()));
//		}
		return b;
	}
}
