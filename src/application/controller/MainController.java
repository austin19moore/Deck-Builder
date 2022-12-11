package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The Class representing the controller for the Main Menu. Initializes the EventHandler interface
 * handles moving player to selected view, or quitting the application
 */
public class MainController implements EventHandler<ActionEvent>{
	
	/**
	* newGameButton: Method that enumerates the effect of the new Game Button
	*/
	public void newGameButton(ActionEvent arg0) {
		
		// move user to Game.fxml view
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Instruction.fxml"));
			System.out.println("Switching user to Instruction view");
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* decksButton: Method that enumerates the effect of the deckbuilder button
	*/
	public void decksButton(ActionEvent arg0) {
		
		// move the user to the Decks view
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Decks.fxml"));
			System.out.println("Switching user to Decks view");
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* settingsButton: Method that enumerates the effect of the settings button
	*/ 
	public void creditsButton(ActionEvent arg0) {
		
		// move the user to the Settings view
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Credits.fxml"));
			System.out.println("Switching user to Credits view");
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* quitButton: method enumerates the effect of clicking on the quit button
	*/
	public void quitButton(ActionEvent arg0) {

		System.out.println("Exiting...");
		System.exit(0);
	}
	
	public void handle(ActionEvent arg0) {

		System.out.println("Switching user to ??? view");
	}
	
}
