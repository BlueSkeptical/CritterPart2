package assignment4;

import assignment4.Critter.CritterShape;
import assignment4.Main;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Painter {
	/*
	 * Returns a square or a circle, according to shapeIndex
	 */
	public static double size;

	private static void setSize() {
		size = Math.max(Params.world_height, Params.world_width);
		size = 750 / size - 1;
		if (size < 15) {
			size = 15;
		}
	}

	public static void paintGridLines(GridPane grid) {
		setSize();
		grid.getChildren().clear();
		for (int r = 0; r < Params.world_height; r++) {
			for (int c = 0; c < Params.world_width; c++) {
				Shape s = new Rectangle(size, size);
				s.setFill(null);
				s.setStroke(Color.PURPLE);
				grid.add(s, c, r);
			}
		}
	}

	static Shape getIcon(int shapeIndex) {
		Shape s = null;
		int size = 100;

		switch (shapeIndex) {
		case 0:
			s = new Rectangle(size, size);
			s.setFill(javafx.scene.paint.Color.RED);
			break;
		case 1:
			s = new Circle(size / 2);
			s.setFill(javafx.scene.paint.Color.GREEN);
			break;
		}
		// set the outline of the shape
		s.setStroke(javafx.scene.paint.Color.BLUE); // outline
		return s;
	}

	/*
	 * Paints the shape on a grid.
	 */
	public static void paint(Critter c, CritterShape shape, Color outlineColor, Color fillColor, GridPane grid) {
		Shape s = null;
		Polygon p = null;
		switch (shape) {
		case CIRCLE:
			s = new Circle(size / 2);
			break;
		case SQUARE:
			s = new Polygon();
			p = (Polygon) s;
			p.getPoints().addAll(new Double[] { 1.0, 1.0, size - 1, 1.0, size - 1, size - 1, 1.0, size - 1 });
			s = p;
			break;
		case TRIANGLE:
			s = new Polygon();
			p = (Polygon) s;
			p.getPoints().addAll(new Double[] { size / 2 - 2, 0.0, 0.0, size - 2, size - 2, size - 2 });
			s = p;
			break;
		case DIAMOND:
			s = new Polygon();
			p = (Polygon) s;
			p.getPoints().addAll(new Double[] { size / 2 - 1, 1.0, 1.0, size / 2 - 1, size / 2 - 1, size - 1, size - 1,
					size / 2 - 1 });
			s = p;
			break;
		case STAR:
			s = new Polygon();
			p = (Polygon) s;
			p.getPoints()
					.addAll(new Double[] { size / 2 - 2, 1.0, 3 * size / 8 - 2, size / 3 - 2, 1.0, size / 3 - 2,
							size / 4 - 2, 2 * size / 3 - 2, size / 5 - 2, size - 2, size / 2 - 2, 4 * size / 5 - 2,
							4 * size / 5 - 2, size - 2, 3 * size / 4 - 2, 2 * size / 3 - 2, size - 2, size / 3 - 2,
							5 * size / 8 - 2, size / 3 - 2 });
			s = p;
			break;
		}
		s.setFill(fillColor);
		s.setStroke(outlineColor);
		grid.add(s, c.getX(), c.getY());
	}

}
