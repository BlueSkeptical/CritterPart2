package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Xin Geng
 * xg2543
 * 15465
 * Zitian Xie
 * zx2253
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

import java.util.ArrayList;
import java.util.List;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	static GridPane grid = new GridPane();

	@Override
	public void start(Stage primaryStage) {
		try {			

			grid.setGridLinesVisible(true);

			Scene scene = new Scene(grid, 500, 500);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			Critter.displayWorld(grid);
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
