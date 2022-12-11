package application.model;

import java.util.LinkedList;

import javafx.concurrent.Task;
import application.model.Phase;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The model that defines a Game. This class extends Class as to keep track of changing game states and to update 
 * dependents on the changes.
 */
public class Game extends Task<String>{
	/**
	* p1Deck: Deck representation of the Player One's deck
	* p2Deck: Deck representation of the Player Two's deck
	* p1Hand: A LinkedList Representing Player One's Hand
	* p2Hand: A LinkedList Representing Player Two's Hand
	* p1Turn: A boolean representation of whose turn the game is.
	* phase: A Phase representation of what phase the game is
	* board: A 2d Array representation of the board that the game is being played on.
	* exit: boolean telling the game to end.
	*/
	private Deck p1Deck;
	private Deck p2Deck;
	private LinkedList<Card> p1Hand;
	private LinkedList<Card> p2Hand;
	private boolean p1Turn = true;
	private Phase phase;
	private Card[][] board;
	private boolean exit = false;
	
	/**
	* Constructor for a game given two set decklist
	* @param filename1: String representation of the filename of player one's decklist
	* @param filename2: String representation of the filename of player two's decklist
	*/
	public Game(String filename1,String filename2) {
		this.p1Deck = new Deck(filename1);
		this.p2Deck = new Deck(filename2);
		p1Deck.shuffle();
		p2Deck.shuffle();
		this.p2Deck.setP1(false);
		p1Hand = new LinkedList<Card>();
		p2Hand = new LinkedList<Card>();
		board = new Card[6][5];
		init();
	}
	
	/**
	* Constructor for a game given a given decksize
	* @param decksize: int representation of a decksize for both of the decks in the game
	*/
	public Game(int decksize) { 
		this.p1Deck = new Deck(decksize);
		this.p2Deck = new Deck(decksize);
		p1Deck.shuffle();
		p2Deck.shuffle();
		this.p2Deck.setP1(false);
		p1Hand = new LinkedList<Card>();
		p2Hand = new LinkedList<Card>();
		board = new Card[6][5];
		init();
	}
	
	/**
	* init: method used to initialize a card Game
	*/
	public void init() {
		//this.draw(2, true);
		//this.draw(2, false);
		phase = Phase.DRAW;
	}
	
	/**
	* call: function required to be overridden for the Task interface
	*/
	@Override
	protected String call() throws Exception {
		// TODO Auto-generated method stub
		while (!exit) { 
			String msg = "";
			if(p1Turn) {
				msg += "P1:";
			} else {
				msg+= "P2:";
			}
			msg += phase.label;
			updateMessage(msg);
		}
		return null;
	}
	
	/**
	* discard: method to discard a specific cards from a given player
	* @param index: the int representation of which card to be discarded from a hand
	* @param player1: the boolean representation of which player should be discarding
	* int, boolean -> void
	*/
	public void discard(int index, boolean player1) {
		if(player1) {p1Hand.remove(index);}
		else {p2Hand.remove(index);}
	}
	
	/**
	* draw: method to draw a number of cards to a given player's hand
	* @param numCards: the int representation of cards to be added to the hand
	* @param player1: the boolean representation of which deck should be drawn from and which hand should be added to.
	*/
	public void draw(int numCards, boolean player1) {
		for (int i = 0; i < numCards; ++i) {
			if(player1) {
				Card next = p1Deck.pop();
				p1Hand.add(next);
			} else {
				Card next = p2Deck.pop();
				p2Hand.add(next);
			}
		}
	}
	
	/**
	* incrementPhase: method to increment the phases of the game.
	*/
	public void incrementPhase() {
		System.out.println(this.phase.label);
		boolean gameOver = false;
		if(this.phase == Phase.END) {
			for(int i = 0; i < 6; ++i) {
				for(int j = 0; j < 5; ++j) {
					if(board[i][j] != null) {
						((TroopCard)board[i][j]).endMovementP();
						((TroopCard)board[i][j]).endFightP();
						if(((TroopCard)board[i][j]).getP1() && i == 5) { gameOver = true;}
						if(!((TroopCard)board[i][j]).getP1() && i == 0) { gameOver = true;}
					}
				}
			}
			p1Turn = !p1Turn;
		} 
		System.out.println(gameOver);
		if(!gameOver) {this.phase = this.phase.next();}
		else {this.phase = Phase.GAMEOVER;}
	}
	
