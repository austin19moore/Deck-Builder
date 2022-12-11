package application.model;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The Class representing a Tactics Card, which extends the AbstractCard Class
 * We did not get to fully flesh out tactics cards, as the solutions presented to implement Tactics Cards were a bit extreme
 * for the project, but this can be extended later.
 */
public class TacticCard extends AbstractCard{

	/**
	* Constructor for a TacticCard
	* @param name: String name of a card
	* @param text: String text on a card
	* @param cardNumber: int number of the card
	* @param cost: int cost of a card
	* @param rarity: Rarity of a card
	*/
	public TacticCard(String name, String text, int cardNumber, int cost, Rarity rarity) {
		super(name, text, cardNumber, cost, Type.TACTIC, rarity);
		// TODO Auto-generated constructor stub
	}

	/**
	* Constructor for a TacticCard to create a copy of a Tactic Card
	* @param c: TacticCard to be copied
	*/
	public TacticCard(TacticCard c) {
		super(c);
	}
	
	/**
	* Overridden function to determine the type of this Card
	* @return: return true since this is a Tactic's card
	*/
	@Override
	public boolean isTactic() {
		return true;
	}
}
