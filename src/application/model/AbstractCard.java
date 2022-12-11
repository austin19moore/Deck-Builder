package application.model;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The Abstract class representation of a Card. This implements the Card interface and implements all the methods 
 * from that interface.
 **/
public class AbstractCard implements Card{

	/**
	* name: String representation of a name
	* text: String representation of the text of a card
	* cardNumber : int representation of a card Number
	* type: Type (enum) representation of a Type of card
	* rarity: Rarity (enum) representation of a rarity of a card
	* cost: int representation of a cost for a card
	* p1: boolean representation of the ownership of a card
	**/
	final String name;
	final String text;
	final int cardNumber;
	final Type type;
	final Rarity rarity;
	final int cost;
	boolean p1;
	
	/**
	* Contructor for an Abstract Card
	* @param name : String representation of a name
	* @param text : String representation of a cards Text
	* @param cardNumber: int representation of a cardNumber
	* @param cost : int representation of a cards cost
	* @param type : Type (enum) representation of a cards type
	* @param rarity : Rarity (enum) representation of a cards rarity
	* String, String, int, int, Type, Rarity -> AbstractCard
	**/
	public AbstractCard(String name, String text, int cardNumber, int cost, Type type, Rarity rarity) {
		this.name = name;
		this.text = text;
		this.cardNumber = cardNumber;
		this.type = type;
		this.rarity = rarity;
		this.cost = cost;
		this.p1 = true;
	}
	/**
	 * Constructor for an Abstract Card. Used to create a copy of a Card
	 * @param c: AbstractCard to be copied
	 * AbstractCard -> AbstractCard
	 */
	public AbstractCard(AbstractCard c) {
		this.name = c.getName();
		this.text = c.getText();
		this.cardNumber = c.getCardNumber();
		this.type = c.getType();
		this.rarity = c.getRarity();
		this.cost = c.getCost();
		this.p1 = c.getP1();
	}
	
	/**
	* Getters and Setters for an AbstractCard
	*/
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public int getCardNumber() {
		// TODO Auto-generated method stub
		return this.cardNumber;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	
	@Override
	public Rarity getRarity() {
		return this.rarity;
	}
	
	/**
	* Used for debugging as by creating a string representation of a card
	*/
	public String toString() {
		return "Type: " + this.type.label + ", Rarity:" + this.rarity.label + ", Card Number: " + String.valueOf(this.cardNumber);
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return this.cost;
	}
	
	public boolean getP1() {
		return this.p1;
	}
	
	public void setP1(boolean p1) {
		this.p1 = p1;
	}
	
	/**
	* Methods used to determine the type of Card this AbstractCard is. Typically overridden by card's extensions
	*/
	@Override
	public boolean isTroop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTactic() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
