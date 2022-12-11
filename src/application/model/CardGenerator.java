package application.model;

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The CardGenerator class is a class that functions to parse the total card list and able to return cards based solely 
 * a given cardNumber. Later could be extended to generate random cards from the card list to fit given parameters.
 **/
public class CardGenerator {
	/**
	* randomGenerator: A Random (Object) representation of a random Number generator. Was to be used to generate a 
	* random card from the card list.
	* allCards: A HashMap containing all cards mapping the int representation of a card number to a Card
	**/
	Random randomGenerator;
	HashMap<Integer, Card> allCards;
	
	/**
	* Constructor for the CardGenerator Class, inluded a seed value
	* @ param seed: int representation of a seed for the random number generator
	*/
	public CardGenerator(int seed) {
		this.randomGenerator = new Random(seed);
		this.allCards = new HashMap<Integer,Card>();
		loadCardLists();
	}
	/**
	* Contructor for the CardGenerator Class, no seeded value
	*/
	public CardGenerator() {
		this.randomGenerator = new Random();
		this.allCards = new HashMap<Integer,Card>();
		loadCardLists();
	}
	/**
	* loadCardLists is a method loads a card list from the datafile
	*/
	public void loadCardLists() {
		try(BufferedReader br = new BufferedReader(new FileReader("data/cards.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				int cardNumber = Integer.valueOf(values[0]);
				Type type = Type.valueOfCardNumber(cardNumber);
				Rarity rarity = Rarity.valueOfCardNumber(cardNumber);
				if(type == Type.TROOP) {
					values = line.split(",",7);
					allCards.put(cardNumber, new TroopCard(values[1],
							values[6], cardNumber, Integer.valueOf(values[2]),
							Integer.valueOf(values[3]), Integer.valueOf(values[4]),
							Integer.valueOf(values[5]), rarity));
				} else if(type == Type.TACTIC) {
					values = line.split(",",5);
					allCards.put(cardNumber, new TacticCard(values[1],
							values[3], cardNumber, Integer.valueOf(values[2]),
							rarity));
				}
				
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
	* givenCard returns a copy of a card from the cardList based on the given card number 
	* @param cardNumber: int representation of a given cardNumber
	* int -> Card
	*/
	public Card givenCard(int cardNumber) {
		Type type = Type.valueOfCardNumber(cardNumber);
		Rarity rarity = Rarity.valueOfCardNumber(cardNumber);
		if (type == Type.TROOP) {
			return new TroopCard((TroopCard)allCards.get(cardNumber));
		} else if (type == Type.TACTIC) {
			return new TacticCard((TacticCard)allCards.get(cardNumber));
		}
		return null;
	}
	/** Unimplemented Method
	public int getUnusedCardNumber(Type type, Rarity rarity) {
		int start = type.start;
		int end = type.end;
		int range = end - start;
		start = start + (int)Math.ceil(range * rarity.lowPercent);
		end = end + (int)Math.ceil(range * rarity.lowPercent);
		/////
		return 0; 
	}
	*/
	/**
	* Getters and Setters for the Deck-builder class.
	*/
	public HashMap<Integer, Card> getCardList() {
		return this.allCards;
	}
	public Random getRandomGenerator() {
		return randomGenerator;
	}
	public void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}
	public HashMap<Integer, Card> getAllCards() {
		return allCards;
	}
	public void setAllCards(HashMap<Integer, Card> allCards) {
		this.allCards = allCards;
	}
	
}