	/**
	public LinkedList<int[]> getMovement(int x,int y){
		LinkedList<int[]> ret = new LinkedList<int[]>();
		if(board[x][y] == null || board[x][y].isTactic()) {return ret;}
		TroopCard c = (TroopCard)board[x][y];
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 5; i++) {
				if(board[i][j] != null && (Math.abs(i - x) + Math.abs(j - y)) <= c.getSpeed()) {
					int[] add= {i,j};
					ret.add(add);
				}
			}
		}
		return ret;
	}
	*/
	/**
	* canPlayCard: method for determining if a the currentPlayer can play a card to a given position.
	* @param index: int representation of the index of a card in someones hand
	* @param x: int representation of the column on a board
	* @param y: int representation of the row on a board
	* @return: boolean indicating if a card can be played
	*/
	public boolean canPlayCard(int index, int x, int y) {
		if(Phase.PLAY != phase) {return false;}
		if(index < 0 || x < 0 || y < 0 || y >= 5 || x >= 6) {return false;}
		if(this.board[x][y] != null) {return false;}
		if(this.p1Turn == true && this.p1Hand.get(index).isTroop() && this.p1Hand.get(index).getP1() == true && x == 0) {return true;}
		if(this.p1Turn == false && this.p2Hand.get(index).isTroop() && this.p2Hand.get(index).getP1() == false && x == 5) {return true;}
		return false;
	}
	
	/**
	* playCard: method for playing a card from the board
	* @param index: int representation of the index of a card in someones hand
	* @param x: int representation of the column on a board
	* @param y: int representation of the row on a board
	* @return: boolean indicating whether a card has been played
	*/
	public boolean playCard(int index, int x, int y) {
		if(canPlayCard(index, x, y)) {
			if (this.p1Turn) {
				Card temp = this.p1Hand.get(index);
				this.p1Hand.remove(index);
				this.board[x][y] = temp;
				return true;
			} else {
				Card temp = this.p2Hand.get(index);
				this.p2Hand.remove(index);
				this.board[x][y] = temp;
				return true;
			}
		}
		return false;
	}
	
	/**
	* canMoveCard: method for determining if a the currentPlayer can move a card to a given position.
	* @param xfrom: int representation of the column on a board to move from
	* @param yfrom: int representation of the row on a board to move from
	* @param xto: int representation of the column on a board to move to
	* @param yto: int representation of the row on a board to move to
	* @return: boolean indicating if a card can be moved
	*/
	public boolean canMoveCard(int xfrom, int yfrom, int xto, int yto) {
		if(Phase.MOVE != phase) {return false;}
		if(xto < 0 || yto < 0 || yto >= 5 || xto >= 6|| xfrom < 0 || yfrom < 0 || yfrom >= 5 || xfrom >= 6) {return false;}
		if(xto == xfrom && yto == yfrom) {return false;}
		if(this.board[xfrom][yfrom] == null) {return false;}
		if(!(this.board[xfrom][yfrom].isTroop())) {return false;}
		if(this.board[xfrom][yfrom].getP1() != this.p1Turn) {return false;}
		if(((TroopCard)this.board[xfrom][yfrom]).getHasMoved()) {return false;}
		if(this.board[xto][yto] != null) {return false;}
		if(Math.abs(yfrom - yto) + Math.abs(xfrom - xto) > ((TroopCard)this.board[xfrom][yfrom]).getSpeed()) {return false;}
		return true;
	}
	/**
	* moveCard: method to move a card to a given position.
	* @param xfrom: int representation of the column on a board to move from
	* @param yfrom: int representation of the row on a board to move from
	* @param xto: int representation of the column on a board to move to
	* @param yto: int representation of the row on a board to move to
	* @return: boolean indicating if a card has been moved.
	*/
	public boolean moveCard(int xfrom, int yfrom, int xto, int yto) { 
		if(canMoveCard(xfrom,yfrom,xto,yto)) {
			TroopCard temp = (TroopCard)board[xfrom][yfrom];
			temp.move();
			board[xfrom][yfrom] = null;
			board[xto][yto] = temp;
			
			return true;
		}
		return false;
	}
	
