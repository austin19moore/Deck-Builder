package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/*
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * Main, creates scene, loads Main.fxml and places it onto the stage
 */
public class Main extends Application {
	
	// + stage : A Stage used in JavaFX applications.
	public static Stage stage;
	
	/**
	 * This function is being overridden from the Application abstract and is used
	 * to begin the JavaFX application.
	 * start : Stage, this -> void
	 * @param primaryStage: This is the stage representation
	 * of the of the application we are creating
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// give access to the other controllers to this primary stage!
			stage = primaryStage;
			
			// Connect to the FXML (contains our layout) and load it in
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("/application/view/Main.fxml") );
			AnchorPane layout = (AnchorPane) loader.load();
			
			// Put the layout onto the scene
			Scene scene = new Scene( layout );
			
			// Set the scene on the stage
			primaryStage.setScene(scene);
			primaryStage.setTitle("Deck-Builder");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* This function is being overridden from the Application abstract and used to
	* define the way the application shuts down (specifically ending all the threads along with the application)
	**/
	@Override
	public void stop() { 
		System.exit(0);
	}
	
	/**
	 * The main method used to run my program
	 * @param args: Arguments from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
