package application.model;

/*
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder Project
 * Fall 2022
 * 
 * TroopCard.java, sub-class of AbstractCard.java
 */

import application.model.AbstractCard;

public class TroopCard extends AbstractCard{
	/**
	* speed: int representation of a cards speed
	* attack: int representation of a cards attack
	* defense: int representaiton of a cards defence
	* cDefense: int representation of a cards CURRENT defence
	* hasMoved: boolean representation of whether a card has moved
	* hasAttacked: boolean representation of whether a card has attacked
	*/
	int speed;
	int attack;
	int defense;
	int cDefense;
	boolean hasMoved;
	boolean hasAttacked;
	
	/**
	* constructor for a TroopCard
	* @param name: String name of a TroopCard
	* @param text: String text of a TroopCard
	* @param cardNumber: int cardNumber of a TroopCard
	* @param cost: int cost of a card
	* @param speed: int speed of a TroopCard
	* @param attack: int attack of a TroopCard
	* @param defense: int defense of a TroopCard
	* @param Rarity: rarity of a card
	*/
	public TroopCard(String name, String text, int cardNumber, int cost, int speed, int attack, int defense, Rarity rarity) {
		super(name, text, cardNumber, cost, Type.TROOP, rarity);
		this.speed = speed;
		this.attack = attack;
		this.defense = defense;
		this.cDefense = defense;
		this.hasAttacked = false;
		this.hasMoved = false;
		// TODO Auto-generated constructor stub
	}
	
	/**
	* constructor for a TroopCard To return a copy of a TroopCard
	* @param c: TroopCard to be copied
	*/
	public TroopCard(TroopCard c) {
		super(c);		
		this.speed = c.getSpeed();
		this.attack = c.getAttack();
		this.defense = c.getDefense();
		this.cDefense = c.getDefense();
		this.hasAttacked = c.getHasAttacked();
		this.hasMoved = c.getHasMoved();
	}
	
	/**
	* Overridden to indicate whether a card is a Troop
	* @return: boolean true because the card is a TroopCard 
	*/
	@Override
	public boolean isTroop() {
		return true;
	}
	
	/**
	* fight: method to simulate being attacked and taking damage
	* @param damage: int the amount of damage taken
	*/ 
	public void fight(int damage) {
		this.cDefense -= damage;
	}
	
	/**
	* attack: method to simulate attacking and taking damage
	* @param damage: int the amount of damage taken
	*/
	public void attack(int damage) {
		hasAttacked = true;
		this.cDefense -= damage;
	}
	/**
	* move: method to simulate moving a card
	*/
	public void move() {
		this.hasMoved = true;
	}
	
	/**
	* endMovementP: method to reset movement of a card
	*/ 
	public void endMovementP() {
		this.hasMoved = false;
	}
	/**
	* endFightP: method to resent hasAttacked of a card
	*/
	public void endFightP() {
		this.hasAttacked = false;
		this.cDefense = defense;
	}
	
	/**
	* getters and setters for a TroopCard
	*/
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getcDefense() {
		return cDefense;
	}
	public void setcDefense(int cDefense) {
		this.cDefense = cDefense;
	}
	public boolean getHasMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	public boolean getHasAttacked() {
		return hasAttacked;
	}
	public void setHasAttacked(boolean hasAttacked) {
		this.hasAttacked = hasAttacked;
	}

}
