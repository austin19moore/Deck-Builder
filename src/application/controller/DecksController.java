package application.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.model.Card;
import application.model.CardGenerator;
import application.model.Deck;
import application.model.TroopCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/*
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder Project
 * Fall 2022
 * 
 * DecksController.java, Controller for Decks.fxml
 * handles changing the cards in player decks, initialization of objects in card and player 1/2 listviews, displays selected cards in each list view, and handles replacement, adding, and removing cards
 */
public class DecksController implements EventHandler<ActionEvent>, Initializable{
	
	// declare objects in Decks.fxml here
	Deck player1 = new Deck("data/player1.csv");
	Deck player2 = new Deck("data/player2.csv");
	
	CardGenerator CG = new CardGenerator();
	
	LinkedList<Pane> cardPane;
	
	@FXML
	ListView<String> decksList;
	ObservableList<String> cardList = FXCollections.observableArrayList();
	int decksListIndex;
	
	@FXML
	ListView<String> playerList;
	ObservableList<String> playerCards = FXCollections.observableArrayList();
	int playerListIndex;
	
	@FXML
	AnchorPane apane;
	
	@FXML
	Label playerLabel;
	
	@FXML
	Pane selectedCard;
	int selectCard;
	
	@FXML
	Pane playerSelectedCard;
	int playerSelectCard;
	
	int playerNum;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
		playerLabel.setText("Player 1");
		playerNum = 1;
		System.out.println(player1.getCards().toString());
		//CG.loadCardLists();
		
		// load all cards from cards.csv into decksList
		// load player1 cards into playerCard panes
		loadCards(1);
		
		player1.getCards();
		CG.getCardList().keySet();
		for (int i: CG.getCardList().keySet()) {
			cardList.add(String.valueOf(CG.getCardList().get(i).getCardNumber()) + ": " + CG.getCardList().get(i).getName());
		}
		
