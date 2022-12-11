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
import javafx.scene.text.TextAlignment;

/*
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder Project
 * Fall 2022
 * 
 * CreditsController.java, Controller for Credits.fxml
 * handles back button
 */
public class CreditsController implements EventHandler<ActionEvent>, Initializable{
	
	// declare objects in Settings.fxml here
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
	}
	
	public void backButton(ActionEvent arg0) {
		
		// move the user to the Main.fxml view
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
			System.out.println("Switching user to Main view");
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
