package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;

import application.Main;
import application.model.Card;
import application.model.Game;
import application.model.Phase;
import application.model.TroopCard;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * Class representation of a GameController. Implements a EventHandler Interface and an Initializable Interface
 */
public class GameController implements EventHandler<ActionEvent>, Initializable{

	/**
	* apane: AnchorPane the AnchorPane of the application
	* board: GridPane the Representation of the board
	* stage: Label the label showing the current stage
	* turnButton: Button used to increment the turn
	* menu: Button used to pull up a menu to return back to menu
	* currentGame: Game class representation of the currentGame
	* popoutPane: Pane containing the pane for a Big Card 
	* menuPane: Pane for the menu to leave the game
	* cardPaneP1: List of Panes representing cards for Player One Hand
	* cardPaneP2: List of Panes representing cards for Player Two Hand
	* phase: Phase, current phase of the game.
	* p1Turn: boolean shows which player is playing
	* seleciton: current Pane that has been selected by the user
	* selectionIndex: index of the currentPane that has been selected
	* discardButton: Button to discard cards if player ends turn with to many cards
	*/
	@FXML
	AnchorPane apane;
	
	@FXML
	GridPane board;
	
	@FXML
	Label stage;
	
	@FXML
	Button turnButton;
	
	@FXML
	Button menu;
	
	Game currentGame;
	Pane popoutPane;
	Pane menuPane;
	LinkedList<Pane> cardPaneP1;
	LinkedList<Pane> cardPaneP2;
	Phase phase;
	boolean p1Turn;
	Pane selection = null;
	int[] selectionIndex;
	Button discardButton;
	
	/**
	* initialize: method used to set up the controller after the @FXML injects have been made
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.currentGame = new Game("Data/player1.csv", "Data/player2.csv");
		cardPaneP1 = new LinkedList<Pane>();
		cardPaneP2 = new LinkedList<Pane>();
		ObservableList<Node> elements = apane.getChildren();
		drawCard(2, true);
		drawCard(2, false);
		discardButton = new Button();
		discardButton.setLayoutX(675);
		discardButton.setLayoutY(180);
		discardButton.setText("Discard");
		discardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(selection != null && selectionIndex != null && (selectionIndex[0] < 0 || selectionIndex[0] > 6)) {
					if(selectionIndex[0] == -1) {
						currentGame.discard(selectionIndex[1], true);
					}
					if(selectionIndex[0] == 7) {
						currentGame.discard(selectionIndex[1], false);
					}
					selectionIndex = null;
					selection = null;
					redraw();
				}
			}
		});
		menu.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setMenu();
			}
		});
		//stage.textProperty().bind(currentGame.messageProperty()); 
		currentGame.messageProperty().addListener((obs, oldProgress, newProgress) ->
			setPhase(newProgress));
		menuPane = new Pane();
		menuPane.setPrefWidth(200);
		menuPane.setPrefHeight(200);
		
		ObservableList<Node> childrenElements = menuPane.getChildren();
		Label text = new Label("Quit and Return to Menu");
		text.setLayoutX(50);
		text.setLayoutY(50);
		Button quit = new Button("Quit");
		Button back = new Button("Back");
		quit.setLayoutX(50);
		quit.setLayoutY(100);
		quit.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
					System.out.println("Switching user to Main view");
					Main.stage.setScene( new Scene(root, 800, 800) );
					Main.stage.show();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		back.setLayoutX(50);
		back.setLayoutY(150);
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ObservableList<Node> elements = apane.getChildren();
				elements.remove(menuPane);
			}
		});
		childrenElements.add(back);
		childrenElements.add(text);
		childrenElements.add(quit);
		menuPane.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, null, null)));
		menuPane.setStyle("-fx-background-color: rgba(220, 220, 220, 1);");
		new Thread(currentGame).start();
		redraw();
	}

	/**
	* setMenu() adds the menu to the anchorPane
	*/
	private void setMenu() {
		ObservableList<Node> elements = apane.getChildren();
		elements.add(menuPane);
	}
	
