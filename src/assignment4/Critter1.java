package assignment4;

import assignment4.Critter.CritterShape;

/**
 * The critter is a "crab" which can only move horizontally, one half chance of
 * choosing to fight
 * 
 * @author Zitian Xie
 *
 */
public class Critter1 extends Critter {

	private int random = getRandomInt(100);

	@Override
	public String toString() {
		return "=";
	}

	public Critter1() {
	}

	public boolean fight(String not_used) {
		if (random % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doTimeStep() {
		if (random % 2 == 0) {
			if (this.look(0, true) == null) {
				run(0);
			} else {
				run(4);
			}
		} else {
			if (this.look(4, true) == null) {
				run(4);
			} else {
				run(0);
			}
		}
	}
	
	@Override
	public CritterShape viewShape() { 
		return CritterShape.STAR; 
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.CORNFLOWERBLUE; 
	}
	
	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.FLORALWHITE; 
	}
}
