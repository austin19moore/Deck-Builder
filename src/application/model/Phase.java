package application.model;

/**
 * @author Deck-Builder Project Team
 * UTSA CS 3443 - Deck-Builder
 * Fall 2022
 * 
 * Enumerations of the Phases of the Game
 */
public enum Phase {
	DRAW("Draw"),
	PLAY("Play"),
	MOVE("Move"),
	FIGHT("Fight"),
	END("End"),
	GAMEOVER("Gameover");
	public final String label;
	
	/**
	* Constructor for the phases
	*/
	private Phase(String label) {
		this.label = label;
	}
	/**
	* get: Method to get a label from a given label
	* @param label: String representation of a label to be returned
	* @return: Phase to be returned
	*/
	public static Phase get(String label) {
		if(label.compareTo(DRAW.label) == 0) {return DRAW;}
		if(label.compareTo(PLAY.label) == 0) {return PLAY;}
		if(label.compareTo(MOVE.label) == 0) {return MOVE;}
		if(label.compareTo(FIGHT.label) == 0) {return FIGHT;}
		if(label.compareTo(END.label) == 0) {return END;}
		if(label.compareTo(GAMEOVER.label) == 0) {return GAMEOVER;}
		return null;
	}
	
	/**
	* next(): Method to return the next phase in a game
	* @return: Phase of the next Phase
	*/
	public Phase next() {
		if (this == Phase.DRAW) {return Phase.PLAY;}
		if (this == Phase.PLAY) {return Phase.MOVE;}
		if (this == Phase.MOVE) {return Phase.FIGHT;}
		if (this == Phase.FIGHT) { return Phase.END;}
		if (this == Phase.END) {return Phase.DRAW;}
		return null;
	}
}