	/**
	* redraws all the cards to the hand and to the board
	*/
	private void redraw() {
		ObservableList<Node> elements = apane.getChildren();
		if(Phase.END != phase && elements.indexOf(discardButton) != -1) {
			elements.remove(discardButton);
		} 
		if(Phase.END == phase && elements.indexOf(discardButton) == -1 && (currentGame.getP1Hand().size() > 5 || currentGame.getP2Hand().size() > 5)) {
			elements.add(discardButton);
		}
		ArrayList<Node> remove = new ArrayList<Node>();
		for(Node node: elements) {
			if(node instanceof Pane && !(node instanceof GridPane)) {remove.add(node);}
		}
		elements.removeAll(remove);
		setHand();
		if (selection != null && elements.indexOf(this.popoutPane) == -1) {System.out.println("Add BigCard"); elements.add(this.popoutPane);}
		elements = board.getChildren();
		remove = new ArrayList<Node>();
		for(Node node: elements) {
			if(node instanceof Pane) {
				ArrayList<Node> removeChildren = new ArrayList<Node>();
				Pane childPane = (Pane)node;
				for(Node childnode: childPane.getChildren()) {
					if(childnode instanceof Pane) {
						remove.add(childnode);
					}
				}
				childPane.getChildren().removeAll(removeChildren);
			}
		}
		elements.removeAll(remove);
		setBoard();
		
	}
	
