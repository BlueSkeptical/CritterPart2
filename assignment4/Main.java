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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	static GridPane grid = new GridPane();
	static FlowPane control = new FlowPane();
	static FlowPane panel = new FlowPane();
	private static String myPackage;
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Stage secondaryStage = new Stage();
			Stage thirdStage = new Stage();
			Scene gridScene = new Scene(grid, 800, 800);
			Scene controlScene = new Scene(control, 100, 300);
			Scene panelScene = new Scene(panel, 300, 300);
			ChoiceBox<String> critterType = new ChoiceBox<String>();
			ChoiceBox<Integer> critterNum = new ChoiceBox<Integer>();
			ChoiceBox<Integer> stepNum = new ChoiceBox<Integer>();
			ChoiceBox<String> statsType = new ChoiceBox<String>();
			Button show = new Button("Show");
			Button make = new Button("Make");
			Button step = new Button("Step");
			Button stats = new Button("Stats");
			Button seed = new Button("Seed");
			Button quit = new Button("quit");
			TextField setSeed = new TextField();
			Text statsInfo = new Text();
			Text blank = new Text("\n");
			grid.setGridLinesVisible(true);
			primaryStage.setScene(gridScene);
			secondaryStage.setScene(controlScene);
			thirdStage.setScene(panelScene);
			primaryStage.show();
			secondaryStage.show();
			thirdStage.show();
			Critter.displayWorld(grid);
			critterType.getItems().addAll("Craig", "Algae", "Critter1", "Critter2", "Critter3", "Critter4",
					"AlgaephobicCritter", "TragicCritter");
			critterType.setValue("Craig");
			critterNum.getItems().addAll(0, 1, 10, 50, 100, 1000);
			critterNum.setValue(0);
			stepNum.getItems().addAll(0, 1, 10, 50, 100, 1000);
			stepNum.setValue(0);
			statsType.getItems().addAll("Craig", "Algae", "Critter1", "Critter2", "Critter3", "Critter4",
					"AlgaephobicCritter", "TragicCritter");
			statsType.setValue("Craig");

			show.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Critter.displayWorld(grid);
					String output = new String();
					try {
						for (String name : critterType.getItems()) {
							String cName = myPackage.toString();
							cName += ".";
							cName += name;
							java.util.List<Critter> list = Critter.getInstances(cName);
							Class placeHolder = Class.forName(cName);
							java.lang.reflect.Method theMethod = placeHolder.getMethod("runStats2",
									java.util.List.class);
							output += (String) theMethod.invoke(placeHolder, list);
							output += "\n";
						}
						statsInfo.setText(output);
					} catch (Exception exc) {

					}
				}
			});

			make.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String name = critterType.getValue();
					int num = critterNum.getValue();
					while (num > 0) {
						try {
							Critter.makeCritter(name);
						} catch (InvalidCritterException exc) {
						}
						num--;
					}
					critterNum.setValue(0);
				}
			});

			step.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					int num = stepNum.getValue();
					while (num > 0) {
						Critter.worldTimeStep();
						num--;
					}
					stepNum.setValue(0);
				}
			});

			stats.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String name = statsType.getValue();
					try {
						String cName = myPackage.toString();
						String output = new String();
						cName += ".";
						cName += name;
						java.util.List<Critter> list = Critter.getInstances(cName);
						Class placeHolder = Class.forName(cName);
						java.lang.reflect.Method theMethod = placeHolder.getMethod("runStats2", java.util.List.class);
						output = (String) theMethod.invoke(placeHolder, list);
						statsInfo.setText(output);
					} catch (Exception exc) {
					}
				}
			});
			
			seed.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String num = new String();
					if(!setSeed.getText().isEmpty()) {
						num = setSeed.getText();
						int seed = Integer.parseInt(num);
						Critter.setSeed(seed);
					}
				}
			});
			
			quit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					System.exit(0);
				}
			});
			
			
			control.getChildren().add(show);
			control.getChildren().add(blank);
			control.getChildren().add(critterType);
			control.getChildren().add(critterNum);
			control.getChildren().add(make);
			control.getChildren().add(stepNum);
			control.getChildren().add(step);
			control.getChildren().add(statsType);
			control.getChildren().add(stats);
			control.getChildren().add(setSeed);
			control.getChildren().add(seed);
			control.getChildren().add(quit);
			panel.getChildren().add(statsInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
