package application.model;

import application.model.Type;
import application.model.Rarity;

/*
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The interface defining the functions that all Cards share
 */
public interface Card{
	String getName();
	String getText();
	int getCardNumber();
	Type getType();
	Rarity getRarity();
	int getCost();
	boolean isTroop();
	boolean isTactic();
	boolean getP1();
	void setP1(boolean p1);
}