		decksList.setItems(cardList);
		
		
	}
	
	// will need to add methods to handle adding each card to player deck (happen on playerCard pane click, adding selectedCard to deck in position)
	
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
	
	// switches player between player 1 and player 2
	public void switchPlayerButton(ActionEvent arg0) {
	
		switchPlayer(playerLabel.getText());
		
	}
	
	// helper fucntion for switching between players
	private void switchPlayer(String playerNumber) {
		
		if (playerNumber.equals("Player 1")) {
			System.out.println("Switching objects in Decks view to player 2");
			playerLabel.setText("Player 2");
			playerNum = 2;
			
			// load player 2 deck into playerCard panes
				System.out.println(player2.getCards().toString());
				loadCards(2);
			
		} else {
			System.out.println("Switching objects in Decks view to player 1");
			playerLabel.setText("Player 1");
			playerNum = 1;
			// load player 1 deck into playerCard panes
				System.out.println(player1.getCards().toString());
				loadCards(1);
		}	
	}
	
	// Loads Player Decks into the Controller
	private void loadCards(Integer pl) {
		
		player1 = new Deck("data/player1.csv");
		player2 = new Deck("data/player2.csv");
		playerCards.removeAll();
		playerList.getItems().clear();
		playerListIndex = 0;
		
		if (pl == 1) {
			LinkedList<Card> p1 = player1.getCards();
			for (int i = 0; i < p1.size(); i++) {	
				playerCards.add(String.valueOf(p1.get(i).getCardNumber()) + ": " + p1.get(i).getName());
			}
			
		} else {
			LinkedList<Card> p2 = player2.getCards();
			for (int i = 0; i < p2.size(); i++) {	
				playerCards.add(String.valueOf(p2.get(i).getCardNumber()) + ": " + p2.get(i).getName());
			}
		}
		playerList.setItems(playerCards);
	}
	
	/**
	* createCard: helper method to create a Pane representing a card
	* @param x: int representation of the x coordinate of the pane on screen
	* @param y: int representation of the y coordinate of the pane of screen
	* @param c: Card representation of a card to be representated
	* @return: Pane a representation of a card
	*/
	private Pane createCard(int x, int y, Card c) {
		Pane p = new Pane();
		p.setLayoutX(x);
		p.setLayoutY(y);
		p.setPrefSize(75, 100);
		if (c.getP1()) {p.setBorder(new Border(new BorderStroke(Color.RED,BorderStrokeStyle.SOLID, null, null)));}
		else {p.setBorder(new Border(new BorderStroke(Color.BLUE,BorderStrokeStyle.SOLID, null, null)));}
		ObservableList<Node> childElements = p.getChildren();
		Label name = new Label();
		name.setLayoutX(5);
		name.setLayoutY(10);
		name.setText(c.getName());
		if(c.isTroop()) {
			Label stats = new Label();
			stats.setLayoutX(5);
			stats.setLayoutY(60);
			stats.setText(String.valueOf("A" + ((TroopCard) c).getAttack()) + " - D" + String.valueOf(((TroopCard) c).getDefense()) + " - S" + String.valueOf(((TroopCard) c).getSpeed()));
			childElements.add(stats);
		}
		childElements.add(name);
		p.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// on click
				System.out.println("Selected: " + c.getName());
					
//					// replace card in slot on view with selected card, replace number in player#.csv
//					int cNum = decksList.getSelectionModel().getSelectedIndex() + 1;
//					if (cNum != 0) {
//						
//						if (playerLabel.getText().charAt(7) == '1') {
//							System.out.println("Replacing player" + playerLabel.getText().charAt(7) + " with: " + cNum);
//							try {
//								replaceCard(1, cNum, 3);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//							loadCards(1);
//						} else {
//							System.out.println("Replacing player" + playerLabel.getText().charAt(7) + "index #" + " with: " + cNum);
//							try {
//								replaceCard(2, cNum, 3);
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//							
//							loadCards(2);
//						}
//						
//					}

				
			}
		});
		p.setStyle("-fx-background-color: rgba(220, 220, 220, 1);");
		return p;
	}
	
	/**
	* replaceCard: helper method to replace a card in a deck
	* @param player: int player to have a card replaced
	* @param cardNum: int cardNumber to be replace in the deck
	* @param line: int line to replace the card
	*/
	public void replaceCard(int player, int cardNum, int line) throws IOException {
		
		Path path = Paths.get("data/player" + player + ".csv");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.set(line - 1, Integer.toString(cardNum));
		System.out.println(path.toString() + " : " + lines);
		Files.write(path, lines, StandardCharsets.UTF_8);
		
	}
	
	/**
	* addButton: method enumating function of the addButton
	*/
	public void addButton() throws IOException {
		System.out.println("decksListIndex: " + decksListIndex);
		System.out.println("playerListIndex: " + playerListIndex);
		
		if (decksListIndex == 0) {
			return;
		}
		Path path = Paths.get("data/player"+String.valueOf(playerNum) + ".csv");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.add(String.valueOf(decksListIndex));
		Files.write(path, lines, StandardCharsets.UTF_8);
		loadCards(playerNum);
		
	}

	/**
	* replaceButton: method enumerating the replace button functionality
	*/
	public void replaceButton() {
		System.out.println("decksListIndex: " + decksListIndex);
		System.out.println("playerListIndex: " + playerListIndex);

		if (decksListIndex == 0 || playerListIndex == 0) {
			return;
		}
		
		try {
			replaceCard(Character.getNumericValue(playerLabel.getText().charAt(7)), decksListIndex, playerListIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadCards(Character.getNumericValue(playerLabel.getText().charAt(7)));	
	}
	
	/**
	* removeButton: method enumerating the remove button functionality
	*/
	public void removeButton() throws IOException {
		System.out.println("decksListIndex: " + decksListIndex);
		System.out.println("playerListIndex: " + playerListIndex);
		
		if ( playerListIndex == 0) {
			return;
		}	
		Path path = Paths.get("data/player"+String.valueOf(playerNum) + ".csv");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.remove(playerListIndex);
		Files.write(path, lines, StandardCharsets.UTF_8);
		loadCards(playerNum);
		
	}
	
	/**
	* listViewClick: Enumerating the functionality of clicking a card in listView
	*/
	public void listViewClick() {
		
		if (playerList.getSelectionModel().getSelectedItem() == null) {
			return;
		}
		
		System.out.println("Card number: " + (decksList.getSelectionModel().getSelectedIndex() + 1) + " clicked");
		decksListIndex = decksList.getSelectionModel().getSelectedIndex() + 1;
		ObservableList<Node> elements = apane.getChildren();
		cardPane = new LinkedList<Pane>(); 
		selectedCard = this.createCard(365, 275, CG.givenCard(decksList.getSelectionModel().getSelectedIndex() + 1));
		selectCard = decksList.getSelectionModel().getSelectedIndex() + 1;
		elements.add(selectedCard);
		cardPane.add(selectedCard);
		
	}
	
	/**
	* playerListViewClick: Enumerating the functionality of clicking a card in playerlistView
	*/
	public void playerListViewClick() {
		
		if (playerList.getSelectionModel().getSelectedItem() == null) {
			return;
		}
		
		System.out.println("Card number: " + (playerList.getSelectionModel().getSelectedIndex() + 1) + " clicked");
		playerListIndex = playerList.getSelectionModel().getSelectedIndex() + 1;
		ObservableList<Node> elements = apane.getChildren();
		cardPane = new LinkedList<Pane>(); 
		playerSelectedCard = this.createCard(365, 500, CG.givenCard(playerList.getSelectionModel().getSelectedItem().charAt(0) - '0'));
		playerSelectCard = playerList.getSelectionModel().getSelectedIndex() + 1;
		elements.add(playerSelectedCard);
		cardPane.add(playerSelectedCard);
		
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
