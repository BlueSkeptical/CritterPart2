package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Xin Geng
 * xg2543
 * 15465
 * Zitian Xie
 * zx2253
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	static GridPane grid = new GridPane();
	static GridPane control = new GridPane();
	static FlowPane panel = new FlowPane();
	static boolean flag = false;
	private static String myPackage;
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// stage and scene and files
			File file = new File("src/assignment5");
			String[] fileNames = file.list();
			ArrayList<String> nameLists = new ArrayList<String>();
			Stage secondaryStage = new Stage();
			Stage thirdStage = new Stage();
			Scene gridScene = new Scene(grid, 750, 750);
			Scene controlScene = new Scene(control, 600, 300);
			Scene panelScene = new Scene(panel, 300, 300);
			// children for the pane
			ChoiceBox<String> critterType = new ChoiceBox<String>();
			ChoiceBox<Integer> stepNum = new ChoiceBox<Integer>();
			ChoiceBox<Integer> frameNum = new ChoiceBox<Integer>();
			ChoiceBox<String> statsType = new ChoiceBox<String>();
			Button show = new Button("Show");
			Button make = new Button("Make");
			Button step = new Button("Step");
			Button stats = new Button("Stats");
			Button seed = new Button("Seed");
			Button quit = new Button("quit");
			Button stop = new Button("stop");
			Button animation = new Button("animation");
			TextField critterNum = new TextField();
			TextField setSeed = new TextField();
			Text statsInfo = new Text();
			Text blank = new Text("     ");
			Timeline tl = new Timeline();
			Label showL = new Label("show:");
			Label makeL = new Label("make:");
			Label stepL = new Label("step:");
			Label statsL = new Label("stats:");
			Label seedL = new Label("seed:");
			Label quitL = new Label("quit:");
			Label animationL = new Label("animation:");
			Label stopL = new Label("stop:");

			// initialization
			tl.setCycleCount(Animation.INDEFINITE);
			grid.setGridLinesVisible(true);
			primaryStage.setScene(gridScene);
			secondaryStage.setScene(controlScene);
			thirdStage.setScene(panelScene);
			primaryStage.show();
			secondaryStage.show();
			thirdStage.show();
			Critter.displayWorld(grid);
			// get the qualified critter file names
			for (String fName : fileNames) {
				String[] cName = fName.split(".java");
				String pName = myPackage.toString();
				pName += ".";
				pName += cName[0];
				java.util.List<Critter> list = Critter.getInstances(pName);
				Class cClass = Class.forName(pName);
				if (Critter.class.isAssignableFrom(cClass)) {
					nameLists.add(cName[0]);
				}
			}
			String[] critterSubNames = new String[nameLists.size()];
			for (int i = 0; i < nameLists.size(); i++) {
				critterSubNames[i] = nameLists.get(i);
			}
			// initialization for children
			critterType.getItems().addAll(critterSubNames);
			critterType.setValue(critterSubNames[0]);
			stepNum.getItems().addAll(0, 1, 10, 50, 100, 1000);
			stepNum.setValue(0);
			frameNum.getItems().addAll(1, 2, 5, 10, 20, 50, 100);
			frameNum.setValue(1);
			statsType.getItems().addAll(critterSubNames);
			statsType.setValue(critterSubNames[0]);
			// buttons
			show.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Critter.displayWorld(grid);
					statsInfo.setText(updateAllStats(critterType));
				}
			});

			make.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String nums = new String();
					if (!critterNum.getText().isEmpty()) {
						nums = critterNum.getText();
						int num = Integer.parseInt(nums);
						String name = critterType.getValue();
						while (num > 0) {
							try {
								Critter.makeCritter(name);
							} catch (InvalidCritterException exc) {
							}
							num--;
						}
						Critter.displayWorld(grid);
					}
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
					Critter.displayWorld(grid);
					statsInfo.setText(updateAllStats(critterType));
				}

			});

			stats.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					String name = statsType.getValue();
					try {
						// make the qualified name
						String cName = myPackage.toString();
						String output = new String();
						cName += ".";
						cName += name;
						java.util.List<Critter> list = Critter.getInstances(cName);
						Class placeHolder = Class.forName(cName);
						// run stats 2 for one selected critter
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
					if (!setSeed.getText().isEmpty()) {
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

			animation.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					int num = frameNum.getValue();
					// update animation for each frame
					KeyFrame updateWorld = new KeyFrame(Duration.seconds(.400), new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							for (int i = 0; i != num; i++) {
								Critter.worldTimeStep();
							}
							Critter.displayWorld(grid);
							statsInfo.setText(updateAllStats(critterType));
						}
					});

					tl.getKeyFrames().add(updateWorld);
					tl.play();
				}
			});

			stop.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					tl.stop();
				}
			});
			// add children to pane
			control.add(showL, 0, 0);
			control.add(show, 0, 1);
			control.add(makeL, 2, 0);
			control.add(critterType, 2, 1);
			control.add(critterNum, 2, 2);
			control.add(make, 2, 3);
			control.add(stepL, 3, 0);
			control.add(stepNum, 3, 1);
			control.add(step, 3, 2);
			control.add(statsL, 4, 0);
			control.add(statsType, 4, 1);
			control.add(stats, 4, 2);
			control.add(seedL, 0, 4);
			control.add(setSeed, 0, 5);
			control.add(seed, 0, 6);
			control.add(quitL, 0, 7);
			control.add(quit, 0, 8);
			control.add(animationL, 2, 4);
			control.add(frameNum, 2, 5);
			control.add(animation, 2, 6);
			control.add(stopL, 3, 4);
			control.add(stop, 3, 5);
			panel.getChildren().add(statsInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function updates all critters' status
	 * 
	 * @param critterType
	 * @return the result
	 */
	private static String updateAllStats(ChoiceBox<String> critterType) {
		String output = new String();
		try {
			for (String name : critterType.getItems()) {
				String cName = myPackage.toString();
				cName += ".";
				cName += name;
				java.util.List<Critter> list = Critter.getInstances(cName);
				Class placeHolder = Class.forName(cName);
				java.lang.reflect.Method theMethod = placeHolder.getMethod("runStats2", java.util.List.class);
				output += (String) theMethod.invoke(placeHolder, list);
				output += "\n";
			}
		} catch (Exception exc) {
		}
		return output;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
