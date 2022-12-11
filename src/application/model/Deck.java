package application.model;

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * Class representation of a Deck of Cards
 */
public class Deck {
	/**
	* cards : LinkedList<Card> of Cards representing a deck
	* deckSize: Int representation of the number of cards in the deck;
	* viewable: boolean representation of a whether a deck is viewable; Not currently used
	* randomGenerator: Random representation of a randomGenerator
	* cardGenerator: CardGenerator (object) for getting cards from a cardlist
	*/
	private LinkedList<Card> cards;
	private int deckSize;
	private boolean viewable;
	private Random randomGenerator = new Random();
	private CardGenerator cardGenerator = new CardGenerator();
	
	/**
	 * Constructor for a Deck, a randomized deck for testing
	 * @param deckSize: The size of the deck generated
	 */
	public Deck(int deckSize) {
		this.cards = new LinkedList<Card>();
		this.deckSize = deckSize;
		boolean viewable = true;
		for(int i = 0; i < deckSize; ++i) {
			cards.add(cardGenerator.givenCard(1));
		}
	}
	
	/**
	* Constructor for a deck, generated from a deck list.
	* @param filename: String representation of a filename
	*/
	public Deck(String filename) {
		this.cards = new LinkedList<Card>();
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.print(line);
				int cardNumber = Integer.valueOf(line);
				cards.add(cardGenerator.givenCard(cardNumber));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* shuffle: method to shuffle a deck
	*/
	public void shuffle() {
		Collections.shuffle(this.cards);
	}
	/**
	* toString: method to give a String representation of a deck
	*/
	public String toString() {
		return cards.toString();
	}
	/**
	* pop: method to return the top card of a deck
	* void -> Card
	*/
	public Card pop() {
		return this.cards.pop();
	}
	/**
	* addToTop: method to add a card to the top of a deck
	* Card -> Void
	*/
	public void addToTop(Card card) {
		cards.addFirst(card);
		this.deckSize +=1;
	}
	/**
	* addToBottom: method to add a card to the bottom of a deck
	* Card -> Void
	*/
	public void addToBotton(Card card) {
		cards.addLast(card);
		this.deckSize += 1;
	}
	/**
	* Getters and Setters for the Deck Model
	*/ 
	public void setP1(boolean p1) {
		for(Card c: cards) {
			c.setP1(p1);
		}
	}
	
	
	////////////////////////////////////////////////////////
	public LinkedList<Card> getCards() {
		return cards;
	}

	public void setCards(LinkedList<Card> cards) {
		this.cards = cards;
	}

	public int getDeckSize() {
		return deckSize;
	}

	public void setDeckSize(int deckSize) {
		this.deckSize = deckSize;
	}

	public boolean isViewable() {
		return viewable;
	}

	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}

	public Random getRandomGenerator() {
		return randomGenerator;
	}

	public void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	public CardGenerator getCardGenerator() {
		return cardGenerator;
	}

	public void setCardGenerator(CardGenerator cardGenerator) {
		this.cardGenerator = cardGenerator;
	}
	
}
