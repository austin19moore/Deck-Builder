package application.model;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * The enumeration for Rarities of Cards
 */
public enum Rarity{
	BASIC("Basic",0.0, 0.5),
	RARE("Rare",0.5, 0.85),
	LEGENDARY("Legendary", 0.85, 1.0);
	public final String label;
	public final double lowPercent;
	public final double highPercent;
	
	/**
	* Constructors for the rarity enumeration
	*/
	private Rarity(String label, double lowPercent, double highPercent) {
		this.label = label;
		this.lowPercent = lowPercent;
		this.highPercent = highPercent;
	}
	
	/**
	* valueOfCardNumber: static method to determine a rarity of a card based on its card number
	* @param cardNumber: int representation of a cardNumber
	* @return: Rarity of the given card
	*/
	public static Rarity valueOfCardNumber(int cardNumber) {
		Type type = Type.valueOfCardNumber(cardNumber);
		int start = type.start;
		int end = type.end;
		int range = end - start;
		for (Rarity r: values()) {
			if ((start + (int) Math.ceil(range * r.lowPercent)) <= cardNumber && 
					(start +(int)Math.ceil(range * r.highPercent) > cardNumber)) 
			{
				return r;
			}
		}
		return null;
	}
}
