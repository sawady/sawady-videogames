package com.uqbar.vainilla;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.Graphics2D;

import com.uqbar.vainilla.appearances.Appearance;
import com.uqbar.vainilla.appearances.Invisible;
import com.uqbar.vainilla.colissions.Bounds;
import com.uqbar.vainilla.colissions.CollisionDetector;

public class GameComponent<SceneType extends GameScene> {

	private SceneType scene;
	private Appearance appearance;
	private double x;
	private double y;
	private Bounds bounds;
	private int z;
	private boolean destroyPending;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public GameComponent() {
		this(new Invisible(), 0, 0, new Bounds(0,0,0,0));
	}

	public GameComponent(double x, double y) {
		this(new Invisible(), x, y, new Bounds(0,0,0,0));
	}

	public GameComponent(double x, double y, Bounds bound) {
		this(new Invisible(), x, y, bound);
	}

	public GameComponent(Appearance appearance, double x, double y, Bounds bound) {
		this.setAppearance(appearance);
		this.x = x;
		this.y = y;
		this.setBounds(bound);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public Game getGame() {
		return this.getScene().getGame();
	}

	// ****************************************************************
	// ** INITIALIZATION
	// ****************************************************************

	public void onSceneActivated() {
	}
	
	// ****************************************************************
	// ** COLLISION OPERATIONS
	// ****************************************************************
	
	public boolean collide(GameComponent<?> g){
		return CollisionDetector.INSTANCE.collidesRectAgainstRect(this.getBounds(), g.getBounds());
	}
	
	// ****************************************************************
	// ** MOVEMENT OPERATIONS
	// ****************************************************************

	public void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		this.getBounds().addX(dx);
		this.getBounds().addY(dy);
	}
	
	public void changePosX(double x) {
		changePos(x, this.getY());
	}
	
	public void changePosY(double y) {
		changePos(this.getX(), y);
	}
	
	public void changePos(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.getBounds().addX(this.getX() - this.getBounds().getX());
		this.getBounds().addY(this.getBounds().getY() - this.getY());
	}
	
	public void correctPosToOutside(Bounds other) {
		if (this.getBounds().getBottom() > other.getTop()) {
			this.move(0, other.getTop() - this.getBounds().getBottom());
		} else if (this.getBounds().getTop() < other.getBottom()) {
			this.move(0, this.getBounds().getTop() - other.getBottom());
		} else if (this.getBounds().getRight() > other.getLeft()) {
			this.move(this.getBounds().getRight() - other.getLeft(), 0);
		} else if (this.getBounds().getLeft() < other.getRight()) {
			this.move(this.getBounds().getLeft() - other.getRight(), 0);
		}
	}

	public void correctPosToInside(Bounds other) {
		if (this.getBounds().getBottom() > other.getBottom()) {
			this.move(0,  other.getBottom() - this.getBounds().getBottom());
		}
		if (this.getBounds().getTop() < other.getTop()) {
			this.move(0, other.getTop() - this.getBounds().getTop());
		}
		if (this.getBounds().getRight() > other.getRight()) {
			this.move(other.getRight() - this.getBounds().getRight(), 0);
		}
		if (this.getBounds().getLeft() < other.getLeft()) {
			this.move(other.getLeft() - this.getBounds().getLeft(), 0);
		}
	}

	public void destroy() {
		this.setDestroyPending(true);
	}

	// ****************************************************************
	// ** ALIGNMENT OPERATIONS
	// ****************************************************************

	public void alignTopTo(double y) {
		this.move(0, y - this.getY());
	}

	public void alignLeftTo(double x) {
		this.move(x - this.getX(), 0);
	}

	public void alignBottomTo(double y) {
		this.alignTopTo(y + this.getAppearance().getHeight());
	}

	public void alignRightTo(double x) {
		this.alignRightTo(x + this.getAppearance().getWidth());
	}

	public void alignHorizontalCenterTo(double x) {
		this.alignLeftTo(x - this.getAppearance().getWidth() / 2);
	}

	public void alignVerticalCenterTo(double y) {
		this.alignTopTo(y - this.getAppearance().getHeight() / 2);
	}

	public void alignCloserBoundTo(GameComponent<?> target) {
		Appearance ownBounds = this.getAppearance();
		Appearance targetBounds = target.getAppearance();
		double bottomDistance = abs(ownBounds.getHeight() + this.getY()
				- target.getY());
		double targetRight = target.getX() + targetBounds.getWidth();
		double leftDistance = abs(this.getX() - targetRight);
		double targetBottom = target.getY() + targetBounds.getHeight();
		double topDistance = abs(this.getY() - targetBottom);
		double rightDistance = abs(this.getX() + ownBounds.getWidth()
				- target.getX());
		double minDistance = min(bottomDistance,
				min(leftDistance, min(topDistance, rightDistance)));

		if (minDistance == bottomDistance) {
			this.alignBottomTo(target.getY());
		} else if (minDistance == leftDistance) {
			this.alignLeftTo(targetRight);
		} else if (minDistance == topDistance) {
			this.alignTopTo(targetBottom);
		} else {
			this.alignRightTo(target.getX());
		}
	}

	// ****************************************************************
	// ** GAME OPERATIONS
	// ****************************************************************

	public void render(Graphics2D graphics) {
		this.getAppearance().render(this, graphics);
	}

	public void update(DeltaState deltaState) {
		this.getAppearance().update(deltaState.getDelta());
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public SceneType getScene() {
		return this.scene;
	}

	protected void setScene(SceneType scene) {
		this.scene = scene;
	}

	public Appearance getAppearance() {
		return this.appearance;
	}

	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	public double getX() {
		return this.x;
	}

	private void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	private void setY(double y) {
		this.y = y;
	}

	public int getZ() {
		return this.z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public boolean isDestroyPending() {
		return this.destroyPending;
	}

	protected void setDestroyPending(boolean destroyPending) {
		this.destroyPending = destroyPending;
	}

}