	/**
	* canFightCard: method to determine if a card can fight another card.
	* @param xfrom: int representation of the column on a board to fight from
	* @param yfrom: int representation of the row on a board to fight from
	* @param xto: int representation of the column on a board to be attacked
	* @param yto: int representation of the row on a board to to be attacked
	* @return: boolean indicating if a card can fight another card
	*/
	public boolean canFightCard(int xfrom, int yfrom, int xto, int yto) {
		System.out.println("check to see if can Fight" + xfrom +" " + yfrom +".");
		if(Phase.FIGHT != phase) {System.out.println(1); return false;}
		if(xto < 0 || yto < 0 || yto >= 5 || xto >= 6|| xfrom < 0 || yfrom < 0 || yfrom >= 5 || xfrom >= 6) {System.out.println(2); return false;}
		if(xto == xfrom && yto == yfrom) {System.out.println(3); return false;}
		if(this.board[xfrom][yfrom] == null) {System.out.println(4); return false;}
		if(!(this.board[xfrom][yfrom].isTroop())) {System.out.println(5); return false;}
		if(this.board[xfrom][yfrom].getP1() != this.p1Turn) {System.out.println(6); return false;}
		if(((TroopCard)this.board[xfrom][yfrom]).getHasAttacked()) {System.out.println(7); return false;}
		if(this.board[xto][yto] == null) {System.out.println(8); return false;}
		if(!(this.board[xto][yto].isTroop())) {System.out.println(9); return false;}
		if(this.board[xto][yto].getP1() == this.p1Turn) {System.out.println(10); return false;}
		if(Math.max(Math.abs(yfrom - yto), Math.abs(xfrom - xto)) > 1) {return false;}
		return true;
	}
	
	/**
	* fightCard: method to fight two cards
	* @param xfrom: int representation of the column on a board to fight from
	* @param yfrom: int representation of the row on a board to fight from
	* @param xto: int representation of the column on a board to be attacked
	* @param yto: int representation of the row on a board to to be attacked
	* @return: boolean indicating if a card has fought
	*/
	public boolean fightCard(int xfrom, int yfrom, int xto, int yto) {
		if(canFightCard(xfrom,yfrom,xto,yto)) {
			TroopCard temp1 = (TroopCard)board[xfrom][yfrom];
			TroopCard temp2 = (TroopCard)board[xto][yto];
			temp1.attack(temp2.getAttack());
			temp2.fight(temp1.getAttack());
			if(temp1.getcDefense() <= 0) {
				board[xfrom][yfrom] = null;
			}
			if(temp2.getcDefense() <= 0) {
				board[xto][yto] = null;
			}
		}
		return false;
	}
	
	/**
	* Getters and Setters for the Game class
	*/
	public Deck getP1Deck() {
		return p1Deck;
	}

	public void setP1Deck(Deck p1Deck) {
		this.p1Deck = p1Deck;
	}

	public Deck getP2Deck() {
		return p2Deck;
	}

	public void setP2Deck(Deck p2Deck) {
		this.p2Deck = p2Deck;
	}

	public LinkedList<Card> getP1Hand() {
		return p1Hand;
	}

	public void setP1Hand(LinkedList<Card> p1Hand) {
		this.p1Hand = p1Hand;
	}

	public LinkedList<Card> getP2Hand() {
		return p2Hand;
	}

	public void setP2Hand(LinkedList<Card> p2Hand) {
		this.p2Hand = p2Hand;
	}

	public boolean isP1Turn() {
		return p1Turn;
	}

	public void setP1Turn(boolean p1Turn) {
		this.p1Turn = p1Turn;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Card[][] getBoard() {
		return board;
	}

	public void setBoard(Card[][] board) {
		this.board = board;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}
	
}
