package application.model;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * the enumeration of Types of cards
 */
public enum Type{
	TROOP("Troop",0, 10),
	TACTIC("Tactic",10, 20);
	public final String label;
	public final int start;
	public final int end;
	
	private Type(String label, int start, int end) {
		this.label = label;
		this.start = start;
		this.end = end;
	}
	
	/**
	* valueOfCardNumber: returns a Type based on cardNumber
	* @param cardNumber: int representation of a cardNumber
	* @return: Type of card from a cardNumber
	*/
	public static Type valueOfCardNumber(int cardNumber) {
		for (Type t: values()) {
			if(t.start <= cardNumber && t.end > cardNumber) {
				return t;
			}
		}
		return null;
	}
}
