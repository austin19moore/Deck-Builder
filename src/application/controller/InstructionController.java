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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextAlignment;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The class representing the Controller for the instruction view
 * Handles back button, displaying instructions, and moving player to the game
 */
public class InstructionController implements EventHandler<ActionEvent>, Initializable{
	
	/**
	* Instructions: the TextArea for instructions
	* ConquerButton: The button to begin playing the game
	*/
	@FXML
	TextArea Instructions;
	
	@FXML
	Button ConquerButton;
	
	/**
	* initialize: method that runs after the class has been constructed and the @FXML has been injected
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	
		Instructions.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
		
		ConquerButton.setStyle("-fx-background-color:#134a01; -fx-text-fill:#b8c248;");
		
	}
	
	/**
	* backButton: method enumerating the effects of clicking the back button
	*/
	public void backButton(ActionEvent arg0) {
		// move the user to the Main view
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
			System.out.println("Switching user to Main view");
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* handleConquer: method enumerating the effects of clicking the Conquer button
	*/
	public void handleConquer(ActionEvent arg0) {
		
		// move the user to the Main view
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Game.fxml"));
			System.out.println("Switching user to GamePlaying");
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
