package assignment5;

import assignment5.Critter.CritterShape;

/**
 * A criiter of "Ninja", who will suicide after 10 rounds.
 * It has one half chance to run away
 * @author Xin Geng
 *
 */
public class Critter4 extends Critter {

	private int random = getRandomInt(100);
	private int dir;
	private int count = 0;

	@Override
	public String toString() {
		return "$";
	}

	public Critter4() {
	}

	public boolean fight(String not_used) {
		if (random % 2 == 0) {
			return true;
		} else {
			dir = Critter.getRandomInt(8);
			if (this.look(dir, true) == null) {
				run(dir);
			}
			else {
				dir = Critter.getRandomInt(8);
				run(dir);
			}
			return false;
		}
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		dir = Critter.getRandomInt(8);
		walk(dir);
		count++;
		if (count >= 10) {
			this.setEnergy(0);
		} else {
			this.setEnergy(this.getEnergy() - 2);
		}
	}
	
	@Override
	public CritterShape viewShape() { 
		return CritterShape.CIRCLE; 
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.SEASHELL; 
	}
	
	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.BLACK; 
	}
}