	/**
	* setBoard: Helper method used to redraw the Board
	*/
	private void setBoard() {
		Card[][] gameBoard = currentGame.getBoard();
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < 5; j++) {
				if(i == 0) { 
					Pane cell = new Pane();
					cell.setPrefSize(60,60);
					cell.setStyle("-fx-background-color: black, salmon; -fx-background-insets: 0, 0 0 1 1;");
					final int col = i;
					final int row = j;
					if(gameBoard[i][j] != null) {
						cell.getChildren().add(this.createCard(10, 10, gameBoard[i][j]));
					}
					cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							// TODO Auto-generated method stub
							System.out.println("Grid selected in cell["+col+", "+row+"]");
							if(selectionIndex == null && selection != null) {System.out.println("set index");selectionIndex = new int[2];selectionIndex[0] = col; selectionIndex[1] = row;}
							if(selectionIndex != null) {System.out.println("Current Selection Index{"+selectionIndex[0]+", " + selectionIndex[1]+"]");}
							if(selectionIndex[0] == -1 && currentGame.canPlayCard(selectionIndex[1], col, row)) {
								System.out.println("CanPlay");
								if (currentGame.playCard(selectionIndex[1], col, row)) {
									selection = null;
									selectionIndex = null; 
								}
							} else if(selectionIndex != null && (selectionIndex[0] != col || selectionIndex[1] != row) && currentGame.canMoveCard(selectionIndex[0], selectionIndex[1], col, row)) {
								currentGame.moveCard(selectionIndex[0], selectionIndex[1], col, row);
								selection = null;
								selectionIndex = null;
							} else if(selectionIndex !=null && currentGame.canFightCard(selectionIndex[0], selectionIndex[1], col, row) ) {
								currentGame.fightCard(selectionIndex[0], selectionIndex[1], col, row);
								selection = null;
								selectionIndex = null;
							}
							redraw();
						}	
					});
					this.board.add(cell, i, j);
				} else if (i == 5) {
					Pane cell = new Pane();
					cell.setPrefSize(60,60);
					cell.setStyle("-fx-background-color: black, skyblue; -fx-background-insets: 0, 0 0 1 1;");
					final int col = i;
					final int row = j;
					if(gameBoard[i][j] != null) {
						cell.getChildren().add(this.createCard(10, 10, gameBoard[i][j]));
					}
					cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							// TODO Auto-generated method stub
							System.out.println("Grid selected in cell["+col+", "+row+"]");
							if(selectionIndex == null && selection != null) {System.out.println("set index");selectionIndex = new int[2];selectionIndex[0] = col; selectionIndex[1] = row;}
							if(selectionIndex != null) {System.out.println("Current Selection Index{"+selectionIndex[0]+", " + selectionIndex[1]+"]");}
							if(selectionIndex[0] == 7 && currentGame.canPlayCard(selectionIndex[1], col, row)) {
								if (currentGame.playCard(selectionIndex[1], col, row)) {
									selection = null;
									selectionIndex = null;
								}
							}  else if(selectionIndex != null && (selectionIndex[0] != col || selectionIndex[1] != row) && currentGame.canMoveCard(selectionIndex[0], selectionIndex[1], col, row)) {
								currentGame.moveCard(selectionIndex[0], selectionIndex[1], col, row);
								selection = null;
								selectionIndex = null;
							} else if(selectionIndex !=null && currentGame.canFightCard(selectionIndex[0], selectionIndex[1], col, row) ) {
								currentGame.fightCard(selectionIndex[0], selectionIndex[1], col, row);
								selection = null;
								selectionIndex = null;
							}
							redraw();
						}	
					});
					this.board.add(cell, i, j);
				} else {
					Pane cell = new Pane();
					cell.setPrefSize(60,60);
					cell.setStyle("-fx-background-color: black, white; -fx-background-insets: 0, 0 0 1 1;");
					final int col = i;
					final int row = j;
					if(gameBoard[i][j] != null) {
						cell.getChildren().add(this.createCard(10, 10, gameBoard[i][j]));
					}
					cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							// TODO Auto-generated method stub
							System.out.println("Grid selected in cell["+col+", "+row+"]");
							if(selectionIndex == null && selection != null) {System.out.println("set index");selectionIndex = new int[2];selectionIndex[0] = col; selectionIndex[1] = row;}
							if(selectionIndex != null && (selectionIndex[0] != col || selectionIndex[1] != row) && currentGame.canMoveCard(selectionIndex[0], selectionIndex[1], col, row)) {
								currentGame.moveCard(selectionIndex[0], selectionIndex[1], col, row);
								selection = null;
								selectionIndex = null;
							} else if(selectionIndex !=null && currentGame.canFightCard(selectionIndex[0], selectionIndex[1], col, row) ) {
								currentGame.fightCard(selectionIndex[0], selectionIndex[1], col, row);
								selection = null;
								selectionIndex = null; 
							}
							redraw();
						}
					
					});
					this.board.add(cell, i, j);
				}
			}
		}
	}
	
	/**
	* discord: helper method to discord a card from the current player's hand
	*/
	private void discard(Pane p, boolean p1) {
		ObservableList<Node> elements = apane.getChildren();
		if(p1) {
			int index = cardPaneP1.indexOf(p);
			cardPaneP1.remove(p);
			elements.remove(p);
			currentGame.discard(index, p1);
		} else {
			int index = cardPaneP2.indexOf(p);
			cardPaneP2.remove(p);
			elements.remove(p);
			currentGame.discard(index, p1);
		}
		redraw();
	}
	
	/**
	* helper function to send the draw card command to the game
	*/ 
	private void drawCard(int num, boolean p1) {
		currentGame.draw(num, p1);
		redraw();
	}
	
	/**
	* setHand: Helper method used to draw the cards in the hand of the current player
	*/
	private void setHand() {
		if(p1Turn) {
			LinkedList<Card> loC = currentGame.getP1Hand();
			ObservableList<Node> elements = apane.getChildren();
			cardPaneP1 = new LinkedList<Pane>(); 
			for(int i = 0; i< loC.size(); ++i) {
				Pane p = this.createCard(i * 100 + 50, 675, loC.get(i));
				elements.add(p);
				cardPaneP1.add(p);
			}
		} else {
			LinkedList<Card> loC = currentGame.getP2Hand();
			System.out.println("HandSize" + loC.size());
			ObservableList<Node> elements = apane.getChildren();
			cardPaneP2 = new LinkedList<Pane>(); 
			for(int i = 0; i< loC.size(); ++i) {
				Pane p = this.createCard(800 - (i * 100) - 150, 675, loC.get(i));
				elements.add(p);
				cardPaneP2.add(p);
			}
		}
	}
	
	/**
	* setPhase: Method that is called each time the game changes phase
	* @param phaseMessage: String representation indicating the current phase of the game.
	*/
	private void setPhase(String phaseMessage) {
		String[] values = phaseMessage.split(":");
		String stmsg = "" + values[1] + " Phase";
		if(Phase.END == phase && ((p1Turn && cardPaneP1.size() > 5) ||
						(!p1Turn && cardPaneP2.size() > 5))) {stmsg += ": Discard to 5";}
		this.phase = Phase.get(values[1]);
		if(Phase.GAMEOVER == phase) {stmsg = values[0] + " Won the Game";}
		p1Turn = values[0].compareTo("P1") == 0;
		stage.setText(stmsg);
		turnButton.setText("End " + values[1] + " Phase");
		if(Phase.GAMEOVER == phase) {turnButton.setText("Back to start");}
		turnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(Phase.DRAW == phase) {
					drawCard(1,p1Turn);
					currentGame.incrementPhase();
				}
				else if(Phase.END == phase && ((p1Turn && cardPaneP1.size() > 5) ||
						(!p1Turn && cardPaneP2.size() > 5))) {return;}
				else if(Phase.GAMEOVER == phase) {
					currentGame.setExit(true);
					try {
						Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
						System.out.println("Switching user to Main view");
						Main.stage.setScene( new Scene(root, 800, 800) );
						Main.stage.show();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {currentGame.incrementPhase();}		
			}
		});
		apane.getChildren().remove(popoutPane);
		if(selectionIndex != null) {System.out.println("Current Selection Index{"+selectionIndex[0]+", " + selectionIndex[1]+"]");}
		selection = null;
		selectionIndex = null;	
		redraw();
	}
	
	/**
	* createCard: Helper function used to create a Pane representation of a Card
	* @param x: int the x position of pane
	* @param y: int the y position of pane
	* @param c: the Card to base the Pane off of
	* return: Pane the representation of the Pane
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
		name.setLayoutX(2);
		name.setLayoutY(10);
		name.setText(c.getName());
		if(c.isTroop()) {
			Label stats = new Label();
			stats.setLayoutX(5);
			stats.setLayoutY(60);
			stats.setText(String.valueOf("A" + ((TroopCard) c).getAttack()) + " - D" + String.valueOf(((TroopCard) c).getcDefense()) + " - S" + String.valueOf(((TroopCard) c).getSpeed()));
			childElements.add(stats);
		}
		childElements.add(name);
		p.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(selection == null) {selection = p;}
				int index = cardPaneP1.indexOf(selection);
				if(index != -1) {
					if(selectionIndex == null) {selectionIndex = new int[2];}
					System.out.println("flag1");
					selectionIndex[0] = -1; 
					selectionIndex[1] = index;
				}
				index = cardPaneP2.indexOf(selection);
				if(index != -1) {
					if(selectionIndex == null) {selectionIndex = new int[2];}
					System.out.println("flag2");
					selectionIndex[0] = 7; 
					selectionIndex[1] = index;
				}
				ObservableList<Node> elements = apane.getChildren();
				createBigCard(x, y, c);
				redraw();
			}
		});
		p.setStyle("-fx-background-color: rgba(220, 220, 220, 1);");
		return p;
	}
	
	/**
	* createBigCard: Helper function used to create a Pane representation of a the Bigger Card Pane
	* @param x: int the x position of pane
	* @param y: int the y position of pane
	* @param c: the Card to base the Pane off of
	*/
	public void createBigCard(int x, int y, Card c) {
		popoutPane = new Pane();
		this.popoutPane.setLayoutX(630);
		this.popoutPane.setLayoutY(400);
		this.popoutPane.setPrefSize(150, 200);
		if (c.getP1()) {this.popoutPane.setBorder(new Border(new BorderStroke(Color.RED,BorderStrokeStyle.SOLID, null, null)));}
		else {this.popoutPane .setBorder(new Border(new BorderStroke(Color.BLUE,BorderStrokeStyle.SOLID, null, null)));}
		ObservableList<Node> childElements = popoutPane.getChildren();
		Label name = new Label();
		name.setLayoutX(5);
		name.setLayoutY(10);
		name.setText(c.getName());
		Label description = new Label();
		description.setLayoutX(10);
		description.setLayoutY(120);
		description.maxWidth(130);
		description.setWrapText(true);;
		description.setText(c.getText());
		childElements.add(description);
		if(c.isTroop()) {
			Label stats = new Label();
			stats.setLayoutX(5);
			stats.setLayoutY(180);
			stats.setText("A" + String.valueOf(((TroopCard) c).getAttack()) + " - D" + String.valueOf(((TroopCard) c).getDefense()) + "/" + String.valueOf(((TroopCard) c).getcDefense()) + " - S" + String.valueOf(((TroopCard) c).getSpeed()));
			childElements.add(stats);
		}
		childElements.add(name);
		this.popoutPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("click");
				selection = null;
				selectionIndex = null;
				ObservableList<Node> elements = apane.getChildren();
				elements.remove(popoutPane);
				
			}
		});
		this.popoutPane.setStyle("-fx-background-color: rgba(220, 220, 220, 1);");
	}
	
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
