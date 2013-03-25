package com.uqbar.vainilla;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import java.awt.Graphics2D;
import com.uqbar.vainilla.appearances.Appearance;
import com.uqbar.vainilla.appearances.Invisible;

public class GameComponent<SceneType extends GameScene> {

	private SceneType scene;
	private Appearance appearance;
	private double x;
	private double y;
	private double width;
	private double height;
	private int z;
	private boolean destroyPending;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public GameComponent() {
		this(new Invisible(), 0, 0, 0, 0);
	}

	public GameComponent(double x, double y) {
		this(new Invisible(), x, y, 0, 0);
	}

	public GameComponent(double x, double y, double w, double h) {
		this(new Invisible(), x, y, w, h);
	}

	public GameComponent(Appearance appearance, double x, double y, double w,
			double h) {
		this.setAppearance(appearance);
		this.setX(x);
		this.setY(y);
		this.setWidth(w);
		this.setHeight(h);
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	public Game getGame() {
		return this.getScene().getGame();
	}

	public double getTopBorder() {
		return this.getY();
	}

	public double getBottomBorder() {
		return this.getY() + this.getHeight();
	}

	public double getLeftBorder() {
		return this.getX();
	}

	public double getRightBorder() {
		return this.getX() + this.getWidth();
	}

	// ****************************************************************
	// ** INITIALIZATION
	// ****************************************************************

	public void onSceneActivated() {
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	public void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
	}

	public void insideMove(double dx, double dy) {
		this.move(dx, dy);
		this.correctPosToInsideBorders();
	}

	private void correctPosToInsideBorders() {
		if (this.getBottomBorder() > this.getGame().getBottomBorder()) {
			this.setY(this.getGame().getBottomBorder() - this.getHeight());
		}
		if (this.getTopBorder() < this.getGame().getTopBorder()) {
			this.setY(this.getGame().getTopBorder());
		}
		if (this.getRightBorder() > this.getGame().getRightBorder()) {
			this.setX(this.getGame().getRightBorder() - this.getWidth());
		}
		if (this.getLeftBorder() < this.getGame().getLeftBorder()) {
			this.setX(this.getLeftBorder());
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

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getZ() {
		return this.z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isDestroyPending() {
		return this.destroyPending;
	}

	protected void setDestroyPending(boolean destroyPending) {
		this.destroyPending = destroyPending;
	}

}